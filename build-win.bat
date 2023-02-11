@echo off
call win-pre.bat

java -jar packr.jar win-packr.json

call win-post.bat