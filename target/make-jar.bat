@echo off

set java="C:\Program Files\Java\jdk1.8.0_111\bin\jar.exe"
set source1="classes"
set source2="../../openblocks/target/classes"

%java% cfe ardublock-all.jar com.ardublock.Main -C %source1% . -C %source2% .

pause