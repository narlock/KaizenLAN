# Habit API

![Java 17](https://img.shields.io/badge/java_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/spring_boot_3-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

**Habit API** is a microservice for managing habits and habit entries designed for [KaizenLAN](https://github.com/narlock/KaizenLAN) profiles. This API allows for creating, retrieving, updating, and deleting habits and habit entries. The application is developed in [Java](https://www.java.com/), utilizing the [Spring Boot](https://spring.io/projects/spring-boot) framework, and interacts with a [MySQL](https://www.mysql.com/) database using [Spring Data JPA](https://spring.io/projects/spring-data-jpa).

## Requirements to Run

This section assumes the user is familiar with installing Java 17 and running Maven-based Java applications. It also assumes knowledge of installing and configuring a MySQL database server.

### **MySQL Configuration**
Configure the `setup_database.sql` file by including your database name.

```sql
USE <replace_with_your_database_name>;
```
Then, run the script in a MySQL terminal.

### Create or Validate MySQL properties
Create the file in the directory `${user.home}/Documents/narlock/secrets/mysql.properties` if it does not exist, and include your MySQL credentials so that this API can read them from this properties file.

```
lmysql.username=<replace_with_your_mysql_username>
lmysql.password=<replace_with_your_mysql_password>
```

### Run the application
- Using Java 17: After building the application, navigate to the directory of the jar in a terminal and use `java -jar habit-api.jar` to run the application.
- Using Maven: In a terminal, use mvn `spring-boot:run` to run the application.


## Relational Database Schema
![Relational Database Schema](./readme%20assets/relational.png)

## API Specification

- [Create a habit](#create-a-habit)
- [Create an entry for a habit](#create-an-entry-for-a-habit)
- [Retrieve habits associated to a profile](#retrieve-habits-associated-to-a-profile)
- [Retrieve a habit's streak](#retrieve-a-habits-streak)
- [Retrieve date entries for a habit](#retrieve-date-entries-for-a-habit)
- [Retrieve an entry for a habit by date](#retrieve-an-entry-for-a-habit-by-date)
- [Update a habit's name](#update-a-habits-name)
- [Delete a habit by name and profile ID](#delete-a-habit-by-name-and-profile-id)

### Create a habit

```json
POST /habit
Content-Type: application/json

{
  "name": "My Habit Name",
  "profileId": 1
}
```

**Response**

```json
HTTP/1.1 201 Created

{
  "name": "My Habit Name",
  "profileId": 1
}
```

### Create an entry for a habit

```json
POST /habit-entry
Content-Type: application/json

{
  "name": "My Habit Name",
  "profileId": 1,
  "date": "2024-05-17"
}
```

**Response**
```json
HTTP/1.1 201 Created

{
  "name": "My Habit Name",
  "profileId": 1,
  "date": "2024-05-17"
}
```

### Retrieve habits associated to a profile

```json
GET /habit?profileId=1
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

[
  {
    "name": "My Habit Name",
    "profileId": 1
  },
  {
    "name": "My Second Habit Name",
    "profileId": 1
  }
]
```

### Retrieve a habit's streak
A habit "streak" represents the number of consecutive days a user with the given profileId has successfully completed a habit.

```json
GET /habit-streak
Content-Type: application/json

{
"name": "My Habit Name",
"profileId": 1
}
```

**Response**
```json
HTTP/1.1 200 OK

20
```
This example request shows the integer `20` being returned. This is the current streak for this example habit.

### Retrieve date entries for a habit

```json
GET /habit-entry?name=My Habit Name&profileId=1
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

[
  "2024-05-17",
  "2024-05-16",
  "2024-05-15",
  "2024-05-14",
  "2024-05-13",
  "2024-05-10",
  "2024-04-20"
]
```

### Retrieve an entry for a habit by date

```json
GET /habit-entry?name=My Habit Name&profileId=1&date=2024-05-17
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

[
  "2024-05-17"
]
```

Notably, if there is no entry matching the date parameter, this will return a 404 Not Found

### Update a habit's name
```json
PUT /habit
Content-Type: application/json

{
  "oldName": "My Cool Habit",
  "newName": "My Awesome Habit",
  "profileId": 1
}
```

**Response**
```json
HTTP/1.1 200 OK

{
  "name": "My Awesome Habit",
  "profileId": 1
}
```

### Delete a habit by name and profile ID
This operation will delete not only the row in the Habit table matching the given criteria, but will also delete each habit entry matching the given criteria.

```json
DELETE /habit?name=My Habit Name&profileId=1
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 204 No Content
```

## Testing
> [!NOTE]
> This section is currently in progress!