unsigned int registerSensor(HttpClient client, SensorType sensorType, int index)
{
    unsigned int id = 0;
    const char* hostname = "internal.wmk-technology.com";
    int errorCode = -1;

    // Number of milliseconds to wait without receiving any data before we give up
    const int kNetworkTimeout = 30*1000;
    // Number of milliseconds to wait if no data is available before trying again
    const int kNetworkDelay = 1000;

    switch (sensorType)
    {
    case SensorType::eHumidity:
        errorCode = client.post(hostname, 8080, "/api/v1/sensor/create/HumidityAndTemperature");
        break;
    case SensorType::eLightLevel:
        errorCode = client.post(hostname, 8080, "/api/v1/sensor/create/LightLevel");
        break;
    case SensorType::eTemperature:
        errorCode = client.post(hostname, 8080, "/api/v1/sensor/create/Temperature");
        break;
    default:
        break;
    }

    if (errorCode == 0)
    {
        Serial.println("Send request for registration.");

        errorCode = client.responseStatusCode();
        if (errorCode >= 0)
        {
            Serial.print("Got status code: ");
            Serial.println(errorCode);

            // Usually you'd check that the response code is 200 or a
            // similar "success" code (200-299) before carrying on,
            // but we'll print out whatever response we get

            errorCode = client.skipResponseHeaders();
            if (errorCode >= 0)
            {
                unsigned long timeoutStart = millis();
                while ((client.connected() && !client.available()) && ((millis() - timeoutStart) < kNetworkTimeout))
                {
                    // We haven't got any data, so let's pause to allow some to
                    // arrive
                    delay(kNetworkDelay);
                }

                std::string stringId = "";
                int bodyIndex = 0;
                char c;
                // Whilst we haven't timed out & haven't reached the end of the body
                while ((client.available()) && ((millis() - timeoutStart) < kNetworkTimeout))
                {
                    c = client.read();
                    // Print out this character
                    if (bodyIndex > 2)
                    {
                        if (c < 48)
                            break;
                        stringId += c;
                    }
                    bodyIndex++;
                }

                id = std::stoi(stringId);
                EEPROM.writeUInt(index * sizeof(int), id);
                EEPROM.commit();
            }
            else
            {
                Serial.print("Failed to skip response headers: ");
                Serial.println(errorCode);
            }
        }
        else
        {
            Serial.print("Getting response failed: ");
            Serial.println(errorCode);
        }
    }
    else
    {
        Serial.println("Failed to request registration");
    }

    return id;
}