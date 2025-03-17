# Water Tracking API

![Java 17](https://img.shields.io/badge/java_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/spring_boot_3-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

**Water Tracking API** is a microservice for managing water entries designed for [KaizenLAN](https://github.com/narlock/KaizenLAN) profiles. This API allows for creating, retrieving, updating, and deleting water entries. The application is developed in [Java](https://www.java.com/), utilizing the [Spring Boot](https://spring.io/projects/spring-boot) framework, and interacts with a [MySQL](https://www.mysql.com/) database using [Spring Data JPA](https://spring.io/projects/spring-data-jpa).

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
- Using Java 17: After building the application, navigate to the directory of the jar in a terminal and use `java -jar water-track-api.jar` to run the application.
- Using Maven: In a terminal, use mvn `spring-boot:run` to run the application.


## Relational Database Schema
![Relational Database Schema](./readme%20assets/relationaldiagram.png)

## API Specification

- [Create a water entry](#create-a-water-entry)
- [Add amount to water entry]()
- [Delete all entries by profile ID](#delete-all-entries-by-profile-id)
- [Get a water entry by profile ID and date](#get-a-weight-entry-by-profile-id-and-date)
- [Get water entries for a profile](#get-weight-entries-for-a-profile)
- [Get water entries for a profile by a range](#get-water-entries-for-a-profile-by-range)

### Create a water entry
`0` is the default `entryAmount`.

```json
POST /water
Content-Type: application/json

{
  "profileId": 1,
  "entryDate": "2024-05-18",
  "entryAmount": 0
}
```

**Response**
```json
HTTP/1.1 201 Created

{
  "profileId": 1,
  "entryDate": "2024-05-18",
  "entryAmount": 0
}
```

### Add water to a water entry
```json
PATCH /water
Content-Type: application/json

{
  "profileId": 1,
  "entryDate": "2024-05-18",
  "entryAmount": 20
}
```

**Response**
```json
HTTP/1.1 200 OK

{
  "profileId": 1,
  "entryDate": "2024-05-18",
  "entryAmount": 20
}
```

### Delete all entries by profile ID
```json
DELETE /water?profileId=1
```

**Response**
```json
HTTP/1.1 204 No Content
```

### Get a water entry by profile ID and date
```json
GET /water?profileId=1&date=2024-05-18
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

[
  {
    "profileId": 1,
    "entryDate": "2024-05-18",
    "entryAmount": 80
  }
]
```

### Get weight entries for a profile
```json
GET /water?profileId=1
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

[
  {
    "profileId": 1,
    "entryDate": "2024-05-18",
    "entryAmount": 80
  },
  {
    "profileId": 1,
    "entryDate": "2024-05-19",
    "entryAmount": 85
  }
]
```

### Get water entries for a profile by range
```json
GET /water/range?profileId=1&startDate=2024-05-12&endDate=2024-05-18
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

[
  {
    "profileId": 1,
    "entryDate": "2024-05-18",
    "entryAmount": 80
  },
  {
    "profileId": 1,
    "entryDate": "2024-05-19",
    "entryAmount": 85
  }
]
```

## Testing
> [!NOTE]
> This section is currently in progress!