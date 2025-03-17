# USE <your_database_name>

CREATE TABLE Exercise(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    imageUrl VARCHAR(255)
);

CREATE TABLE Workout(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    streak INT
);

CREATE TABLE ExerciseItem(
    exercise_id INT NOT NULL,
    workout_id INT NOT NULL,
    itemIndex INT NOT NULL,
    workTime INT NOT NULL,
    breakTime INT NOT NULL,
    PRIMARY KEY (exercise_id, workout_id, itemIndex),
    FOREIGN KEY (exercise_id) REFERENCES Exercise(id),
    FOREIGN KEY (workout_id) REFERENCES Workout(id)
);