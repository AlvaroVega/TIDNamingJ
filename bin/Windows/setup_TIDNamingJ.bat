@echo off

rem Comprueba si hay un directorio como argumento para agregarlo al classpath
if "%1"=="dir" goto classpath

rem Comprueba si TID_NAMINGJ_HOME, JAVA_HOME y JDBC_HOME estan definidas
if "%TID_NAMINGJ_HOME%"=="" goto error_HOME
if "%JAVA_HOME%"=="" goto error_JDK
if "%JDBC_HOME%"=="" goto error_JDBC
	set SRV_DIR=%TID_NAMINGJ_HOME%
	set SRV_BIN=%SRV_DIR%\bin
	set SRV_LIB=%SRV_DIR%\lib
	set SRV_TOOLS=%SRV_BIN%\tools

	rem Configura el PATH para los procesos del servicio
	PATH %PATH%;%SRV_BIN%

	rem Configura el PATH para las herramientas del servicio
	PATH %PATH%;%SRV_TOOLS%

	rem Configura el CLASSPATH con las librerias del servicio
	for %%L in ("%SRV_LIB%"\*.jar) do call %0 dir "%%L"

	rem Configura el CLASSPATH para JDBC
	echo %CLASSPATH% | find "%JDBC_HOME%\lib\classes12.zip" > NUL:
	if errorlevel 1 call %0 dir "%JDBC_HOME%\lib\classes12.zip"

	set SRV_DIR=
	set SRV_BIN=
	set SRV_LIB=
	set SRV_TOOLS=
	goto end

:classpath
	set !L=%2
	set !L=%!L:"=%
	set CLASSPATH=%CLASSPATH%;%!L%
	set !L=
	goto end

:error_HOME
	echo Debe configurar la variable de entorno TID_NAMINGJ_HOME
	echo.
	goto end

:error_JDK
	echo Debe configurar la variable de entorno JAVA_HOME
	echo.
	goto end

:error_JDBC
	echo Debe configurar la variable de entorno JDBC_HOME
	echo.

:end
