# Kaizen Profile API

![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

**Kaizen Profile API** is an API that provides CRUD operations against MySQL database for retrieiving profile information related to [Kaizen LAN](https://github.com/narlock/KaizenLAN).

## Requirements to Run

This section assumes that the user of this software has the knowledge of installing Node.js and running Node applications. It also assumes that the user knows how to install and configure a MySQL database server.
- **MySQL**: With MySQL running, configure and run the `setup_database.sql` script to create the **narlock-kaizen** database.
- **Run the application**
  - Using **Docker**: In a terminal, run `docker-compose up -d` to run the API.
  - Using **Node**: navigate to this directory in a terminal and run `node index.js` to run this API.

## Application Design

### High Level Usage

![High Level Usage](./readme%20assets/high_level.png)

### Database - Version 1

![Database V1](./readme%20assets/db_v1.png)

## API Specification

- [Create Profile](#create-profile)
- [Read Profile](#read-profile)
- [Update Profile Details](#update-profile-details)
- [Delete Profile](#delete-profile)

### Create Profile

```json
POST /profile
Content-type: application/json

{
  "username": "myUsername",
  "age": 25,
  "birth_date": "1999-01-01",
  "image_url": "",
  "xp": 0,
  "health": {
    "height": 170,
    "weight": 77,
    "goalWeight": 77,
    "goalWater": 3000
  }
}
```

**Response**
```json
HTTP/1.1 201 Created

{
  "id": 1,
  "username": "myUsername",
  "age": 25,
  "birth_date": "1999-01-01",
  "image_url": "",
  "xp": 0,
  "health": {
    "height": 170,
    "weight": 77,
    "goalWeight": 77,
    "goalWater": 3000
  }
}
```

### Read Profile

```json
GET /profile/{id}
Content-type: application/json
```

**Response**
```json
HTTP/1.1 200 OK

{
  "id": 1,
  "username": "myUsername",
  "age": 25,
  "birth_date": "1999-01-01",
  "image_url": "",
  "xp": 0,
  "health": {
    "height": 170,
    "weight": 77,
    "goalWeight": 77,
    "goalWater": 3000
  }
}
```

### Update Profile Details

```json
PUT /profile/{id}
Content-type: application/json

{
  "username": "myUsername",
  "age": 25,
  "birth_date": "1999-01-01",
  "image_url": "",
  "xp": 0,
  "health": {
    "height": 170,
    "weight": 77,
    "goalWeight": 77,
    "goalWater": 3000
  }
}
```

**Response**
```json
HTTP/1.1 200 OK

{
  "id": 1,
  "username": "myUsername",
  "age": 25,
  "birth_date": "1999-01-01",
  "image_url": "",
  "xp": 0,
  "health": {
    "height": 170,
    "weight": 77,
    "goalWeight": 77,
    "goalWater": 3000
  }
}
```

### Delete Profile

```json
DELETE /profile/{id}
```

**Response**
```json
HTTP/1.1 204 No Content
```