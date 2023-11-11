#define BLINKY_LED 14

void setup()
{
    Serial.begin(115200);
    pinMode(BLINKY_LED, OUTPUT);
}

void loop()
{
    activityLED(BLINKY_LED);
}
