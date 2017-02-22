@echo off

set PATH=D:\Bin\jdk180;D:\Bin\apache-maven\bin;%PATH%
set JAVA_HOME=D:\Bin\jdk180
set M2_HOME=D:\Bin\apache-maven

set MAVEN_BATCH_ECHO=off
set MAVEN_BATCH_PAUSE=on

cd..

rem Installation
rem mvn validate


rem Make all project
rem mvn clean package


rem Compile new sourcecode
mvn compile
