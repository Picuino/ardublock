/*
   display.h - This file is part of Picuino Control Board PC42 
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

#ifndef __DISPLAY_H
#define __DISPLAY_H


// Seven Segment code of chars
#define DD_0    0b00111111  // 0
#define DD_1    0b00000110  // 1
#define DD_2    0b01011011  // 2
#define DD_3    0b01001111  // 3
#define DD_4    0b01100110  // 4
#define DD_5    0b01101101  // 5
#define DD_6    0b01111101  // 6
#define DD_7    0b00000111  // 7
#define DD_8    0b01111111  // 8
#define DD_9    0b01101111  // 9
#define DD_A    0b01110111  // A
#define DD_b    0b01111100  // b
#define DD_b    0b01111100  // b
#define DD_B    DD_8        // B
#define DD_C    0b00111001  // C
#define DD_d    0b01011110  // d
#define DD_E    0b01111001  // E
#define DD_F    0b01110001  // F
#define DD_G    0b00111101  // G
#define DD_g    0b01101111  // g
#define DD_H    0b01110110  // H
#define DD_h    0b01110100  // h
#define DD_I    0b00110000  // I
#define DD_i    0b00000100  // i
#define DD_J    0b00011110  // J
#define DD_K    0b01110000  // |-
#define DD_L    0b00111000  // L
#define DD_n    0b01010100  // n
#define DD_ny   0b01010101  // ñ
#define DD_o    0b01011100  // o
#define DD_O    DD_0        // O
#define DD_P    0b01110011  // P
#define DD_q    0b01100111  // q
#define DD_r    0b01010000  // r
#define DD_S    0b01101101  // S
#define DD_t    0b01111000  // t
#define DD_u    0b00011100  // u
#define DD_U    0b00111110  // U
#define DD_y    0b01100110  // y
#define DD_Y    0b01101110  // Y
#define DD_Z    DD_2        // Z
#define DD_SP   0b00000000  // BLANK SPACE


#endif






