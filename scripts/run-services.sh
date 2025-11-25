#!/bin/bash

echo "Starting Book Catalogue Services..."
echo

echo "Building Management Service..."
cd ../management-service
mvn clean package -q
echo "Management Service built successfully!"

echo "Building Web Service..."
cd ../web-service
mvn clean package -q
echo "Web Service built successfully!"

echo
echo "Starting Management Service on port 8081..."
cd ..
java -jar management-service/target/management-service-1.0.0.jar &

sleep 5

echo "Starting Web Service on port 8080..."
java -jar web-service/target/web-service-1.0.0.jar &

echo
echo "========================================"
echo "Services started successfully!"
echo "========================================"
echo "Management Service: http://localhost:8081"
echo "Web Service (UI): http://localhost:8080/books"
echo "H2 Console: http://localhost:8081/h2-console"
echo "========================================"
echo