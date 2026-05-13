@REM Maven wrapper script for Windows
@echo off
setlocal

set MAVEN_WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES=.mvn\wrapper\maven-wrapper.properties

if exist "%MAVEN_WRAPPER_JAR%" goto execute

@REM Download wrapper jar using PowerShell
for /f "tokens=2 delims==" %%i in ('findstr /i "wrapperUrl" "%MAVEN_WRAPPER_PROPERTIES%"') do set DOWNLOAD_URL=%%i
powershell -Command "(new-object System.Net.WebClient).DownloadFile('%DOWNLOAD_URL%', '%MAVEN_WRAPPER_JAR%')"

:execute
"%JAVA_HOME%\bin\java.exe" -jar "%MAVEN_WRAPPER_JAR%" %*
