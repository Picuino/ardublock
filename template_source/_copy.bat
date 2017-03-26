@set python=/Bin/Python27/python27.exe
@set mingw=/Bin/mingw32/bin

@set java=../src/main/java/com/ardublock/translator/block
@set translate=../src/main/resources/com/ardublock/block

%mingw%/cp -rf build/block/*   %java%
%mingw%/cp -f  build/ardublock.xml            %translate%
%mingw%/cp -f  build/ardublock.properties     %translate%
%mingw%/cp -f  build/block-mapping.properties %translate%

pause