unsigned int registerSensor(HttpClient client, SensorType sensorType, int index)
{
    unsigned int id = 0;

    switch (sensorType)
    {
    case SensorType::eHumidity:
        client.post("/api/v1/sensor/create/HumidityAndTemperature");
        break;
    case SensorType::eLightLevel:
        client.post("/api/v1/sensor/create/LightLevel");
        break;
    case SensorType::eTemperature:
        client.post("/api/v1/sensor/create/Temperature");
        break;
    default:
        break;
    }

    int statusCode = client.responseStatusCode();
    if (statusCode < 300 && statusCode >= 200)
    {
        String response = client.responseBody();
        id = std::stoi(response.c_str());
        EEPROM.writeUInt(index * sizeof(int), id);
        EEPROM.commit();
    }
    else
    {
        Serial.println("Failed to request registration");
    }

    return id;
}