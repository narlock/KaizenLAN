# USE <your_database_name>

CREATE TABLE WeightEntry(
    profile_id INT,
    entry_date DATE,
    entry_amount FLOAT,
    PRIMARY KEY(profile_id, entry_date)
);