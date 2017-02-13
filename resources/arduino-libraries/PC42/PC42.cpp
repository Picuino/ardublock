/*
   PC42.cpp - This file is part of Picuino Control Board PC42 
   library for Arduino.
   
   Copyright 2015-2016 Carlos Pardo Martín. All rights reserved.

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
   GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public 
   License along with this program. If not, see
   <http://www.gnu.org/licenses/>.

   Version 1.0, 07 septembrer 2016
*/

#include <stdlib.h>
#include <inttypes.h>
#include <arduino.h>
#include <Wire.h>
#include "PC42.h"


// Initialize Class Variables //////////////////////////////////////////////////
uint8_t PC42::Buff[WIRE_BUFFER_SIZE];
uint8_t PC42::twiAddr;
uint8_t PC42::error_num;


/******************************************************************************
   PRIVATE METHODS
 ******************************************************************************/

/////////////////////////////////////////////////////////////////
void PC42::WireSendBuff(uint8_t cmd, uint8_t bytes) {
   uint8_t *p;

   error_num = 0;
   Buff[0] = cmd;
   if (bytes > WIRE_BUFFER_SIZE)
      bytes = WIRE_BUFFER_SIZE;
   p = Buff;
   Wire.beginTransmission(twiAddr);
   while(bytes--) {
      Wire.write(*p++);
   }
   error_num = Wire.endTransmission();       // Stop transmitting
   delayMicroseconds(500);  // Clock stretching
};


