@echo off
echo Starting Book Catalogue Services...
echo.

echo Building Management Service...
cd ..\management-service
call mvn clean package -q
echo Management Service built successfully!

echo Building Web Service...
cd ..\web-service
call mvn clean package -q
echo Web Service built successfully!

echo.
echo Starting Management Service on port 8081...
cd ..
start "Management Service" cmd /k "java -jar management-service\target\management-service-1.0.0.jar

timeout /t 5

echo Starting Web Service on port 8080...
start "Web Service" cmd /k "java -jar web-service\target\web-service-1.0.0.jar

echo.
echo ========================================
echo Services started successfully!
echo ========================================
echo Management Service: http://localhost:8081
echo Web Service (UI): http://localhost:8080/books
echo H2 Console: http://localhost:8081/h2-console
echo ========================================
echo.
pause