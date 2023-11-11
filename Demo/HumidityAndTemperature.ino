byte readData(int pin)
{
    byte data;
    for(int i = 0; i < 8; i++)
    {
        if (digitalRead(pin) == LOW)
        {
            while(digitalRead(pin) == LOW); // wait for 50us

            delayMicroseconds(30); // determine the duration of the high level to determine the data is '0 'or '1'

            if (digitalRead(pin) == HIGH)
                data |= (1 << (7-i)); // high front and low in the post

            while(digitalRead(pin) == HIGH); // data '1 ', wait for the next one receiver
        }
    }
    return data;
}

HumidityAndTemperatureMeasurement startTest(int pin)
{
    static byte data[4];

    digitalWrite(pin, LOW); // put pin down and send start signal
    delay(30); // delay greater than 18ms, so DHT11 start signal can be detected
    
    digitalWrite(pin, HIGH);
    delayMicroseconds(40); // Waiting for the sensor's response
    
    pinMode(pin, INPUT);
    while(digitalRead(pin) == HIGH);
    delayMicroseconds(80); // DHT11 response and pulled the pin 80us
    if (digitalRead(pin) == LOW);
    delayMicroseconds(80); // DHT11 80us after the pin has been pulled down to start sending data
    
    for (int i = 0; i < 4; i++) // receive temperature and humidity data raw data while parity is not considered
        data[i] = readData(pin);
    
    pinMode(pin, OUTPUT);
    digitalWrite(pin, HIGH); // send data once after releasing the pin, wait for the host to open the next Start signal

    const uint16_t humidityInt = (data[0] << 8) | data[1];
    const uint16_t temperatureInt = (data[2] << 8) | data[3];
    const float humidity = humidityInt / static_cast<float>(1 << 8);
    const float temperature = temperatureInt / static_cast<float>(1 << 8);
    return {humidity, temperature};
}

void humidityAndTemperature(int pin, unsigned long updateRate)
{
    static unsigned long lastMeasurement = millis();

    const unsigned long currentMillis = millis();
    if (lastMeasurement + updateRate < currentMillis)
    {
        const auto data = startTest(pin);

        Serial.println("Humidity = " + String(data.humidity, 1) + "%");
        Serial.println("Temperature = " + String(data.temperature, 1) + "C");

        lastMeasurement = currentMillis;
    }
}