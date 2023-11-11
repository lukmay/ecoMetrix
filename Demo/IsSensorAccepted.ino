SensorState isSensorAccepted(HttpClient client, unsigned int id)
{
    // Number of milliseconds to wait without receiving any data before we give up
    const int kNetworkTimeout = 30*1000;
    // Number of milliseconds to wait if no data is available before trying again
    const int kNetworkDelay = 1000;
    const char* hostname = "internal.wmk-technology.com";
    const std::string url = "/api/v1/web-app/sensor/" + std::to_string(id);
    int errorCode = client.get(hostname, 8080, url.c_str());

    if (errorCode == 0)
    {
        // Serial.println("Query if sensor is accepted.");

        errorCode = client.responseStatusCode();
        if (errorCode >= 0)
        {
            // Serial.print("Got status code: ");
            // Serial.println(errorCode);

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

                char c;
                std::string body = "";
                int bodyIndex = 0;
                // Whilst we haven't timed out & haven't reached the end of the body
                while ((client.available()) && ((millis() - timeoutStart) < kNetworkTimeout))
                {
                    c = client.read();
                    if (bodyIndex > 2)
                        body += c;
                    bodyIndex++;
                }

                const auto iterator = body.find_last_of("}");
                body.erase(body.begin() + iterator + 1, body.end());

                StaticJsonDocument<256> doc;
                deserializeJson(doc, body.c_str());
                return {id, doc["accepted"], doc["updateRateInMS"]};
            }
            else
            {
                // Serial.print("Failed to skip response headers: ");
                // Serial.println(errorCode);
            }
        }
        else
        {
            // Serial.print("Getting response failed: ");
            // Serial.println(errorCode);
        }
    }
    else
    {
        // Serial.println("Failed to request acception state");
    }

    return {id, false, 10e30};
}