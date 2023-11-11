void activityLED(int pin, unsigned long interval)
{
    static bool state = false;
    static unsigned long lastStateChange = millis();

    const unsigned long currentMillis = millis();
    if (lastStateChange + interval < currentMillis)
    {
        if (state)
        {
            Serial.println("[MCU still active]");
            digitalWrite(pin, HIGH);
        }
        else
        {
            Serial.println("[MCU still active]");
            digitalWrite(pin, LOW);
        }

        state = !state;
        lastStateChange = currentMillis;
    }
}