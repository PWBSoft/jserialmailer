@echo off
if exist out-win\ (
  rmdir /s /q out-win
)

call mvn clean install

java -jar packr.jar win-packr.json