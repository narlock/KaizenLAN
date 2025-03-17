# USE <your_database_name>;

CREATE TABLE Profile(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(50),
    age INT,
    birth_date DATE,
    image_url VARCHAR(255),
    xp INT,
    num_rows INT,
    pin CHAR(4) CHECK (pin REGEXP '^[0-9]{4}$')
);

CREATE TABLE Health(
    profile_id INT PRIMARY KEY,
    height FLOAT,
    weight FLOAT,
    goal_weight FLOAT,
    goal_water FLOAT,
    FOREIGN KEY (profile_id) REFERENCES Profile(id)
);

CREATE TABLE RowInfo (
    profile_id INT,
    row_index INT,
    widgets_list MEDIUMTEXT,
    PRIMARY KEY(profile_id, row_index)
);

