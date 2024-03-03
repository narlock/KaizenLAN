# USE <your_database_name>

CREATE TABLE Profile(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(50),
    age INT,
    birth_date DATE,
    image_url VARCHAR(255),
    xp INT
);

CREATE TABLE Health(
    profile_id INT PRIMARY KEY,
    height FLOAT,
    weight FLOAT,
    goal_weight FLOAT,
    goal_water FLOAT,
    FOREIGN KEY (profile_id) REFERENCES Profile(id)
);