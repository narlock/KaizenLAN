const express = require('express');
const path = require('path');

const app = express();
const port = 3000; // You can choose any available port

// Serve static files from the 'public' directory
app.use(express.static(path.join(__dirname, 'public')));

// Set up a route to handle requests to the root
app.get('/', (req, res) => {
  // Send the HTML file
  console.log('sending file')
  res.sendFile(path.join(__dirname, 'index.html'));
});

app.get('/weight', (req, res) => {
    // Send the HTML file
    res.sendFile(path.join(__dirname, 'public/weight.html'));
});

// Start the server
app.listen(port,() => {
  console.log(`Server is running at http://localhost:${port}`);
});
 