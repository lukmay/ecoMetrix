#include <EEPROM.h>
#include <HttpClient.h>
#include <WiFi.h>

// std
#include <string>

#define BLINKY_LED 14

// Sensors
#define HUMIDITY_TEMPERATURE_SENSOR 19
#define PHOTO_RESISTOR 32

enum SensorType
{
    eHumidityTemperature,
    eLightLevel
};

struct HumidityAndTemperatureMeasurement
{
    float humidity;
    float temperature;
};

unsigned int humidityId;
unsigned int photoId;

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
    EEPROM.begin(idOffset * 2);
    humidityId = EEPROM.readUInt(0);
    photoId = EEPROM.readUInt(1 * idOffset);
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

    if (humidityId != 0)
    {
        humidityAndTemperature(HUMIDITY_TEMPERATURE_SENSOR, 5000);
    }
    else
    {
        humidityId = registerSensor(httpClient, SensorType::eHumidityTemperature, 0);
    }

    if (photoId != 0)
    {
        photoResistor(PHOTO_RESISTOR, 5000);
    }
    else
    {
        photoId = registerSensor(httpClient, SensorType::eLightLevel, 1);
    }
}
