#define BLINKY_LED 14

// Sensors
#define HUMIDITY_TEMPERATURE_SENSOR 19

struct HumidityAndTemperatureMeasurement
{
    float humidity;
    float temperature;
};

void setup()
{
    Serial.begin(115200);
    pinMode(BLINKY_LED, OUTPUT);
    pinMode(HUMIDITY_TEMPERATURE_SENSOR, OUTPUT); // Has to be output since it has to initiate measurement
}

void loop()
{
    activityLED(BLINKY_LED);
    humidityAndTemperature(HUMIDITY_TEMPERATURE_SENSOR, 5000);
}
