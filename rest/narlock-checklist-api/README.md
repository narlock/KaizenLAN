# Checklist API

![Java 17](https://img.shields.io/badge/java_17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/spring_boot_3-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

**Checklist API** is a microservice for managing checklists and checklist items designed for [KaizenLAN](https://github.com/narlock/KaizenLAN) profiles. This API allows for creating, retrieving, updating, and deleting checklists and checklist items. The application is developed in [Java](https://www.java.com/), utilizing the [Spring Boot](https://spring.io/projects/spring-boot) framework, and interacts with a [MySQL](https://www.mysql.com/) database using [Spring Data JPA](https://spring.io/projects/spring-data-jpa).

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
- Using Java 17: After building the application, navigate to the directory of the jar in a terminal and use `java -jar checklist-api.jar` to run the application.
- Using Maven: In a terminal, use mvn `spring-boot:run` to run the application.


## Relational Database Schema
![Relational Database Schema](./readme%20assets/relational.png)

## API Specification

- [Create a checklist](#create-a-checklist)
- [Retrieve a checklist](#retrieve-a-checklist)
- [Update checklist](#update-checklist)
- [Delete a checklist](#delete-a-checklist)
- [Create a item for a checklist](#create-a-checklist-item)
- [Retrieve a item in a checklist](#retrieve-a-checklist-item)
- [Update a checklist item](#update-a-checklist-item)
- [Update checklist item's streak](#update-checklist-item-streak)
- [Delete a checklist item](#delete-a-checklist-item)
- [Retrieve checklist items by checklist name](#retrieve-checklist-items-by-checklist-name)

### Create a checklist
Creates a new checklist.

```json
POST /checklist
Content-Type: application/json

{
    "name": "Daily Tasks",
    "profileId": 1,
    "repeatEvery": "DAY"
}
```

**Response**

```json
HTTP/1.1 200 OK

{
    "name": "Daily Tasks",
    "profileId": 1,
    "repeatEvery": "DAY"
}
```

### Retrieve a checklist
Retrieves a checklist by name and profile ID.

```json
GET /checklist?name=Daily Tasks&profileId=1
Content-Type: application/json
```

**Response**

```json
HTTP/1.1 200 OK

{
    "name": "Daily Tasks",
    "profileId": 1,
    "repeatEvery": "DAY"
}
```

### Update checklist
Updates the repeat interval of a checklist.

```json
PATCH /checklist?name=Daily Tasks&profileId=1&repeatEvery=WEEK
Content-Type: application/json
```

**Response**

```json
HTTP/1.1 200 OK

{
    "name": "Daily Tasks",
    "profileId": 1,
    "repeatEvery": "WEEK"
}
```

### Delete a checklist
Deletes an existing checklist.

```json
DELETE /checklist?name=Daily Tasks&profileId=1
Content-Type: application/json
```

**Response**

```json
HTTP/1.1 204 No Content
```

### Create a checklist item
Creates a new checklist item.

```json
POST /checklist-item
Content-Type: application/json

{
    "checklistName": "Daily Tasks",
    "profileId": 1,
    "name": "Exercise",
    "description": "Morning exercise routine",
    "lastCompletedDate": "2024-05-11",
    "excludeDays": "Sunday",
    "streak": 0
}
```

**Response**

```json
HTTP/1.1 201 Created

{
    "id": 1,
    "checklistName": "Daily Tasks",
    "profileId": 1,
    "name": "Exercise",
    "description": "Morning exercise routine",
    "lastCompletedDate": "2024-05-11",
    "excludeDays": "Sunday",
    "streak": 0
}
```

### Retrieve a checklist item
Retrieves a checklist item by ID, checklist name, and profile ID.

```json
GET /checklist-item/{id}?checklistName=Daily Tasks&profileId=1
Content-Type: application/json
```

**Response**

```json
HTTP/1.1 200 OK

{
    "id": 1,
    "checklistName": "Daily Tasks",
    "profileId": 1,
    "name": "Exercise",
    "description": "Morning exercise routine",
    "lastCompletedDate": "2024-05-11",
    "excludeDays": "Sunday",
    "streak": 0
}
```

### Update a checklist item
Updates an existing checklist item.

```json
PUT /checklist-item/{id}
Content-Type: application/json

{
    "checklistName": "Morning Habits",
    "profileId": 1,
    "name": "Drink 16 oz Water",
    "description": "I need to drink 16 oz of water"
}
```

**Response**

```json
HTTP/1.1 200 OK

{
    "id": 1,
    "checklistName": "Morning Habits",
    "profileId": 1,
    "name": "Drink 16 oz Water",
    "description": "I need to drink 16 oz of water",
    "lastCompletedDate": null,
    "excludeDays": null,
    "streak": null
}
```

### Update checklist item streak
Updates the streak of a checklist item.

```json
PATCH /checklist-item/{id}/streak?checklistName=Morning Habits&profileId=1
Content-Type: application/json
```

**Response**

```json
HTTP/1.1 200 OK

{
    "id": 2,
    "checklistName": "Morning Habits",
    "profileId": 1,
    "name": "Walk around the park",
    "description": null,
    "lastCompletedDate": "2024-05-11",
    "excludeDays": null,
    "streak": 2
}
```

### Delete a checklist item
Deletes a checklist item by ID, checklist name, and profile ID.

```json
DELETE /checklist-item/{id}?checklistName=Daily Tasks&profileId=1
Content-Type: application/json
```

**Response**

```json
HTTP/1.1 204 No Content
```

### Retrieve checklist items by checklist name
Retrieves all checklist items for a given checklist name and profile ID.

```json
GET /checklist-item?checklistName=Daily Tasks&profileId=1
Content-Type: application/json
```

**Response**

```
HTTP/1.1 200 OK

[
    {
        "id": 1,
        "checklistName": "Daily Tasks",
        "profileId": 1,
        "name": "Exercise",
        "description": "Morning exercise routine",
        "lastCompletedDate": "2024-05-11",
        "excludeDays": "Sunday",
        "streak": 2
    }
]
```

## Testing
> [!NOTE]
> This section is currently in progress!