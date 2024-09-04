# Kaizen LAN

### Frontend
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

### APIs and Microservices

![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/spring_boot_3-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java 17](https://img.shields.io/badge/java_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

### Datastore
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

# Description

**Kaizen LAN**: Continuously improve your life with helpful widgets - accessible around your home! 

This application offers a interface that contains many different self improvement tools. The goal is to provide the user a customized experience to help continuously improve their lives through the use of small and simple tools. The following tools are planned to be included in Kaizen LAN:

### Currently Implemented
- **Kaizen profile** (v1.0.0)
- **Weight tracking** (v1.0.0)
- **Water/hydration tracking** (v1.0.0)
- **Habit tracking** (v1.0.0)

### Planned to be Implemented
- **Routine checklists** (planned for v1.1.0)
- **Text/CSV editor** (planned for v1.1.0)
- **Time blocking** (planned for v1.1.0)
- **Journal** (planned for v1.1.0)
- **Food calorie tracking** (planned for v1.2.0)
- **Todo list** (planned for v1.2.0)
- **Countdown workout** (planned for v1.2.0)

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
- **[Java](https://openjdk.org/projects/jdk/) version 17 or higher** (used to build and run each API microservice)
- **[MySQL](https://www.mysql.com/) version 8 or higher** (used to run the MySQL database)
- **Bash or related terminal interface**
    - macOS testing using [Zsh](https://en.wikipedia.org/wiki/Z_shell) (preinstalled on macOS)
    - Linux (Ubuntu) testing using [GNOME](https://en.wikipedia.org/wiki/GNOME_Terminal) (preinstalled on Ubuntu 22)

## Dependencies

This application requires other applications to run alongside of it. These applications are outlined in the concept view.

**Currently Implemented**
- [Kaizen GraphQL API](https://github.com/narlock/kaizen-graphql-api)
- [Kaizen Profile API](https://github.com/narlock/kaizen-profile-api)
- [Narlock Weight Track API](https://github.com/narlock/narlock-weight-track-api)
- [Narlock Water Track API](https://github.com/narlock/narlock-water-track-api)
- [Narlock Habit Track API](https://github.com/narlock/narlock-habit-track-api)

**Dependencies that will be added in future releases**
- [Narlock Checklist API](https://github.com/narlock/narlock-checklist-api)
- [Narlock Notepad API](https://github.com/narlock/narlock-notepad-api)
- [Narlock Time Block API](https://github.com/narlock/narlock-time-block-api)
- [Narlock Journal API](https://github.com/narlock/narlock-journal-api)
- [Narlock Countdown Workout API](https://github.com/narlock/narlock-countdown-workout-api)

## Starting the app

You can download the latest release of the application from the **[releases page](https://github.com/narlock/KaizenLAN/releases)**. The package release will contain all the files needed to run the application.

### Initial Setup

For initial setup, you must have your MySQL instance running on your machine. Verify that you can open MySQL through the terminal by using the command and signing in with your MySQL password:
```bash
mysql -u {your_mysql_username} -p
```

If you are able to login with a username and password like this, you are ready to move forward with installing Kaizen. This README does not serve the purpose of showing users how to install MySQL on their device.

Run the `setup.sh` script in a bash-supported terminal. 
- You will be prompted to sign into your MySQL using the script. This script will create all of the tables used in KaizenLAN. It will also create a **secrets** folder which stores the MySQL credentials and LAN address in the user's `Documents/narlock/secrets/mysql.properties` file. The APIs that KaizenLAN utilizes will read from this properties file so that it can communicate with the MySQL database.
- Next, you will setup your profile. The script will prompt you to create a Kaizen username and enter various information for your profile. After this is completed, a row in the Profile and Health tables for the Kaizen Profile will be added to the MySQL database and the setup will be completed.

> [!NOTE]  
> If you are interested in viewing the contents of the bash script, you can open the script in a text editor, or view it online on [GitHub](https://github.com/narlock/KaizenLAN/blob/main/setup.sh)


### Run the application

Once the setup is complete, you can run the `start.sh` script in a new terminal. This will launch the Kaizen LAN server, Kaizen GraphQL API, and each microservice utilized by this application.

> [!WARNING]  
> By running this application, you are running roughly 6 applications on your system. RAM usage may vary.

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

# Concept Idea

Based off of [Kaizen](https://github.com/narlock/Kaizen), which is a local Java application that contains a Todo list, habit tracker, journal, and anti habit tracker, I wanted a way to be able to access the features that Kaizen offered through my devices without connecting to any outside server. The solution I came up with was hosting my own server and database and being able to connect to it through local area network. This means that all I have to do is run the server and its required applications, and I can get all of the benefits of Kaizen from any device connected to my network.

# First Iteration

![First Iteration](./readme%20assets/Interface.png)

The first iteration of Kaizen LAN introduces a simple interface utilizing the habit api, water tracking api, profile api, and weight tracking api. The navigation bar at the top of the screen expands on each of the widgets that appear on the home screen.

# Future Enhancements
- Multiprofile: require users to "login" to their profile. This allows multiple users to have a profile to utilize Kaizen LAN. This includes the functionality of providing a password (or not) to sign in to a profile.
