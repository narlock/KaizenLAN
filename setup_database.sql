CREATE TABLE Profile(
    id INT,
    username VARCHAR(50),
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
    goal_water FLOAT
);

CREATE TABLE RowInfo (
    profile_id INT,
    row_index INT,
    widgets_list MEDIUMTEXT
);

CREATE TABLE Habit(
    name VARCHAR(250),
    profile_id INT,
    PRIMARY KEY(name, profile_id)
);

CREATE TABLE HabitEntry(
    habit_name VARCHAR(250),
    profile_id INT,
    completed_date DATE,
    PRIMARY KEY(habit_name, profile_id, completed_date)
);

CREATE TABLE WaterEntry(
    profile_id INT,
    entry_date DATE,
    entry_amount INT,
    PRIMARY KEY(profile_id, entry_date)
);

CREATE TABLE WeightEntry(
    profile_id INT,
    entry_date DATE,
    entry_amount FLOAT,
    PRIMARY KEY(profile_id, entry_date)
);

