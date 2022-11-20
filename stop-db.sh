#!/bin/bash
docker stop auth_service_db 2> /dev/null | grep "stop"
docker rm auth_service_db 2> /dev/null | grep "remove"