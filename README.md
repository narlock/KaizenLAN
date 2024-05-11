# Kaizen LAN

> [!NOTE]  
> This README is currently a **plan** for Kaizen LAN. There are no current releases at this time and the first version is in development.

### Frontend
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

### APIs and Microservices

![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/spring_boot_3-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java 17](https://img.shields.io/badge/java_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)

### Datastore
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

# Description

**Kaizen LAN**: Continuously improve your life with helpful widgets - accessible around your home! 

This application offers a interface that contains many different self improvement tools. The goal is to provide the user a customized experience to help continuously improve their lives through the use of small and simple tools. The following tools are planned to be included in Kaizen LAN:
- **Kaizen profile** (planned for v1.0.0)
- **Morning, daily, and night checklists** (planned for v1.0.0)
- **Weight tracking** (planned for v1.0.0)
- **Water/hydration tracking** (planned for v1.0.0)
- **Habit tracking** (planned for v1.0.0)
- **Text/CSV editor** (planned for v1.0.0)
- **Time blocking** (planned for v1.0.0)
- **Journal** (planned for v1.0.0)
- **Countdown workout** (planned for v1.0.0)
- **Food calorie tracking** (planned for v1.1.0)
- **Todo list** (planned for v1.1.0)

# Concept View

![Concept View](./readme%20assets/Concept.png)

| Color  | Description |
| ------------- | ------------- |
| Green  | Application running with Node  |
| Orange  | Application running with Java  |
| Blue  | Database from MySQL  |

# Running Kaizen LAN

## Requirements

To utilize all of the features of Kaizen LAN, you will need to designate a computer system to run each part of the Kaizen LAN application. This includes the Kaizen LAN server, Kaizen GraphQL API, each microservice, and a MySQL database. To be able to run all of these, you must have the following:
- **[Node.js](https://nodejs.org/en) version 18 or higher** (used to run Kaizen LAN server and Kaizen Profile API)
- **[Java](https://openjdk.org/projects/jdk/) version 17 or higher** and **Maven version 3 or higher** (used to build and run each microservice)
- **[MySQL](https://www.mysql.com/) version 8 or higher** (used to run the MySQL database)
- **Bash or related terminal interface**
    - macOS testing using [Zsh](https://en.wikipedia.org/wiki/Z_shell) (preinstalled on macOS)
    - Linux (Ubuntu) testing using [GNOME](https://en.wikipedia.org/wiki/GNOME_Terminal) (preinstalled on Ubuntu 22)
    - Windows testing using [Git Bash](https://git-scm.com/downloads) (NOT preinstalled)

## Dependencies

This application requires other applications to run alongside of it. These applications are outlined in the concept view.
- [Kaizen GraphQL API](https://github.com/narlock/kaizen-graphql-api)
- [Kaizen Profile API](https://github.com/narlock/kaizen-profile-api)
- [Narlock Checklist API](https://github.com/narlock/narlock-checklist-api)
- [Narlock Weight Track API](https://github.com/narlock/narlock-weight-track-api)
- [Narlock Water Track API](https://github.com/narlock/narlock-water-track-api)
- [Narlock Habit Track API](https://github.com/narlock/narlock-habit-track-api)
- [Narlock Notepad API](https://github.com/narlock/narlock-notepad-api)
- [Narlock Time Block API](https://github.com/narlock/narlock-time-block-api)
- [Narlock Journal API](https://github.com/narlock/narlock-journal-api)
- [Narlock Countdown Workout API](https://github.com/narlock/narlock-countdown-workout-api)

## Starting the app

You can download the latest release of the application from the **[releases page](https://github.com/narlock/KaizenLAN/releases)**. The package release will contain all the files needed to run the application.

### Initial Setup

For initial setup, you must have your MySQL instance running on your machine. Open the `setup.sh` file to configure with your MySQL credentials:
```bash
#!/bin/bash

# MySQL credentials
MYSQL_USER="your_mysql_username"
MYSQL_PASSWORD="your_mysql_password"
MYSQL_HOST="localhost"
MYSQL_DB="your_database_name"
```
Specifically, you will need to modify the `MYSQL_USER`, `MYSQL_PASSWORD`, and `MYSQL_DB` values. Kaizen LAN was designed to run on the local machine, so there is no need to modify the host value.

Running this script will create and configure the MySQL database along with each table utilized in this application. If the setup was successful, you will see a message that reads "`SUCCESS: Kaizen LAN setup complete`". If the setup was unsuccessful, you will see a message that reads "`FAILURE: Setup was unable to complete`".

### Run the application

<!-- ** TODO - add information regarding LAN setup ** -->

Once the setup is complete, you can run the `start.sh` script in a new terminal. This will launch the Kaizen LAN server, Kaizen GraphQL API, and each microservice utilized by this application.

> [!WARNING]  
> By running this application, you are running roughly 10+ applications on your system. RAM usage may vary.

Once the script completes, you can choose to exit the terminal if you wish. Next, you can navigate to **http://localhost:3000/** to access Kaizen LAN.

### Stopping the application

When the start script is ran, it will run all of the required applications for Kaizen LAN in the background. The stop script will end each process that was opened by the start script. Simply just run the `stop.sh` script in a terminal.

# Concept Idea

Based off of [Kaizen](https://github.com/narlock/Kaizen), which is a local Java application that contains a Todo list, habit tracker, journal, and anti habit tracker, I wanted a way to be able to access the features that Kaizen offered through my devices without connecting to any outside server. The solution I came up with was hosting my own server and database and being able to connect to it through local area network. This means that all I have to do is run the server and its required applications, and I can get all of the benefits of Kaizen from any device connected to my network.

# First Iteration

![First Iteration](./readme%20assets/Interface.png)

The first iteration of Kaizen LAN introduces a simple interface utilizing the checklist api, water tracking api, profile api, and weight tracking api. The navigation bar at the top of the screen expands on each of the widgets that appear on the home screen.

# Future Enhancements
- Multiprofile: require users to "login" to their profile. This allows multiple users to have a profile to utilize Kaizen LAN.
    - This includes the functionality of providing a password (or not) to sign in to a profile.
- Anti Habit API integration