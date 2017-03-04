rem @echo off

set src=..\build\ardublock-all.jar
set dest=D:\Datos\User\Arduino
set ardublock-path=tools\ArduBlockTool\tool

copy %src%  %dest%\%ardublock-path%

pause