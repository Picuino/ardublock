@set python=/Bin/Python27/python27.exe
@set mingw=/Bin/mingw32/bin

@set java=../src/main/java/com/ardublock/translator/block
@set src_translate=../src/main/resources/com/ardublock/block
@set target=../target/classes/com/ardublock/block


%mingw%/cp -rf build/block/*   %java%
%mingw%/cp -f  build/ardublock.xml            %src_translate%
%mingw%/cp -f  build/ardublock.properties     %src_translate%
%mingw%/cp -f  build/block-mapping.properties %src_translate%


%mingw%/cp -f  build/ardublock.xml            %target%
%mingw%/cp -f  build/ardublock.properties     %target%
%mingw%/cp -f  build/block-mapping.properties %target%


pause