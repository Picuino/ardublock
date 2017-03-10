// 
//    FILE: dewpoint.h
// VERSION: 0.4.1
// PURPOSE: DHT11 Temperature & Humidity Sensor library for Arduino
// LICENSE: GPL v3 (http://www.gnu.org/licenses/gpl.html)
//
// DATASHEET: http://www.micro4you.com/files/sensor/DHT11.pdf
//
//     URL: http://playground.arduino.cc/Main/DHT11Lib
//
// HISTORY:
// George Hadjikyriacou - Original version
// see dht.cpp file
// 

#ifndef dewpoint_h
#define dewpoint_h

#include <math.h>
// Celsius to Fahrenheit conversion
double Fahrenheit(double celsius) {
   return 1.8 * celsius + 32;
}

//Celsius to Kelvin conversion
double Kelvin(double celsius) {
   return celsius + 273.15;
}

// dewPoint function NOAA
// reference (1) : http://wahiduddin.net/calc/density_algorithms.htm
// reference (2) : http://www.colorado.edu/geography/weather_station/Geog_site/about.htm
//
double dewPoint(double celsius, double humidity) {
   // (1) Saturation Vapor Pressure = ESGG(T)
   double RATIO = 373.15 / (273.15 + celsius);
   double RHS = -7.90298 * (RATIO - 1);
   RHS += 5.02808 * log10(RATIO);
   RHS += -1.3816e-7 * (pow(10, (11.344 * (1 - 1/RATIO ))) - 1) ;
   RHS += 8.1328e-3 * (pow(10, (-3.49149 * (RATIO - 1))) - 1) ;
   RHS += log10(1013.246);

   // factor -3 is to adjust units - Vapor Pressure SVP * humidity
   double VP = pow(10, RHS - 3) * humidity;

   // (2) DEWPOINT = F(Vapor Pressure)
   double T = log(VP/0.61078);   // temp var
   return (241.88 * T) / (17.558 - T);
}

// delta max = 0.6544 wrt dewPoint()
// 6.9 x faster than dewPoint()
// reference: http://en.wikipedia.org/wiki/Dew_point
double dewPointFast(double celsius, double humidity) {
   double a = 17.271;
   double b = 237.7;
   double temp = (a * celsius) / (b + celsius) + log(humidity*0.01);
   double Td = (b * temp) / (a - temp);
   return Td;
}

#endif