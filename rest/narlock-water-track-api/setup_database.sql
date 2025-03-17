# USE <your_database_name>

CREATE TABLE WaterEntry(
    profile_id INT,
    entry_date DATE,
    entry_amount INT,
    PRIMARY KEY(profile_id, entry_date)
);