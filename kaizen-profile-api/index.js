// Import necessary modules
const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');
const app = express();
const port = 8079;

// MySQL Connection Pool
const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'password',
    database: 'narlock',
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

// Express Configuration
app.use(express.json());
app.use(cors()); // This is here so we can allow any requests to go through

// POST endpoint to create profile
app.post('/profile', (req, res) => {
    const { username, age, birth_date, image_url, xp, health } = req.body;

    // Step 1: Insert into Profile table
    const profileQuery = 'INSERT INTO Profile (age, username, birth_date, image_url, xp) VALUES (?, ?, ?, ?, ?)';
    pool.query(profileQuery, [age, username, birth_date, image_url, xp], (profileError, profileResults) => {
        if (profileError) {
            console.error('Error creating profile:', profileError);
            res.status(500).json({
                message: 'Internal Server Error',
                detail: 'Error creating profile in the database'
            });
        } else {
            const profileId = profileResults.insertId;

            // Step 2: Insert into Health table
            const { height, weight, goalWeight, goalWater } = health;
            const healthQuery = 'INSERT INTO Health (profile_id, height, weight, goal_weight, goal_water) VALUES (?, ?, ?, ?, ?)';
            pool.query(healthQuery, [profileId, height, weight, goalWeight, goalWater], (healthError, healthResults) => {
                if (healthError) {
                    console.error('Error creating health entry:', healthError);
                    // If there's an error, you might want to roll back the profile creation, handle accordingly
                    res.status(500).json({
                        message: 'Internal Server Error',
                        detail: 'Error creating health entry in the database'
                    });
                } else {
                    res.status(201).json({
                        message: 'Profile created successfully',
                        profileId: profileId
                    });
                }
            });
        }
    });
});

// GET endpoint to retrieve profile data
app.get('/profile/:id', (req, res) => {
    const id = req.params.id;
    const query = `SELECT p.id, p.username, p.age, DATE_FORMAT(p.birth_date, "%Y-%m-%d") AS birth_date, p.image_url, p.xp, h.height, h.weight, h.goal_weight AS goalWeight, h.goal_water AS goalWater
                   FROM Profile p
                   LEFT JOIN Health h ON p.id = h.profile_id
                   WHERE p.id = ? LIMIT 1`;

    pool.query(query, [id], (error, results) => {
        if (error) {
            console.error('Error querying MySQL:', error);
            res.status(500).json({
                message: 'Internal Server Error',
                detail: 'Error querying the database'
            });
        } else {
            if (results.length > 0) {
                const profileData = {
                    id: results[0].id,
                    username: results[0].username,
                    age: results[0].age,
                    birth_date: results[0].birth_date,
                    image_url: results[0].image_url,
                    xp: results[0].xp,
                    health: {
                        height: results[0].height,
                        weight: results[0].weight,
                        goalWeight: results[0].goalWeight,
                        goalWater: results[0].goalWater
                    }
                };
                res.json(profileData);
            } else {
                res.status(404).json({
                    message: 'Profile not found',
                    detail: `No profile information found for id ${id}`
                });
            }
        }
    });
});

// PUT endpoint to update profile
app.put('/profile/:id', (req, res) => {
    const id = req.params.id;
    const { username, age, birth_date, image_url, xp, health } = req.body;

    const updateProfileQuery = `UPDATE Profile SET username = ?, age = ?, birth_date = ?, image_url = ?, xp = ? WHERE id = ?`;

    pool.query(updateProfileQuery, [username, age, birth_date, image_url, xp, id], (updateProfileError, updateProfileResults) => {
        if (updateProfileError) {
            console.error('Error updating profile:', updateProfileError);
            res.status(500).json({
                message: 'Internal Server Error',
                detail: 'Error updating profile in the database'
            });
            return;
        }
        // Assuming health data is part of the request
        const { height, weight, goalWeight, goalWater } = health;
        const updateHealthQuery = `UPDATE Health SET height = ?, weight = ?, goal_weight = ?, goal_water = ? WHERE profile_id = ?`;

        pool.query(updateHealthQuery, [height, weight, goalWeight, goalWater, id], (updateHealthError, updateHealthResults) => {
            if (updateHealthError) {
                console.error('Error updating health entry:', updateHealthError);
                res.status(500).json({
                    message: 'Internal Server Error',
                    detail: 'Error updating health entry in the database'
                });
            } else {
                res.json({
                    id,
                    username,
                    age,
                    birth_date,
                    image_url,
                    xp,
                    health: {
                        height,
                        weight,
                        goalWeight,
                        goalWater
                    }
                });
            }
        });
    });
});

// DELETE endpoint to delete profile
app.delete('/profile/:id', (req, res) => {
    const id = req.params.id;

    // It's often safer to start with the dependent tables
    const deleteHealthQuery = `DELETE FROM Health WHERE profile_id = ?`;

    pool.query(deleteHealthQuery, [id], (deleteHealthError, deleteHealthResults) => {
        if (deleteHealthError) {
            console.error('Error deleting health entry:', deleteHealthError);
            res.status(500).json({
                message: 'Internal Server Error',
                detail: 'Error deleting health entry in the database'
            });
            return;
        }
        const deleteProfileQuery = `DELETE FROM Profile WHERE id = ?`;
        pool.query(deleteProfileQuery, [id], (deleteProfileError, deleteProfileResults) => {
            if (deleteProfileError) {
                console.error('Error deleting profile:', deleteProfileError);
                res.status(500).json({
                    message: 'Internal Server Error',
                    detail: 'Error deleting profile in the database'
                });
            } else {
                res.status(204).send(); // HTTP 204 No Content
            }
        });
    });
});


// Start the server
app.listen(port, () => {
  console.log(`Kaizen Profile API is listening on port ${port}`);
});