/////////////////////////////////////////////////////////////////
uint8_t PC42::WireReadChar(void) {
   uint8_t wait;
   wait = WIRE_MAX_WAIT;
   error_num = 0;
   for(;;) {
      if (Wire.available())
         return Wire.read();
      if (wait == 0) {
         error_num = CMD_ERROR;
         return error_num;
      }
      delayMicroseconds(10);
      wait--;
   }
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::WireReadBuff(uint8_t bytes) {
   uint8_t *p, c;

   if (bytes == 0) {
      error_num = -1;
      return error_num;
   }

   if (bytes != Wire.requestFrom((uint8_t)twiAddr, bytes)) {   // request bytes from slave device
      error_num = -1;
      return error_num;
   }

   Buff[0] = WireReadChar();
   if (Buff[0] == CMD_ERROR) {
      error_num = -2;
      return error_num;
   }

   p = &Buff[1];
   while(--bytes)
      *p++ = WireReadChar();

   if (Buff[0] == CMD_NOP) {
     error_num = 0;
     return 0;
   }
   else 
      return Buff[0];
}


/******************************************************************************
   PUBLIC METHODS
 ******************************************************************************/

// Constructors ////////////////////////////////////////////////////////////////
PC42::PC42() {
   twiAddr = WIRE_ADDRESS;
}


/****************************************************************
   SYSTEM METHODS
 ****************************************************************/


/////////////////////////////////////////////////////////////////
void PC42::digitalWrite(uint8_t ledNum, uint8_t value) {
   if (ledNum>=1 && ledNum<=8) {
      ledWrite(ledNum, value);
   }
   else if (ledNum>=11 && ledNum<=48) {
      // Set/Reset segments in digit
      Buff[1] = ledNum;
      if (value != LOW) Buff[1] |= 0x80;
      WireSendBuff(CMD_DISPLAY + CMD_DISP_SEGM, 2);
   }
}


/////////////////////////////////////////////////////////////////
int PC42::digitalRead(uint8_t keyNum) {
   return (int)keyValue(keyNum);
}


/////////////////////////////////////////////////////////////////
signed char PC42::error(void) {
   return error_num;
}


/////////////////////////////////////////////////////////////////
void PC42::begin(uint8_t Addr) {
   twiAddr = Addr;
   Wire.begin(twiAddr);
   sysBegin();
}

void PC42::begin(void) {
   Wire.begin(twiAddr);
   sysBegin();
}


/////////////////////////////////////////////////////////////////
void PC42::sysBegin(void) {
   // Inits leds state
   WireSendBuff(CMD_SYSTEM + CMD_SYS_BEGIN, 1);
}


/////////////////////////////////////////////////////////////////
void PC42::sysVersion(char *str) {
   WireSendBuff(CMD_SYSTEM + CMD_SYS_VERSION, 1);
   WireReadBuff(6);
   sprintf(str, "v%d.%02d %02d/%02d/20%02d", Buff[1], Buff[2], Buff[3], Buff[4], Buff[5]);
}


/////////////////////////////////////////////////////////////////
int PC42::analogRead(uint8_t channel) {
   // Read ADC

   switch (channel & 0x03) {
   case ADC_VCC:
      WireSendBuff(CMD_SYSTEM + CMD_SYS_VCC, 1);
      if (WireReadBuff(3) == 0)
         return *((uint16_t *)(&Buff[1]));
      break;
   case ADC_TEMPERATURE:
      WireSendBuff(CMD_SYSTEM + CMD_SYS_TEMPERATURE, 1);
      if (WireReadBuff(2) == 0)
         return ((signed char)Buff[1]);
      break;
   default:
      break;
   }
   return -1;
}


/////////////////////////////////////////////////////////////////
void PC42::eepromWrite(uint8_t address, uint8_t data) {
   // Read temperature
   Buff[1] = address;
   Buff[2] = data;
   WireSendBuff(CMD_SYSTEM + CMD_SYS_WRITE_EEPROM, 3);
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::eepromRead(uint8_t address) {
   // Read EEPROM
   Buff[1] = address;
   WireSendBuff(CMD_SYSTEM + CMD_SYS_READ_EEPROM, 2);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::sysOsccal(void) {
   // Calibrate PC42 Oscillator
   WireSendBuff(CMD_SYSTEM + CMD_SYS_OSCCAL, 1);
}


/////////////////////////////////////////////////////////////////
void PC42::sysTest(uint8_t data1) {
   // Read EEPROM
   Buff[1] = data1;
   WireSendBuff(CMD_SYSTEM + CMD_SYS_TEST, 2);
}


uint16_t PC42::sysTest(uint8_t data1, uint16_t data2) {
   // Read EEPROM
   Buff[1] = data1;
   *((uint16_t *)(&Buff[2])) = data2;
   WireSendBuff(CMD_SYSTEM + CMD_SYS_TEST, 4);
   WIRE_RETURN_BUFF2();
}


/****************************************************************
   LED METHODS
 ****************************************************************/

/////////////////////////////////////////////////////////////////
void PC42::ledBegin(void) {
   // Inits leds state
   WireSendBuff(CMD_LED + CMD_BEGIN, 1);
}

/////////////////////////////////////////////////////////////////
void PC42::ledWrite(uint8_t ledNum, uint8_t bright) {
   // Switch led on and off
   if (ledNum > LED_NUM_MAX)
      return;
   Buff[1] = ledNum;
   Buff[2] = bright;
   WireSendBuff(CMD_LED + CMD_WRITE, 3);
}

/////////////////////////////////////////////////////////////////
void PC42::ledBlink(uint8_t ledNum, uint16_t time_on, uint16_t time_off) {
   // Blink led with time_on and time_off
   if (ledNum > LED_NUM_MAX)
      return;
   Buff[1] = ledNum;
   Buff[2] = time_on;
   Buff[3] = time_on >> 8;
   Buff[4] = time_off;
   Buff[5] = time_off >> 8;
   WireSendBuff(CMD_LED + CMD_WRITE, 6);
}


/****************************************************************
   KEYBOARD METHODS
 ****************************************************************/

/////////////////////////////////////////////////////////////////
void PC42::keyBegin(void) {
   // Init keyboard state
   WireSendBuff(CMD_KEYB + CMD_BEGIN, 1);
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::keyValue(uint8_t keyNum) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = keyNum;
   WireSendBuff(CMD_KEYB + CMD_KEY_VALUE, 2);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::keyPressed(uint8_t keyNum) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = keyNum;
   WireSendBuff(CMD_KEYB + CMD_KEY_PRESSED, 2);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::keyToggle(uint8_t keyNum) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = keyNum;
   WireSendBuff(CMD_KEYB + CMD_KEY_TOGGLE, 2);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
uint16_t PC42::keyTimeOn(uint8_t keyNum) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = keyNum;
   WireSendBuff(CMD_KEYB + CMD_KEY_TIMEON, 2);
   WIRE_RETURN_BUFF2();
}


/////////////////////////////////////////////////////////////////
uint16_t PC42::keyTimeOff(uint8_t keyNum) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = keyNum;
   WireSendBuff(CMD_KEYB + CMD_KEY_TIMEOFF, 2);
   WIRE_RETURN_BUFF2();
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::keyCount(uint8_t keyNum) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = keyNum;
   WireSendBuff(CMD_KEYB + CMD_KEY_COUNT, 2);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
uint8_t PC42::keyEvents(uint8_t keyNum, uint8_t event) {
   if (keyNum > KEY_NUM_MAX) return 0;
   Buff[1] = (event << 4) + (keyNum & 0x0F);
   WireSendBuff(CMD_KEYB + CMD_KEY_EVENTS, 2);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
void PC42::keyReset(void) {
   // Reset all Keyboard Events
   WireSendBuff(CMD_KEYB + CMD_KEY_RESET, 1);
}


/****************************************************************
   DISPLAY METHODS
 ****************************************************************/

/////////////////////////////////////////////////////////////////
void PC42::dispBegin(void) {
   // Init display state
   WireSendBuff(CMD_DISPLAY + CMD_BEGIN, 1);
}


/////////////////////////////////////////////////////////////////
void PC42::dispNum(uint16_t number) {
   // Display 4 digit number
   if (number < 0) number = -number;
   *((uint16_t *)(&Buff[1])) = number;
   WireSendBuff(CMD_DISPLAY + CMD_DISP_PRINTN, 3);
}


void PC42::dispNum(uint8_t digitNum, uint8_t number) {
   // Display 1 digit number
   const uint8_t segments[] = {
      DD_0, DD_1, DD_2, DD_3, DD_4,
      DD_5, DD_6, DD_7, DD_8, DD_9,
      DD_A, DD_b, DD_C, DD_d, DD_E,  DD_F,
   };
   if (digitNum > 4) return;
   if (number > sizeof(segments)) 
      return;
   Buff[1] = digitNum;
   Buff[2] = segments[number];
   WireSendBuff(CMD_DISPLAY + CMD_WRITE, 3);
}


/////////////////////////////////////////////////////////////////
void PC42::dispWrite(uint8_t dig1, uint8_t dig2, uint8_t dig3, uint8_t dig4) {
  // Display string
   Buff[1] = dig4;
   Buff[2] = dig3;
   Buff[3] = dig2;
   Buff[4] = dig1;
   WireSendBuff(CMD_DISPLAY + CMD_WRITE, 5);
}


void PC42::dispWrite(uint8_t digitNum, uint8_t segments) {
   // Display segments in digit
   if (digitNum > DISPLAY_NUM_DIGIT || digitNum == 0)
      return;
   Buff[1] = digitNum;
   Buff[2] = segments;
   WireSendBuff(CMD_DISPLAY + CMD_WRITE, 3);
}


/////////////////////////////////////////////////////////////////
void PC42::dispDots(uint8_t dots) {
   // Display dots
   Buff[1] = dots;
   WireSendBuff(CMD_DISPLAY + CMD_DISP_DOTS, 2);
}


/****************************************************************
   BUZZER METHODS
 ****************************************************************/

/////////////////////////////////////////////////////////////////
void PC42::buzzBegin(void) {
   // Init buzzer state
   WireSendBuff(CMD_BUZZER + CMD_BEGIN, 1);
}


/////////////////////////////////////////////////////////////////
void PC42::buzzFreq(int frequency) {
   int per;
   // Buzz frequency
   if (frequency < 0)
      frequency = 0;

   *((uint16_t *)(&Buff[1])) = frequency;
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_FREQ, 3);
}


void PC42::buzzFreq(float frequency) {
   // Buzz frequency
   if (frequency > 1024.0*1024 || frequency < 15.0)
      return;

   frequency = (4000000.0 / frequency);
   Buff[2] = 1;
   while(1) {
      if (frequency < 254.0 || Buff[2]==5) {
        Buff[1] = (frequency + 0.5);
        break;
      }
      if (Buff[2] <= 2) {
         frequency /= 8;
      }
      else {
        frequency /= 4;
      }
      Buff[2]++;
   }
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_PWM, 3);
}


/////////////////////////////////////////////////////////////////
void PC42::buzzTone(uint8_t note) {
   Buff[1] = note;
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_TONE, 2);
}


/////////////////////////////////////////////////////////////////
void PC42::buzzPlay(uint8_t tone, uint16_t time) {
   Buff[1] = tone;
   if (time < 128)
      Buff[2] = (time>>1);
   else if (time < 384)
      Buff[2] =  32 + (time>>2);
   else if (time < 896)
      Buff[2] =  80 + (time>>3);
   else if (time < 1920)
      Buff[2] = 136 + (time>>4);
   else Buff[2] = 0;
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_PLAY, 3);
}

/////////////////////////////////////////////////////////////////
uint8_t PC42::buzzPlay(void) {
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_PLAY_LEN, 1);
   WIRE_RETURN_BUFF1();
}


/////////////////////////////////////////////////////////////////
void PC42::buzzOn(void) {
   // Read buzzer state
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_ON, 1);
}


/////////////////////////////////////////////////////////////////
void PC42::buzzOff(void) {
   // Read buzzer state
   WireSendBuff(CMD_BUZZER + CMD_BUZZ_OFF, 1);
}


// Preinstantiate Objects //////////////////////////////////////////////////////
PC42 pc = PC42();

/************************* END **************************/
