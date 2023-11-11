#include <EEPROM.h>

int i = 0;

void setup()
{
    Serial.begin(115200);
    Serial.println("Resetting EEPROM");
}

void loop()
{
    if (i < EEPROM.length() || i < 128)
    {
        EEPROM.write(i, 0);
        i++;
    }

    if (i == EEPROM.length() - 1 || i == 127)
        Serial.println("EEPROM is reset!");
}