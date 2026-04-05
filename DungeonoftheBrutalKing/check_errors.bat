@echo off
setlocal

set "ROOT=G:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing"
set "BIN=%ROOT%\bin"
set "SRC_LIST=%ROOT%\compile_sources.list"
set "ERR_FILE=%ROOT%\full_compile_errors.txt"

echo [1/3] Compiling sources...
javac -d "%BIN%" @"%SRC_LIST%" 2> "%ERR_FILE%"
set "JAVAC_EXIT=%ERRORLEVEL%"

echo.
echo [2/3] Counting compile errors...
find /c ": error:" "%ERR_FILE%"

echo.
echo [3/3] Showing first 80 lines from full_compile_errors.txt
powershell -NoProfile -Command "Get-Content '%ERR_FILE%' -TotalCount 80"

echo.
if "%JAVAC_EXIT%"=="0" (
    echo Compile completed with exit code 0.
) else (
    echo Compile finished with javac exit code %JAVAC_EXIT%.
)
echo Error file: "%ERR_FILE%"

endlocal
exit /b %JAVAC_EXIT%
