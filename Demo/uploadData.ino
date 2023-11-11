void uploadData(HttpClient client, unsigned int id, float value)
{
    const std::string url = "/api/v1/sensor/data/" + std::to_string(id);
    const std::string body = "{\"value\":" + std::to_string(value) + "}";

    client.beginRequest();
    client.post(url.c_str());
    client.sendHeader("Content-Type", "application/json");
    client.sendHeader("Content-Length", body.size());
    client.beginBody();
    client.print(body.c_str());
    client.endRequest();

    int statusCode = client.responseStatusCode();
    if (statusCode < 200 || statusCode >= 300)
        Serial.println("Upload of sensor " + String(id) + " failed with code " + String(statusCode));
}