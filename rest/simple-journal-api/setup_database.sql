# USE <your_database_name>

CREATE TABLE JournalEntry(
    date DATE PRIMARY KEY NOT NULL,
    profileId INT NOT NULL,
    box1 VARCHAR(255),
    box2 VARCHAR(255),
    box3 VARCHAR(255),
    box4 VARCHAR(255),
    mood INT
);
