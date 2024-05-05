# Kaizen LAN

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
- **Food calorie tracking** (planned for v1.0.0)
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

## Starting the app

Each microservice contains it's own documentation for how to run. The important thing to note is that you do not modify any ports that they run on, as Kaizen LAN is configured to call each microservice by their default port.

You must have a configured MySQL server running, and have ran each database setup for this application and each microservice. You also need to make sure that each microservice is properly configured.

Once each microservice is running, you can start the Kaizen LAN server by using `node server.js` in your terminal. The server will start on port `3000`. For connecting to this server, you can use `http://localhost:3000/` from the server computer, or connect to it by finding your LAN address and going to port 3000. This document assumes the reader knows how to obtain the LAN address of their server computer.

# Concept Idea

Based off of [Kaizen](https://github.com/narlock/Kaizen), which is a local Java application that contains a Todo list, habit tracker, journal, and anti habit tracker, I wanted a way to be able to access the features that Kaizen offered through my devices without connecting to any outside server. The solution I came up with was hosting my own server and database and being able to connect to it through local area network. This means that all I have to do is run the server and its required applications, and I can get all of the benefits of Kaizen from any device connected to my network.

# First Iteration

![First Iteration](./readme%20assets/Interface.png)

The first iteration of Kaizen LAN introduces a simple interface utilizing the checklist api, water tracking api, profile api, and weight tracking api. The navigation bar at the top of the screen expands on each of the widgets that appear on the home screen.

# Future Enhancements
- Multiprofile: require users to "login" to their profile. This allows multiple users to have a profile to utilize Kaizen LAN.
    - This includes the functionality of providing a password (or not) to sign in to a profile.
- Anti Habit API integration
- Ability to pick and choose what widgets to see on the home screen.