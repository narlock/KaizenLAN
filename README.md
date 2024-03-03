# KaizenLAN

![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

**KaizenLAN** - Continuously improve your life with helpful widgets - accessible around your home! This application offers a interface that contains many different self improvement tools. These tools are offered by simple APIs that connect to a backend database. These features include:

1. Kaizen Profile provided by **Kaizen Profile API** (requires Node JS)
2. Habit Tracking provided by **[Simple Habit Track API]()** (requires Java 17)
3. Schedule Calendar provided by **[Simple Time Block API](https://github.com/narlock/simple-time-block-api)** (requires Java 17)
4. Weight Tracking provided by **[Simple Weight Track API](https://github.com/narlock/simple-weight-track-api)** (requires Java 17)
5. Water / Hydration Tracking provided by **[Simple Water Track API](https://github.com/narlock/simple-water-track-api)** (requires Java 17)
6. Journal provided by **[Simple Journal API](https://github.com/narlock/simple-journal-api)** (requires Java 17)

This program's intention is to run the server, the MySQL database, and each of these microservices on the same computer system within the desired local area network. From there, any device connected to the network can access Kaizen.

![Concept View](./readme%20assets/Concept.png)

## Concept Idea

Based off of [Kaizen](https://github.com/narlock/Kaizen), which is a local Java application that contains a Todo list, habit tracker, journal, and anti habit tracker, I wanted a way to be able to access the features that Kaizen offered through my devices without connecting to any outside server. The solution I came up with was hosting my own server and database and being able to connect to it through local area network. This means that all I have to do is run the server and its required applications, and I can get all of the benefits of Kaizen from any device connected to my network.