@echo off
set TARGET=arduino-libraries.zip
set ardu_lib_path=libraries

set mingw32=/Bin/mingw32/bin
set mkdir=%mingw32%/mkdir.exe
set copy=%mingw32%/cp.exe
set remove=%mingw32%/rm.exe
set zip=/Bin/7-zip/7z.exe


%copy% -rf arduino-libraries/*  ../build/%ardu_lib_path%
cd ../build
%mkdir% -p %ardu_lib_path%
%remove% -p %TARGET%
%zip% a -r %TARGET% %ardu_lib_path%/*

pause