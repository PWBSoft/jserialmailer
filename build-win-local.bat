@echo off
call win-pre.bat

java -jar packr.jar win-packr-local.json

call win-post.bat