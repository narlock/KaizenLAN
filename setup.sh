#!/bin/bash

# ANSI color codes
GRE='\033[0;32m'
RED='\033[0;31m'
MAG='\033[0;35m'
CYA='\033[0;36m'
NC='\033[0m' # No Color

echo -e "${MAG}====================================================================${NC}"
echo -e "                  ${MAG}Welcome to the ${GRE}KaizenLAN ${MAG}Setup${NC}"
echo -e "          ${MAG}Created by narlock -- ${CYA}Version Beta v1.0.0${NC}"
echo -e "${MAG}Please make sure you have read the README before running the setup.${NC}"
echo -e "${MAG}====================================================================${NC}"

# Prompt the user for MySQL credentials
echo -e "${RED}Enter MySQL username: ${NC}"
read USER
echo -e "${RED}Enter MySQL password: ${NC}"
read -s PASSWORD
echo
DATABASE="narlock"
SQL_FILE="setup_database.sql"

# Check if the database exists and create it if it doesn't
DB_EXISTS=$(
    mysql -u $USER -p$PASSWORD -e "SHOW DATABASES LIKE '$DATABASE';" | grep "$DATABASE" >/dev/null
    echo "$?"
)

if [ $DB_EXISTS -eq 0 ]; then
    echo "Database $DATABASE already exists."
else
    echo "Database $DATABASE does not exist. Creating database..."
    mysql -u $USER -p$PASSWORD -e "CREATE DATABASE $DATABASE;"
    if [ $? -eq 0 ]; then
        echo -e "${GRE}Database $DATABASE created successfully.${NC}"
    else
        echo "Failed to create database $DATABASE."
        exit 1
    fi
fi

# Execute the SQL file
mysql -u $USER -p$PASSWORD $DATABASE <$SQL_FILE

if [ $? -eq 0 ]; then
    echo -e "${GRE}SQL script executed successfully.${NC}"

    if command -v ifconfig >/dev/null 2>&1; then
        ip_address=$(ifconfig | grep 'inet ' | awk '{print $2}' | grep '192\.168\.0\.')
    elif command -v ip >/dev/null 2>&1; then
        ip_address=$(ip addr show | grep 'inet ' | grep '192\.168\.0\.' | awk '{print $2}' | cut -d'/' -f1)
    else
        echo "Neither ifconfig nor ip command found."
        exit 1
    fi

    if [ -n "$ip_address" ]; then
        echo "The IP address is: $ip_address"
    else
        echo "No 192.168.0.x IP address found."
    fi

    # Create the properties file in the user's home directory
    PROPERTIES_FILE="$HOME/Documents/narlock/secrets/mysql.properties"
    mkdir -p "$(dirname "$PROPERTIES_FILE")" # Create the directory if it doesn't exist

    echo "lmysql.username=$USER" >"$PROPERTIES_FILE"
    echo "lmysql.password=$PASSWORD" >>"$PROPERTIES_FILE"
    echo "lan.address=$ip_address" >>"$PROPERTIES_FILE"

    echo -e "${GRE}Properties file created at $PROPERTIES_FILE${NC}"

    # Modify the kaizenGraphInterface.js file
    KAIZEN_GRAPH_FILE="$(pwd)/KaizenLAN/public/lib/graph/kaizenGraphInterface.js"
    if [ -f "$KAIZEN_GRAPH_FILE" ]; then
        sed -i.bak "s|const GRAPH_ENDPOINT = .*|const GRAPH_ENDPOINT = \"http://$ip_address:8080/graphql\";|" "$KAIZEN_GRAPH_FILE"
        if [ $? -eq 0 ]; then
            echo -e "${GRE}Updated GRAPH_ENDPOINT in $KAIZEN_GRAPH_FILE with LAN address $ip_address${NC}"
        else
            echo "Failed to update GRAPH_ENDPOINT in $KAIZEN_GRAPH_FILE."
            exit 1
        fi
    else
        echo "File $KAIZEN_GRAPH_FILE not found."
        exit 1
    fi

else
    echo "Failed to execute SQL script."
fi

# Prompt the user for MySQL credentials
echo -e "${RED}Create a Kaizen username: ${NC}"
read KAIZEN_USER
echo -e "${RED}Enter birthdate (yyyy-MM-dd): ${NC}"
read KAIZEN_BIRTHDATE
echo -e "${RED}Enter weight: ${NC}"
read KAIZEN_WEIGHT
echo -e "${RED}Enter goal weight: ${NC}"
read KAIZEN_WEIGHT_GOAL
echo -e "${RED}Enter height: ${NC}"
read KAIZEN_HEIGHT
echo -e "${RED}Enter water intake goal: ${NC}"
read KAIZEN_WATER_GOAL

# Default values
KAIZEN_XP=100
KAIZEN_NUM_ROWS=0
KAIZEN_PIN="1234"

# Insert into Profile table
INSERT_PROFILE="INSERT INTO Profile (id, username, birth_date, xp, num_rows, pin) VALUES (1, '$KAIZEN_USER', '$KAIZEN_BIRTHDATE', $KAIZEN_XP, $KAIZEN_NUM_ROWS, '$KAIZEN_PIN');"
mysql -u $USER -p$PASSWORD $DATABASE -e "$INSERT_PROFILE"

if [ $? -eq 0 ]; then
    echo -e "${GRE}Profile entry created successfully.${NC}"
else
    echo "Failed to create Profile entry."
    exit 1
fi

# Get the last inserted id from Profile
PROFILE_ID=$(mysql -u $USER -p$PASSWORD $DATABASE -se "SELECT LAST_INSERT_ID();")

# Insert into Health table
INSERT_HEALTH="INSERT INTO Health (profile_id, height, weight, goal_weight, goal_water) VALUES (1, $KAIZEN_HEIGHT, $KAIZEN_WEIGHT, $KAIZEN_WEIGHT_GOAL, $KAIZEN_WATER_GOAL);"
mysql -u $USER -p$PASSWORD $DATABASE -e "$INSERT_HEALTH"

if [ $? -eq 0 ]; then
    echo -e "${GRE}Health entry created successfully.${NC}"
else
    echo "Failed to create Health entry."
    exit 1
fi

# Move to application directory
cd KaizenLAN

# Define the node modules you want to check and install
modules=("express")

# Check if the node_modules directory exists
if [ ! -d "node_modules" ]; then
    echo "node_modules directory does not exist. Creating and installing all modules..."
    npm install
else
    echo "node_modules directory exists. Checking for missing modules..."
    for module in "${modules[@]}"; do
        if [ ! -d "node_modules/$module" ]; then
            echo "Module $module is not installed. Installing..."
            npm install "$module"
        else
            echo "Module $module is already installed."
        fi
    done
fi

echo -e "${MAG}KaizenLAN Beta v1.0.0 Setup Completed. Run ${CYA}start.sh${MAG} to start the application${NC}"
