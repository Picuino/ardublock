@echo off
set TARGET=../build/ardublock-picuino.zip
set PICUINO-STATIC=../../picuino-staticweb/_download

set mingw32=/Bin/mingw32/bin
set copy=%mingw32%/cp.exe

%copy% -f %TARGET%  %PICUINO-STATIC%

pause