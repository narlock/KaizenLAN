#!/bin/bash

# ANSI color codes
GRE='\033[0;32m'
RED='\033[0;31m'
MAG='\033[0;35m'
CYA='\033[0;36m'
NC='\033[0m'

# Change directory to api
cd api

# Start Kaizen Profile API
echo -e "${MAG}Starting ${GRE}Kaizen Profile API{$MAG} on port 8079${NC}"
nohup java -jar kaizen-profile-api.jar &

# Start Narlock Habit API
echo -e "${MAG}Starting ${GRE}Narlock Habit API{$MAG} on port 8089${NC}"
nohup java -jar narlock-habit-api.jar &

# Start Narlock Water Track API
echo -e "${MAG}Starting ${GRE}Narlock Water Track API{$MAG} on port 8083${NC}"
nohup java -jar narlock-water-track-api.jar &

# Start Narlock Weight Track API
echo -e "${MAG}Starting ${GRE}Narlock Weight Track API{$MAG} on port 8081${NC}"
nohup java -jar narlock-weight-track-api.jar &

# Start Kaizen GraphQL API
echo -e "${MAG}Starting ${GRE}Narlock GraphQL API{$MAG} on port 8080${NC}"
nohup java -jar kaizen-graphql-api.jar &

# Change directory to KaizenLAN server
cd ../KaizenLAN

# Start KaizenLAN
echo -e "${MAG}Starting ${GRE}Kaizen LAN{$MAG} on port 3000${NC}"
nohup node server.js &

# Detach from terminal
exit