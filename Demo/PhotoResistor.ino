MeasurementResult photoResistor(int pin, unsigned long updateRate)
{
    static unsigned long lastMeasurement = millis();

    const unsigned long currentMillis = millis();
    if (lastMeasurement + updateRate < currentMillis)
    {
        const auto rawValue = analogRead(pin);
        Serial.println("Photocell light intensity: " + String(rawValue));

        lastMeasurement = currentMillis;
        return {true, static_cast<float>(rawValue)};
    }

    return {false, 0.0f};
}