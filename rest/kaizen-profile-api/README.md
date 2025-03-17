# Kaizen Profile API

![Java 17](https://img.shields.io/badge/java_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/spring_boot_3-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

**Kaizen Profile API** is a microservice for managing [KaizenLAN](https://github.com/narlock/KaizenLAN) user profiles and associated widget row information. This project's purpose is to provide a simple API to integrate with this data. The core application is developed in [Java](https://www.java.com/), utilizing the [Spring Boot](https://spring.io/projects/spring-boot) framework. The application uses [Spring Data JPA](https://spring.io/projects/spring-data-jpa) to interact with a [MySQL](https://www.mysql.com/) database.

## Requirements to Run

This section assumes the user is familiar with installing Java 17 and running Maven-based Java applications. It also assumes knowledge of installing and configuring a MySQL database server.

### **MySQL Configuration**
Configure the `setup_database.sql` file by including which database are using. 

```sql
USE <replace_with_your_database_name>;
```

Then, run the script in a MySQL terminal.

### **Create or Validate MySQL properties**
Create the file in the directory `${user.home}/Documents/narlock/secrets/mysql.properties` if it does not exist, and include your MySQL credentials so that this API can read them from this properties file.

```
lmysql.username=<replace_with_your_mysql_username>
lmysql.password=<replace_with_your_mysql_password>
```

### **Run the application**
- Using **Java 17**: After building the application, navigate to the directory of the jar in a terminal and use `java -jar kaizen-profile-api.jar` to run the application.
- Using **Maven**: In a terminal, use `mvn spring-boot:run` to run the application.

## Relational Database Schema

![Relational Database Schema](./README%20Assets/relationaldb.png)

## API Specification

- [**Create** a profile](#create-a-profile)
- [**Update** a profile](#update-a-profile)
- [**Delete** a profile](#delete-a-profile)
- [**Retrieve** a profile](#retrieve-a-profile)
- [**Save** row information](#save-row-information)
- [**Update** row information](#update-row-information)
- [**Delete** row information](#delete-row-information)

### Create a profile
Creates a Profile and Health entry for a Kaizen Profile.

```json
POST /profile
Content-Type: application/json

{
    "username": "narlock",
    "birthDate": "2000-02-02",
    "imageUrl": null,
    "xp": 0,
    "numRows": 1,
    "pin": 1234,
    "height": 723,
    "weight": 175,
    "goalWeight": 155,
    "goalWater": 128
}
```

**Response**
```json
HTTP/1.1 201 Created

{
    "profile": {
        "id": 12,
        "username": "narlock",
        "birthDate": "2000-02-02",
        "imageUrl": null,
        "xp": 0,
        "numRows": 1,
        "pin": "1234"
    },
    "health": {
        "profileId": 12,
        "height": 723.0,
        "weight": 175.0,
        "goalWeight": 155.0,
        "goalWater": 128.0
    },
    "rowInfoList": null
}
```

### Update a profile
Updates a Profile and Health entry for a Kaizen Profile.

```json
PUT /profile
Content-Type: application/json

{
    "profileId": 12,
    "username": "narlock",
    "birthDate": "2000-02-02",
    "imageUrl": null,
    "xp": 0,
    "numRows": 1,
    "pin": 1234,
    "height": 723,
    "weight": 175,
    "goalWeight": 155,
    "goalWater": 128
}
```

**Response**
```json
HTTP/1.1 200 OK

{
    "profile": {
        "id": 12,
        "username": "narlock",
        "birthDate": "2000-02-02",
        "imageUrl": null,
        "xp": 0,
        "numRows": 1,
        "pin": "1234"
    },
    "health": {
        "profileId": 12,
        "height": 723.0,
        "weight": 175.0,
        "goalWeight": 155.0,
        "goalWater": 128.0
    },
    "rowInfoList": null
}
```

### Delete a profile
Deletes an existing profile.

```json
DELETE /profile/{id}
```

**Response**
```json
HTTP/1.1 204 No Content
```

### Retrieve a profile
Retrieves a profile by its ID.

```json
GET /profile/{id}
Content-Type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

{
    "profile": {
        "id": 12,
        "username": "narlock",
        "birthDate": "2000-02-02",
        "imageUrl": null,
        "xp": 0,
        "numRows": 1,
        "pin": "1234"
    },
    "health": {
        "profileId": 12,
        "height": 723.0,
        "weight": 175.0,
        "goalWeight": 155.0,
        "goalWater": 128.0
    },
    "rowInfoList": []
}
```

### Save row information
Saves row information for a given profile ID.

```json
POST /profile/{id}/rowInfo
Content-Type: application/json

{
    "rowIndex": 1,
    "widgets": "checklist,weight,profile"
}
```

**Response**
```json
HTTP/1.1 200 OK

[
    {
        "profileId": 12,
        "rowIndex": 1,
        "widgets": "checklist,weight,profile"
    }
]
```

### Update row information
Updates row information for a given profile ID.

```json
PUT /profile/{id}/rowInfo
Content-Type: application/json

{
    "rowIndex": 1,
    "widgets": "checklist,profile,weight"
}
```

**Response**
```json
HTTP/1.1 200 OK

[
    {
        "profileId": 12,
        "rowIndex": 1,
        "widgets": "checklist,profile,weight"
    }
]
```

### Delete row information
Deletes row information for a given profile ID and row index.

```json
DELETE /profile/{id}/rowInfo/{index}
```

**Response**
```json
HTTP/1.1 204 No Content
```

## Testing

> [!NOTE]  
> This section is currently in progress!