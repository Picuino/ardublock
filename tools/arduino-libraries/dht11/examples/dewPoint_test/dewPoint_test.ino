// 
//    FILE: test_dewPoint.pde
// VERSION: 0.1.01
// PURPOSE: test dewpoint
//
// HISTORY:
// 2011-05-01 first version 
// 2013-08-04 updated
// 

#include <dewpoint.h>  // make a .h file of the dewpoint functions above or copy them here instead

void setup() {
   Serial.begin(115200);
   double f;
   double x;

   unsigned long b = millis();
   for (int i=0; i<1000; i++) {
      x = dewPoint(10, 50);
   }
   unsigned long e = millis();
   Serial.println(x, 4);
   Serial.println(e - b);
   f = e - b;


   b = millis();
   for (int i=0; i<1000; i++) {
      x = dewPointFast(10,50);
   }
   e = millis();
   Serial.println(x, 4);
   Serial.println(e - b);
   f = f / (e - b);


   Serial.print("Factor: ");
   Serial.println(f);

   double m = 0;
   for (int t = -30; t < 70; t++) {
      for (int h = 0; h < 100; h++) {
         double x = dewPoint(t, h);
         double y = dewPointFast(t, h);
         m = max(abs(x-y), m);
         if (m > 0.5) {
            Serial.print(t);
            Serial.print("\t");
            Serial.println(h);
         }
      }
      Serial.print(".");
   } 
   Serial.println();
   Serial.print("max: ");
   Serial.println(m);
   Serial.println("done");
}

void loop() {
}

