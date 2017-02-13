//
//   FILE:  dht11_test1.ino
// PURPOSE: DHT11 library test sketch for Arduino
//
#include <dht11.h>
#include <dewpoint.h>

dht11 DHT11;

#define DHT11PIN  4

void setup() {
   Serial.begin(115200);
   Serial.println("DHT11 TEST PROGRAM ");
   Serial.print("LIBRARY VERSION: ");
   Serial.println(DHT11LIB_VERSION);
   Serial.println();
   int chk = DHT11.read(DHT11PIN);
}

void loop() {

   delay(1000);
   int chk = DHT11.read(DHT11PIN);

   Serial.println("\n");
   Serial.print("Read sensor: ");
   switch (chk) {
   case DHTLIB_OK:
      Serial.println("OK");
      break;
   case DHTLIB_ERROR_CHECKSUM:
      Serial.println("Checksum error");
      break;
   case DHTLIB_ERROR_TIMEOUT:
      Serial.println("Time out error");
      break;
   default:
      Serial.println("Unknown error");
      break;
   }

   if (chk == DHTLIB_OK) {

      Serial.print("Humidity (%): ");
      Serial.println((float)DHT11.humidity, 2);

      Serial.println("Temperature");
      Serial.print("       (C): ");
      Serial.println((float)DHT11.temperature, 2);

      Serial.print("       (F): ");
      Serial.println(Fahrenheit(DHT11.temperature), 2);

      Serial.print("       (K): ");
      Serial.println(Kelvin(DHT11.temperature), 2);

      Serial.print("Dew Point     (C): ");
      Serial.println(dewPoint(DHT11.temperature, DHT11.humidity));

      Serial.print("Dew PointFast (C): ");
      Serial.println(dewPointFast(DHT11.temperature, DHT11.humidity));
   }
}