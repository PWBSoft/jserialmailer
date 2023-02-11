for /f %%i in ('call mvn -q --non-recursive "-Dexec.executable=cmd" "-Dexec.args=/C echo ${project.version}" "org.codehaus.mojo:exec-maven-plugin:1.3.1:exec"') do set VERSION=%%i

echo %VERSION%

set dt=%DATE:~6,4%_%DATE:~3,2%_%DATE:~0,2%__%TIME:~0,2%_%TIME:~3,2%_%TIME:~6,2%
set dt=%dt: =0%


rcedit.exe "out-win/JSerialMailer.exe" --set-icon "out-win/resources/icon.ico" --set-version-string "FileVersion" "%VERSION%-%dt%"
rcedit.exe "out-win/JSerialMailer.exe" --set-version-string "CompanyName" "Sofia Vicedomini"
rcedit.exe "out-win/JSerialMailer.exe" --set-version-string "FileDescription" "Send Transactional EMails from your desktop"
rcedit.exe "out-win/JSerialMailer.exe" --set-version-string "LegalCopyright" "(C) 2023 - Sofia Vicedomini - GPLv3"
rcedit.exe "out-win/JSerialMailer.exe" --set-version-string "OriginalFilename" "JSerialMailer.exe"
rcedit.exe "out-win/JSerialMailer.exe" --set-version-string "ProductName" "JSerialMailer"
rcedit.exe "out-win/JSerialMailer.exe" --set-version-string "ProductVersion" "%VERSION%"