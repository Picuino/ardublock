/*
   tones.h - This file is part of Picuino Control Board PC42 
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

#ifndef __TONES_H
#define __TONES_H

#define  Silence    0   // F =    0,00 Hz
#define  Do0        1   // F =   16,35 Hz
#define  Do_0       2   // F =   17,32 Hz
#define  Re0        3   // F =   18,35 Hz
#define  Re_0       4   // F =   19,45 Hz
#define  Mi0        5   // F =   20,60 Hz
#define  Fa0        6   // F =   21,83 Hz
#define  Fa_0       7   // F =   23,12 Hz
#define  Sol0       8   // F =   24,50 Hz
#define  Sol_0      9   // F =   25,96 Hz
#define  La0       10   // F =   27,50 Hz
#define  La_0      11   // F =   29,14 Hz
#define  Si0       12   // F =   30,87 Hz
#define  Do1       13   // F =   32,70 Hz
#define  Do_1      14   // F =   34,65 Hz
#define  Re1       15   // F =   36,71 Hz
#define  Re_1      16   // F =   38,89 Hz
#define  Mi1       17   // F =   41,20 Hz
#define  Fa1       18   // F =   43,65 Hz
#define  Fa_1      19   // F =   46,25 Hz
#define  Sol1      20   // F =   49,00 Hz
#define  Sol_1     21   // F =   51,91 Hz
#define  La1       22   // F =   55,00 Hz
#define  La_1      23   // F =   58,27 Hz
#define  Si1       24   // F =   61,74 Hz
#define  Do2       25   // F =   65,41 Hz
#define  Do_2      26   // F =   69,30 Hz
#define  Re2       27   // F =   73,42 Hz
#define  Re_2      28   // F =   77,78 Hz
#define  Mi2       29   // F =   82,41 Hz
#define  Fa2       30   // F =   87,31 Hz
#define  Fa_2      31   // F =   92,50 Hz
#define  Sol2      32   // F =   98,00 Hz
#define  Sol_2     33   // F =  103,83 Hz
#define  La2       34   // F =  110,00 Hz
#define  La_2      35   // F =  116,54 Hz
#define  Si2       36   // F =  123,47 Hz
#define  Do3       37   // F =  130,81 Hz
#define  Do_3      38   // F =  138,59 Hz
#define  Re3       39   // F =  146,83 Hz
#define  Re_3      40   // F =  155,56 Hz
#define  Mi3       41   // F =  164,81 Hz
#define  Fa3       42   // F =  174,61 Hz
#define  Fa_3      43   // F =  185,00 Hz
#define  Sol3      44   // F =  196,00 Hz
#define  Sol_3     45   // F =  207,65 Hz
#define  La3       46   // F =  220,00 Hz
#define  La_3      47   // F =  233,08 Hz
#define  Si3       48   // F =  246,94 Hz
#define  Do4       49   // F =  261,63 Hz
#define  Do_4      50   // F =  277,18 Hz
#define  Re4       51   // F =  293,66 Hz
#define  Re_4      52   // F =  311,13 Hz
#define  Mi4       53   // F =  329,63 Hz
#define  Fa4       54   // F =  349,23 Hz
#define  Fa_4      55   // F =  369,99 Hz
#define  Sol4      56   // F =  392,00 Hz
#define  Sol_4     57   // F =  415,30 Hz
#define  La4       58   // F =  440,00 Hz
#define  La_4      59   // F =  466,16 Hz
#define  Si4       60   // F =  493,88 Hz
#define  Do5       61   // F =  523,25 Hz
#define  Do_5      62   // F =  554,37 Hz
#define  Re5       63   // F =  587,33 Hz
#define  Re_5      64   // F =  622,25 Hz
#define  Mi5       65   // F =  659,26 Hz
#define  Fa5       66   // F =  698,46 Hz
#define  Fa_5      67   // F =  739,99 Hz
#define  Sol5      68   // F =  783,99 Hz
#define  Sol_5     69   // F =  830,61 Hz
#define  La5       70   // F =  880,00 Hz
#define  La_5      71   // F =  932,33 Hz
#define  Si5       72   // F =  987,77 Hz
#define  Do6       73   // F = 1046,50 Hz
#define  Do_6      74   // F = 1108,73 Hz
#define  Re6       75   // F = 1174,66 Hz
#define  Re_6      76   // F = 1244,51 Hz
#define  Mi6       77   // F = 1318,51 Hz
#define  Fa6       78   // F = 1396,91 Hz
#define  Fa_6      79   // F = 1479,98 Hz
#define  Sol6      80   // F = 1567,98 Hz
#define  Sol_6     81   // F = 1661,22 Hz
#define  La6       82   // F = 1760,00 Hz
#define  La_6      83   // F = 1864,66 Hz
#define  Si6       84   // F = 1975,53 Hz
#define  Do7       85   // F = 2093,00 Hz
#define  Do_7      86   // F = 2217,46 Hz
#define  Re7       87   // F = 2349,32 Hz
#define  Re_7      88   // F = 2489,02 Hz
#define  Mi7       89   // F = 2637,02 Hz
#define  Fa7       90   // F = 2793,83 Hz
#define  Fa_7      91   // F = 2959,96 Hz
#define  Sol7      92   // F = 3135,96 Hz
#define  Sol_7     93   // F = 3322,44 Hz
#define  La7       94   // F = 3520,00 Hz
#define  La_7      95   // F = 3729,31 Hz
#define  Si7       96   // F = 3951,07 Hz
#define  Do8       97   // F = 4186,01 Hz
#define  Do_8      98   // F = 4434,92 Hz
#define  Re8       99   // F = 4698,64 Hz
#define  Re_8     100   // F = 4978,03 Hz
#define  Mi8      101   // F = 5274,04 Hz
#define  Fa8      102   // F = 5587,65 Hz
#define  Fa_8     103   // F = 5919,91 Hz
#define  Sol8     104   // F = 6271,93 Hz
#define  Sol_8    105   // F = 6644,88 Hz
#define  La8      106   // F = 7040,00 Hz
#define  La_8     107   // F = 7458,62 Hz
#define  Si8      108   // F = 7902,13 Hz
#define  Do9      109   // F = 8372,02 Hz
#define  Do_9     110   // F = 8869,84 Hz
#define  Re9      111   // F = 9397,27 Hz
#define  Re_9     112   // F = 9956,06 Hz
#define  Mi9      113   // F = 10548,08 Hz
#define  Fa9      114   // F = 11175,30 Hz
#define  Fa_9     115   // F = 11839,82 Hz
#define  Sol9     116   // F = 12543,85 Hz
#define  Sol_9    117   // F = 13289,75 Hz
#define  La9      118   // F = 14080,00 Hz
#define  La_9     119   // F = 14917,24 Hz
#define  Si9      120   // F = 15804,27 Hz
#define  Do10     121   // F = 16744,04 Hz
#define  Do_10    122   // F = 17739,69 Hz
#define  Re10     123   // F = 18794,55 Hz
#define  Re_10    124   // F = 19912,13 Hz
#define  Mi10     125   // F = 21096,16 Hz
#define  Fa10     126   // F = 22350,61 Hz
#define  Fa_10    127   // F = 23679,64 Hz

#endif
