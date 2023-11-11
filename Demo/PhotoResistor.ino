void photoResistor(int pin, unsigned long updateRate)
{
    static unsigned long lastMeasurement = millis();

    const unsigned long currentMillis = millis();
    if (lastMeasurement + updateRate < currentMillis)
    {
        constexpr float adcResolution = static_cast<float>(1 << 12);
        const auto rawValue = analogRead(pin);
        Serial.println("Photocell light intensity: " + String(rawValue));

        lastMeasurement = currentMillis;
    }
}