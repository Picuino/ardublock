@echo off
set TARGET=ardublock-picuino.zip
set ardu_tool_path=tools/ArduBlockTool/tool

set mingw32=/Bin/mingw32/bin
set mkdir=%mingw32%/mkdir.exe
set copy=%mingw32%/cp.exe
set remove=%mingw32%/rm.exe
set zip=/Bin/7-zip/7z.exe

cd ../build
%mkdir% -p %ardu_tool_path%
%copy% -f ardublock-all.jar %ardu_tool_path%
%remove% %TARGET%
%zip% a -r %TARGET% %ardu_tool_path%/*

pause