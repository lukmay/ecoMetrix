void uploadData(HttpClient client, unsigned int id, float value)
{
    const char* hostname = "internal.wmk-technology.com";
    const std::string url = "/api/v1/sensor/data/" + std::to_string(id);
    const std::string body = "{\"value\":" + std::to_string(value) + "}";
    client.beginRequest();
    client.sendHeader("Content-Type", "application/json");
    client.write(reinterpret_cast<const uint8_t*>(body.c_str()), body.size() + 1);
    client.endRequest();
    int errorCode = client.post(hostname, 8080, url.c_str());

    if (errorCode == 0)
    {
        Serial.println("Sensor data has been uploaded.");

        errorCode = client.responseStatusCode();
        if (errorCode >= 0)
        {
            Serial.print("Got status code: ");
            Serial.println(errorCode);
        }
        else
        {
            Serial.print("Getting response failed: ");
            Serial.println(errorCode);
        }
    }
    else
    {
        Serial.println("Failed to upload data");
        Serial.println(("Url: " + url).c_str());
        Serial.println(("Body: " + body).c_str());
    }
}