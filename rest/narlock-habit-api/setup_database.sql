# USE <your_database_name>

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