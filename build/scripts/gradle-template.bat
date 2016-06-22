@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  gradle-template startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_TEMPLATE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\gradle-template.jar;%APP_HOME%\lib\unirest-java-1.4.6.jar;%APP_HOME%\lib\spring-context-4.2.5.RELEASE.jar;%APP_HOME%\lib\spark-core-2.5.jar;%APP_HOME%\lib\httpclient-4.3.6.jar;%APP_HOME%\lib\httpasyncclient-4.0.2.jar;%APP_HOME%\lib\httpmime-4.3.6.jar;%APP_HOME%\lib\json-20140107.jar;%APP_HOME%\lib\spring-aop-4.2.5.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.2.5.RELEASE.jar;%APP_HOME%\lib\spring-core-4.2.5.RELEASE.jar;%APP_HOME%\lib\spring-expression-4.2.5.RELEASE.jar;%APP_HOME%\lib\slf4j-api-1.7.13.jar;%APP_HOME%\lib\jetty-server-9.3.6.v20151106.jar;%APP_HOME%\lib\jetty-webapp-9.3.6.v20151106.jar;%APP_HOME%\lib\websocket-server-9.3.6.v20151106.jar;%APP_HOME%\lib\websocket-servlet-9.3.6.v20151106.jar;%APP_HOME%\lib\httpcore-4.3.3.jar;%APP_HOME%\lib\commons-codec-1.6.jar;%APP_HOME%\lib\httpcore-nio-4.3.2.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\javax.servlet-api-3.1.0.jar;%APP_HOME%\lib\jetty-http-9.3.6.v20151106.jar;%APP_HOME%\lib\jetty-io-9.3.6.v20151106.jar;%APP_HOME%\lib\jetty-xml-9.3.6.v20151106.jar;%APP_HOME%\lib\jetty-servlet-9.3.6.v20151106.jar;%APP_HOME%\lib\websocket-common-9.3.6.v20151106.jar;%APP_HOME%\lib\websocket-client-9.3.6.v20151106.jar;%APP_HOME%\lib\websocket-api-9.3.6.v20151106.jar;%APP_HOME%\lib\jetty-util-9.3.6.v20151106.jar;%APP_HOME%\lib\jetty-security-9.3.6.v20151106.jar;%APP_HOME%\lib\commons-logging-1.2.jar

@rem Execute gradle-template
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_TEMPLATE_OPTS%  -classpath "%CLASSPATH%" app.Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE_TEMPLATE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%GRADLE_TEMPLATE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
