# USE <your_database_name>

CREATE TABLE CalendarEvent(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    note VARCHAR(250),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    date DATE NOT NULL,
    meta VARCHAR(250)
);