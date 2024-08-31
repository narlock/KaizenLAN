---
layout: page
title: Install Guide
permalink: /install/
---
<time>Aug 31, 2024</time>

# Local Installation
To utilize all of the features of Kaizen LAN, you will need to designate a computer system to run each part of the Kaizen LAN application. This includes the Kaizen LAN server, Kaizen GraphQL API, each microservice, and a MySQL database. To be able to run all of these, you must have the following:
- **[Node.js](https://nodejs.org/en) version 18 or higher** (used to run Kaizen LAN server and Kaizen Profile API)
- **[Java](https://openjdk.org/projects/jdk/) version 17 or higher** (used to build and run each API microservice)
- **[MySQL](https://www.mysql.com/) version 8 or higher** (used to run the MySQL database)
- **Bash or related terminal interface** (used to run the start and stop scripts)

You can download the latest release of the application from the **[releases page](https://github.com/narlock/KaizenLAN/releases)**. The package release will contain all the files needed to run the application.

### Initial Setup

For initial setup, you must have your MySQL instance running on your machine. Verify that you can open MySQL through the terminal by using the command and signing in with your MySQL password:
```bash
mysql -u {your_mysql_username} -p
```

If you are able to login with a username and password like this, you are ready to move forward with installing Kaizen. This README does not serve the purpose of showing users how to install MySQL on their device.

Run the `setup.sh` script in a bash-supported terminal. 
- You will be prompted to sign into your MySQL using the script. This script will create all of the tables used in KaizenLAN. It will also create a **secrets** folder which stores the MySQL credentials and LAN address in the user's `Documents/narlock/secrets/mysql.properties` file. The APIs that KaizenLAN utilizes will read from this properties file so that it can communicate with the MySQL database.
- Next, you will setup your profile. The script will prompt you to create a Kaizen username and enter various information for your profile. After this is completed, a row in the Profile and Health tables for the Kaizen Profile will be added to the MySQL database and the setup will be completed. If you are interested in viewing the contents of the bash script, you can open the script in a text editor, or view it online on [GitHub](https://github.com/narlock/KaizenLAN/blob/main/setup.sh)


### Run the application

Once the setup is complete, you can run the `start.sh` script in a new terminal. This will launch the Kaizen LAN server, Kaizen GraphQL API, and each microservice utilized by this application.

❗️ By running this application, you are running roughly 6 applications on your system. RAM usage may vary.

When you run the application, you will receive the messages indicating which applications are running on which port number:
```sh
Starting Kaizen Profile API{} on port 8079
Starting Narlock Habit API{} on port 8089
Starting Narlock Water Track API{} on port 8083
Starting Narlock Weight Track API{} on port 8081
Starting Narlock GraphQL API{} on port 8080
Starting Kaizen LAN{} on port 3000
```

Once the script completes, you can choose to exit the terminal if you wish. Next, you can navigate to **http://localhost:3000/** to access Kaizen LAN. On other devices within your local network, you can navigate to your LAN **http://{lan_address}:3000/** on any of the devices connected to your network to use the application. You can find the exact address in the secrets properties folder that was created during the setup if you cannot find this address.

### Stopping the application

When the start script is ran, it will run all of the required applications for Kaizen LAN in the background. The stop script will end each process that was opened by the start script. Simply just run the `stop.sh` script in a terminal. This will kill each process that is running on the ports above.
