@set mingw=/Bin/mingw32/bin
@set java=../../src/main/java/com/ardublock/translator/block
@set translate =../../src/main/resources/com/ardublock/block

%mingw%/cp build/block/* %java%

%mingw%/cp build/ardublock.xml            %translate%
%mingw%/cp build/ardublock.properties     %translate%
%mingw%/cp build/block-mapping.properties %translate%

