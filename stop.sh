#!/bin/bash

# Array of ports to check
ports=(3000 8079 8080 8085 8089 8081 8083)

# Iterate through each port
for port in "${ports[@]}"; do
    # Check if any process is running on the port
    pid=$(lsof -ti:$port)
    if [ -n "$pid" ]; then
        echo "Process running on port $port with PID $pid. Killing..."
        # Kill the process
        kill -9 $pid
        echo "Process killed."
    else
        echo "No process running on port $port."
    fi
done