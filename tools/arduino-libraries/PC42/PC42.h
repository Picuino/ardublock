/*
   PC42.h - This file is part of Picuino Control Board PC42 
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

#ifndef __PC42_H
#define __PC42_H

#include <stdlib.h>
#include <inttypes.h>
#include <Wire.h>
#include "include/display.h"
#include "include/tones.h"

#define DEBUG


/****************************************************************
   COMANDS
 ****************************************************************/

#define CMD_NOP              0x00
#define CMD_ERROR            0xFF


// Command code for Modules
#define CMD_MODULE_MASK      0xF0   // Command mask for module type
#define CMD_SYSTEM           0x00
#define CMD_LED              0x10
#define CMD_DISPLAY          0x20
#define CMD_KEYB             0x30
#define CMD_BUZZER           0x40


// Command code for Functions
#define CMD_TYPE_MASK        0x0F   // Command mask for command type
#define CMD_BEGIN            0x00   // Module initialization
#define CMD_WRITE            0x01   // Write data: Arduino -> PC42
#define CMD_READ             0x02   // Read data:  PC42    -> Arduino


#define CMD_SYS_BEGIN        0x01
#define CMD_SYS_VERSION      0x02
#define CMD_SYS_TEMPERATURE  0x03
#define CMD_SYS_VCC          0x04
#define CMD_SYS_READ_EEPROM  0x05
#define CMD_SYS_WRITE_EEPROM 0x06
#define CMD_SYS_OSCCAL       0x07
#define CMD_SYS_TEST         0x0F


#define CMD_KEY_VALUE        0x03
#define CMD_KEY_PRESSED      0x04
#define CMD_KEY_TOGGLE       0x05
#define CMD_KEY_TIMEON       0x06
#define CMD_KEY_TIMEOFF      0x07
#define CMD_KEY_COUNT        0x08
#define CMD_KEY_EVENTS       0x09
#define CMD_KEY_RESET        0x0A


#define CMD_DISP_PRINTN      0x03
#define CMD_DISP_SEGM        0x04
#define CMD_DISP_DOTS        0x05


#define CMD_BUZZ_ON          0x03
#define CMD_BUZZ_OFF         0x04
#define CMD_BUZZ_PWM         0x05
#define CMD_BUZZ_FREQ        0x06
#define CMD_BUZZ_TONE        0x07
#define CMD_BUZZ_PLAY        0x08
#define CMD_BUZZ_PLAY_LEN    0x09


/****************************************************************
   CONSTANTS
 ****************************************************************/
#define WIRE_BUFFER_SIZE      16
#define WIRE_MAX_WAIT        200  // *10us
#define WIRE_ADDRESS        0x4A

//
//   SYSTEM
//
#define ADC_VCC                0
#define ADC_TEMPERATURE        1

//
//   LED
//
#define LED_NUM_MAX          (8)
#define LED_ON            (0xFF)
#define LED_OFF           (0x00)


//
//   DISPLAY
//
#define DISPLAY_NUM_DIGIT    (4)
#define DISPLAY_DOT_1       0x01
#define DISPLAY_DOT_2       0x02
#define DISPLAY_DOT_3       0x04
#define DISPLAY_DOT_4       0x08
#define DISPLAY_DOTS        0x00


//
//   KEYBOARD
//
#define KEY_NUM_MAX          (6)

#define KEY_PRESSED_TIME1   0x01
#define KEY_PRESSED_TIME2   0x02
#define KEY_PRESSED_TIME3   0x03
#define KEY_RELEASED        0x04

#define KEY_ALL                0
#define KEY_LEFT               1
#define KEY_RIGHT              2
#define KEY_DOWN               3
#define KEY_UP                 4
#define KEY_ENTER              5
#define KEY_BACK               6


