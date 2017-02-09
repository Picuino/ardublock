@echo off

set java="\Bin\jdk180\bin\jar.exe"
set source1="../target/classes"
set source2="../../picuino-openblocks/target/classes"

%java% cfm ardublock-all.jar Manifest.txt -C %source1% . -C %source2% .

pause