#!/bin/bash
./stop-db.sh
docker run --name auth_service_db -d -p 3603:3306 -e MYSQL_ROOT_PASSWORD=password mysql:8.0