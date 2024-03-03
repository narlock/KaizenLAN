# KaizenLAN

![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

**KaizenLAN** - Continuously improve your life with helpful widgets - accessible around your home! This application offers a interface that contains many different self improvement tools. These tools are offered by simple APIs that connect to a backend database. These features include:

1. Kaizen Profile provided by **[Kaizen Profile API](https://github.com/narlock/KaizenLAN/tree/main/kaizen-profile-api)** (requires Node JS)
2. Habit Tracking provided by **[Simple Habit Track API]()** (requires Java 17)
3. Schedule Calendar provided by **[Simple Time Block API](https://github.com/narlock/simple-time-block-api)** (requires Java 17)
4. Weight Tracking provided by **[Simple Weight Track API](https://github.com/narlock/simple-weight-track-api)** (requires Java 17)
5. Water / Hydration Tracking provided by **[Simple Water Track API](https://github.com/narlock/simple-water-track-api)** (requires Java 17)
6. Journal provided by **[Simple Journal API](https://github.com/narlock/simple-journal-api)** (requires Java 17)

This program's intention is to run the server, the MySQL database, and each of these microservices on the same computer system within the desired local area network. From there, any device connected to the network can access Kaizen. In simple terms, one LAN device will run the Kaizen server application, and any other LAN device can use a web browser to interact with the application.

![Concept View](./readme%20assets/Concept.png)

**Legend**
- Grey: LAN devices (computer, smartphone, etc.)
- Green: Software that runs using Node.js
- Orange: Software that runs using Java
- Blue: MySQL database

## Running Kaizen LAN

### Requirements

To utilize all of the features of Kaizen LAN, you will need to designate a computer system to run the Kaizen LAN server. This computer system will run the Kaizen LAN server, each microservice, and the MySQL database. To be able to run all of these, you must have the following:
- **Node.js version 18 or higher** (used to run Kaizen LAN server and Kaizen Profile API)
- **Java version 17 or higher** and **Maven version 3 or higher** (used to build and run each microservice)
- **MySQL version 8 or higher** (used to run the MySQL database)

### Starting the app

Each microservice contains it's own documentation for how to run. The important thing to note is that you do not modify any ports that they run on, as Kaizen LAN is configured to call each microservice by their default port.

You must have a configured MySQL server running, and have ran each database setup for this application and each microservice. You also need to make sure that each microservice is properly configured.

Once each microservice is running, you can start the Kaizen LAN server by using `node server.js` in your terminal. The server will start on port `3000`. For connecting to this server, you can use `http://localhost:3000/` from the server computer, or connect to it by finding your LAN address and going to port 3000. This document assumes the reader knows how to obtain the LAN address of their server computer.

## Concept Idea

Based off of [Kaizen](https://github.com/narlock/Kaizen), which is a local Java application that contains a Todo list, habit tracker, journal, and anti habit tracker, I wanted a way to be able to access the features that Kaizen offered through my devices without connecting to any outside server. The solution I came up with was hosting my own server and database and being able to connect to it through local area network. This means that all I have to do is run the server and its required applications, and I can get all of the benefits of Kaizen from any device connected to my network.

## Future Enhancements
- Multiprofile: require users to "login" to their profile. This allows multiple users to have a profile to utilize Kaizen LAN.
    - This includes the functionality of providing a password (or not) to sign in to a profile.
- Anti Habit API integration