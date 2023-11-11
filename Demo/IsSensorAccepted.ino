SensorState isSensorAccepted(HttpClient client, unsigned int id)
{
    const std::string url = "/api/v1/web-app/sensor/" + std::to_string(id);
    int errorCode = client.get(url.c_str());


    int statusCode = client.responseStatusCode();
    if (statusCode < 300 && statusCode >= 200)
    {
        std::string body = client.responseBody().c_str();
        const auto iterator = body.find_last_of("}");
        body.erase(body.begin() + iterator + 1, body.end());

        StaticJsonDocument<256> doc;
        deserializeJson(doc, body.c_str());
        return {id, doc["accepted"], doc["updateRateInMS"]};
    }

    return {id, false, 10e30};
}