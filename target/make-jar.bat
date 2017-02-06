@echo off

set java="C:\Program Files\Java\jdk1.8.0_111\bin\jar.exe"
set source1="classes"
set source2="../../picuino-openblocks/target/classes"

%java% cfm ardublock-all.jar Manifest.txt -C %source1% . -C %source2% .

pause