//
//   EEPROM
//
// EEPROM ADDRESSES
#define EEPROM_MAX_ADDR     63
#define EADDR_TWI_ADDRESS   (EEPROM_MAX_ADDR - 0)
#define EADDR_OSCCAL        (EEPROM_MAX_ADDR - 1)
#define EADDR_TEMP_OFFSET   (EEPROM_MAX_ADDR - 2)
#define EADDR_TEMP_GAIN     (EEPROM_MAX_ADDR - 3)
#define EADDR_VREF_OFFSET   (EEPROM_MAX_ADDR - 4)


/****************************************************************
   MACROS
 ****************************************************************/
#define WIRE_RETURN_BUFF1()             \
   if (WireReadBuff(2) == CMD_NOP)      \
      return Buff[1];                   \
   return 0;                            \

#define WIRE_RETURN_BUFF2()             \
   if (WireReadBuff(3) == CMD_NOP)      \
      return *((uint16_t *)(&Buff[1])); \
   return 0;                            \



/****************************************************************
   CLASS DECLARATION
 ****************************************************************/
class PC42 {

   // ***********************************************************
   // PRIVATE SECTION
   // ***********************************************************
   private:
   #ifdef DEBUG
   #warning "CAUTION: DEBUG ACTIVE"
   public:
   #endif

      // PRIVATE DATA
      static uint8_t Buff[];
      static uint8_t twiAddr;
      static uint8_t error_num;

      // PRIVATE METHODS
      void WireSendBuff(uint8_t cmd, uint8_t bytes);
      uint8_t WireReadBuff(uint8_t bytes);
      uint8_t WireReadChar(void);
      

   // ***********************************************************
   // PUBLIC SECTION
   // ***********************************************************
   #ifndef DEBUG
   public:
   #endif

      // PUBLIC DATA

      // PUBLIC METHODS

      // CONSTRUCTOR
      PC42();

      // COMPATIBILITY METHODS
      void digitalWrite(uint8_t ledNum, uint8_t value);
      int digitalRead(uint8_t keyNum);
      
      // SYSTEM METHODS
      signed char error(void);
      void begin(void);
      void begin(uint8_t Addr);
      void sysBegin(void);
      void sysVersion(char *str);
      int analogRead(uint8_t channel);
      void eepromWrite(uint8_t address, uint8_t data);
      uint8_t eepromRead(uint8_t address);
      uint8_t sysOsccal(void);
      void sysTest(uint8_t data1);
      uint16_t sysTest(uint8_t data1, uint16_t data2);

      // LED METHODS
      void ledBegin(void);
      void ledWrite(uint8_t ledNum, uint8_t value);
      void ledBlink(uint8_t ledNum, uint16_t time_on, uint16_t time_off);
      void ledBright(uint8_t ledNum, uint8_t value);

      // KEYBOARD METHODS
      void keyBegin(void);
      void keyBegin(uint8_t filter, uint8_t time1,
                    uint8_t time2, uint8_t time3,
                    uint8_t repeat2, uint8_t repeat3);
      uint8_t keyValue(uint8_t keyNum);
      uint8_t keyPressed(uint8_t keyNum);
      uint8_t keyToggle(uint8_t keyNum);
      uint8_t keyCount(uint8_t keyNum);
      uint16_t keyTimeOn(uint8_t keyNum);
      uint16_t keyTimeOff(uint8_t keyNum);
      uint8_t keyEvents(uint8_t keyNum, uint8_t event);
      void keyReset(void);

      // DISPLAY METHODS
      void dispBegin(void);
      void dispNum(uint16_t number);
      void dispNum(uint8_t digitNum, uint8_t number);
      void dispWrite(uint8_t digitNum, uint8_t segments);
      void dispWrite(uint8_t, uint8_t, uint8_t, uint8_t);
      void dispDots(uint8_t dots);

      // BUZZER METHODS
      void buzzBegin(void);
      void buzzOn(void);
      void buzzOff(void);
      void buzzFreq(int frequency);
      void buzzFreq(float frequency);
      void buzzTone(uint8_t tone);
      void buzzPlay(uint8_t tone, uint16_t time);
      uint8_t buzzPlay(void);
};


extern PC42 pc;

#endif
