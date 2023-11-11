#include <ArduinoJson.h>
#include <EEPROM.h>
#include <HttpClient.h>
#include <WiFi.h>

// std
#include <functional>
#include <string>

#define BLINKY_LED 14

// Sensors
#define HUMIDITY_TEMPERATURE_SENSOR 19
#define PHOTO_RESISTOR 32

enum SensorType
{
    eHumidity,
    eLightLevel,
    eTemperature
};

struct SensorState
{
    unsigned int id;
    bool accepted;
    unsigned long updateInterval;

    SensorState()
    {
        id = 0;
        accepted = false;
        updateInterval = 10e30;
    }

    SensorState(unsigned id, bool accepted, unsigned long updateInterval)
    {
        this->id = id;
        this->accepted = accepted;
        this->updateInterval = updateInterval;
    }
};

struct HumidityAndTemperatureMeasurement
{
    float humidity;
    float temperature;
};

struct MeasurementResult
{
    bool shouldUpdate;
    float value;
};

SensorState humidityState{};
SensorState photoState{};
SensorState temperatureOneState{};

// WiFi
const char* ssid = "openAiR";

void setup()
{
    Serial.begin(115200);
    pinMode(BLINKY_LED, OUTPUT);
    pinMode(HUMIDITY_TEMPERATURE_SENSOR, OUTPUT); // Has to be output since it has to initiate measurement
    pinMode(PHOTO_RESISTOR, ANALOG);

    WiFi.begin(ssid);
    Serial.print("Connecting to " + String(ssid));

    while(WiFi.status() != WL_CONNECTED){
        Serial.print(".");
        activityLED(BLINKY_LED, 200);
        delay(100);
    }

    Serial.println("\nConnected to " + String(ssid));

    constexpr int idOffset = sizeof(int);
    EEPROM.begin(idOffset * 3);
    humidityState.id = EEPROM.readUInt(0);
    photoState.id = EEPROM.readUInt(1 * idOffset);
    temperatureOneState.id = EEPROM.readUInt(2 * idOffset);
}

void loop()
{
    if(WiFi.status() == WL_CONNECTED)
    {
        activityLED(BLINKY_LED, 1000);
    }
    else
    {
        activityLED(BLINKY_LED, 200);
        return;
    }

    WiFiClient wifiClient;
    HttpClient httpClient(wifiClient);

    if (humidityState.id != 0)
    {
        if (humidityState.accepted)
        {
            const auto result = humidity(HUMIDITY_TEMPERATURE_SENSOR, humidityState.updateInterval);
            if (result.shouldUpdate)
                uploadData(httpClient, humidityState.id, result.value);
        }
        else
        {
            humidityState = isSensorAccepted(httpClient, humidityState.id);
        }
    }
    else
    {
        humidityState.id = registerSensor(httpClient, SensorType::eHumidity, 0);
    }

    if (temperatureOneState.id != 0)
    {
        if (temperatureOneState.accepted)
        {
            const auto result = temperatureSingleWire(HUMIDITY_TEMPERATURE_SENSOR, temperatureOneState.updateInterval);
            if (result.shouldUpdate)
                uploadData(httpClient, temperatureOneState.id, result.value);
        }
        else
        {
            temperatureOneState = isSensorAccepted(httpClient, temperatureOneState.id);
        }
    }
    else
    {
        temperatureOneState.id = registerSensor(httpClient, SensorType::eTemperature, 2);
    }

    if (photoState.id != 0)
    {
        if (photoState.accepted)
        {
            const auto result = photoResistor(PHOTO_RESISTOR, photoState.updateInterval);
            if (result.shouldUpdate)
                uploadData(httpClient, photoState.id, result.value);
        }
        else
        {
            photoState = isSensorAccepted(httpClient, photoState.id);
        }
    }
    else
    {
        photoState.id = registerSensor(httpClient, SensorType::eLightLevel, 1);
    }
}

void querySensor(HttpClient httpClient, SensorState sensorState, SensorType type, int pin, int index, std::function<MeasurementResult(int, unsigned long)> measurementFunction)
{
    if (sensorState.id != 0)
    {
        if (sensorState.accepted)
        {
            const auto result = measurementFunction(pin, sensorState.updateInterval);
            if (result.shouldUpdate)
                uploadData(httpClient, sensorState.id, result.value);
        }
        else
        {
            sensorState = isSensorAccepted(httpClient, sensorState.id);
        }
    }
    else
    {
        sensorState.id = registerSensor(httpClient, type, index);
    }
}