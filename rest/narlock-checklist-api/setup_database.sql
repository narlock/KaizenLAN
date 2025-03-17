# USE <your_database_name>

CREATE TABLE Checklist(
    name VARCHAR(50),
    profile_id INT,
    repeat_every VARCHAR(50) NOT NULL,
    PRIMARY KEY(name, profile_id)
);

CREATE TABLE ChecklistItem(
    id INT AUTO_INCREMENT,
    checklist_name VARCHAR(50),
    profile_id INT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    last_completed_date DATE,
    exclude_days VARCHAR(25),
    streak INT,
    PRIMARY KEY(id, checklist_name, profile_id)
);