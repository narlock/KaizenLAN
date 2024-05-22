const HOST_ADDRESS = "192.168.0.129"
let TODAY_ENTRY = null
var myChart = null
var PROFILE = null;

/**
 * =================
 * ON LOAD FUNCTIONS
 * =================
 */

document.addEventListener("DOMContentLoaded", function () {
    setWeightBoxInformation()
    getProfileEntry();
    todayEntry();
});

function getProfileEntry() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Show profile details
            PROFILE = JSON.parse(xmlhttp.response)
            console.log(PROFILE)
            showStats();
        } else if (this.readyState == 4 && this.status == 404) {
            // Show create profile details
            console.log("Profile not found, showing create profile interface")
        } else if (this.readyState == 4) {
            // Unknown error occurred
            console.error("an unexpected error occurred when calling kaizen-profile-api (is it running?)")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8079/profile/1`);
    xmlhttp.send();
}

function setWeightBoxInformation() {
    // Set checklist information
    var box = document.getElementById('weight');
    box.innerText = 'Weight'
    box = document.getElementById('stats')
    box.innerText = 'Statistics'

    // set Graph settings
    var graphBox = document.getElementById('graph')

    var graphBoxTitle = document.createElement('h3')
    graphBoxTitle.id = 'graphBoxTitle'
    graphBoxTitle.innerText = 'Graph'

    var weekViewButton = document.createElement('button')
    weekViewButton.innerText = 'Week View'
    weekViewButton.id = 'weightGraphWeekView'
    weekViewButton.addEventListener('click', createWeekGraph);

    var monthViewButton = document.createElement('button')
    monthViewButton.innerText = 'Month View'
    monthViewButton.id = 'weightGraphMonthView'
    monthViewButton.addEventListener('click', createMonthGraph);

    var yearViewButton = document.createElement('button')
    yearViewButton.innerText = 'Year View'
    yearViewButton.id = 'weightGraphYearView'
    yearViewButton.addEventListener('click', createYearGraph);

    graphBox.appendChild(graphBoxTitle);
    graphBox.appendChild(weekViewButton);
    graphBox.appendChild(monthViewButton);
    graphBox.appendChild(yearViewButton);
}

function todayEntry() {
    var today = getCurrentDateString()
    console.log(`attempting to retrieve /weight?date=${today}`)
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            // There is an entry for today
            TODAY_ENTRY = JSON.parse(xmlhttp.response)
            console.log(`weight entry for ${today} found`)

            showTodayWeightInterface("weight")
        } else if (this.readyState == 4 && this.status == 404) {
            // No entry found for date
            var response = JSON.parse(xmlhttp.response)
            console.log(response.message)

            // Give option to create entry
            showCreateWeightInterface("weight")
        } else if (this.readyState == 4) {
            // Some error occurred`
            var response = JSON.parse(xmlhttp.response)
            console.error(`an unexpected error occurred when calling simple-time-block-api (is it running?)`)
        }
    };

    xmlhttp.open("GET", `http://${HOST_ADDRESS}:8081/weight?date=${today}`, true)
    xmlhttp.send();
}

/**
 * =================
 * SHOW STATS
 * =================
 */

function showStats() {
    var bmi = getBmi();
    var bmiString = getBmiString(bmi);

    console.log('showing stats interface')
    var element = document.getElementById('stats')

    // TODO create a special div with an id that can be styled
    var displayStatsDiv = document.createElement('div')
    displayStatsDiv.id = 'displayStatsDiv'
    displayStatsDiv.innerHTML = `<p>BMI: <b>${bmi}</b> [<b class="bmi">${bmiString}</b>]</p>`

    element.appendChild(displayStatsDiv)

    var bmiElement = document.querySelector('.bmi');
    bmiElement.classList.remove('underweight', 'normal', 'overweight', 'obese');
    bmiElement.classList.add(bmiString.toLowerCase());
}

function getBmi() {
    var heightInInches = PROFILE.health.height;
    var weightInPounds = PROFILE.health.weight;
    
    // Convert height from inches to meters (1 inch = 0.0254 meters)
    var heightInMeters = heightInInches * 0.0254;

    // Convert weight from pounds to kilograms (1 pound = 0.453592 kilograms)
    var weightInKilograms = weightInPounds * 0.453592;

    // Calculate BMI
    var bmi = weightInKilograms / (heightInMeters * heightInMeters);

    // Round the BMI to two decimal places
    bmi = Math.round(bmi * 100) / 100;

    return bmi;
}

function getBmiString(bmi) {
    if (bmi < 18.5) {
        return "Underweight";
    } else if (bmi >= 18.5 && bmi < 25) {
        return "Normal";
    } else if (bmi >= 25 && bmi < 30) {
        return "Overweight";
    } else {
        return "Obese";
    }
}

function updateStats() {
    var displayStatsDiv = document.getElementById('displayStatsDiv')
    var bmi = getBmi();
    var bmiString = getBmiString(bmi);
    displayStatsDiv.innerHTML = `<p>BMI: <b>${bmi}</b> [<b class="bmi">${bmiString}</b>]</p>`

    var bmiElement = document.querySelector('.bmi');
    bmiElement.classList.remove('underweight', 'normal', 'overweight', 'obese');
    bmiElement.classList.add(bmiString.toLowerCase());
}

/**
 * =================
 * GRAPH
 * =================
 */

let GRAPH_RESPONSE = null;

function createWeekGraph() {
    getGraphEntries(7);
    document.getElementById('graphBoxTitle').innerText = 'Graph (Week View)'
}

function createMonthGraph() {
    getGraphEntries(30);
    document.getElementById('graphBoxTitle').innerText = 'Graph (Month View)'
}

function createYearGraph() {
    getGraphEntries(365);
    document.getElementById('graphBoxTitle').innerText = 'Graph (Year View)'
}

function getGraphEntries(daysAgo) {
    if(myChart) {
        myChart.destroy();
        myChart = null;
    }

    // Default to show Week View
    var today = getCurrentDateString()
    var daysAgo = getDaysAgoDateString(daysAgo)
    console.log(`attempting to retrieve /weight/range?startDate=${daysAgo}&endDate=${today}`)
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            GRAPH_RESPONSE = JSON.parse(xmlhttp.response)

            const xValues = [];
            const yValues = [];

            GRAPH_RESPONSE.entries.forEach(element => {
                xValues.push(element.date)
                yValues.push(element.weight)
            });

            displayGraph("myChart", xValues, yValues);
        } else if (this.readyState == 4) {
            // Some error occurred`
            var response = JSON.parse(xmlhttp.response)
            console.error(`an unexpected error occurred when calling simple-time-block-api ${response.error}`)
        }
    };

    xmlhttp.open("GET", `http://${HOST_ADDRESS}:8081/weight/range?startDate=${daysAgo}&endDate=${today}`, true)
    xmlhttp.send();
}

function displayGraph(element, xValues, yValues) {
    myChart = new Chart(element, {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                label: "Weight Trend",
                fill: false,
                lineTension: 0.2,
                backgroundColor: "rgba(0,235,123,1.0)",
                borderColor: "rgba(0,166,87,0.7)",
                pointRadius: 8,
                pointHoverRadius: 12,
                data: yValues
            }]
        },
        options: {
            plugins: {
                legend: {
                  labels: {
                    color: "white",
                  }
                }
              },
            scales: {
                y: {
                    title: {
                        display: true,
                        color: "white",
                        text: "Weight"
                    },
                    ticks: {
                        color: "white"
                    }
                },
                x: {
                    title: {
                        display: true,
                        color: "white",
                        text: "Date"
                    },
                    ticks: {
                        color: "white"
                    }
                }
            }
        }
    });
}

/**
 * =================
 * CREATE WEIGHT FUNCTIONS
 * =================
 */

function showCreateWeightInterface(elementId) {
    var element = document.getElementById(elementId)

    var createWeightDiv = document.createElement('div')
    createWeightDiv.id = 'createWeightDiv'

    // Create text box for entering weight
    var weightEntryBox = document.createElement('input')
    weightEntryBox.type = 'text'
    weightEntryBox.id = 'createWeightEntry'
    weightEntryBox.placeholder = 'Enter weight here...';

    // Create a button for submitting the weight entry to database
    var weightEntrySubmit = document.createElement('button')
    weightEntrySubmit.innerText = 'Submit Entry'
    weightEntrySubmit.id = 'createWeightButton'
    weightEntrySubmit.addEventListener('click', handleCreateWeightClick);

    // add them to the dom
    createWeightDiv.appendChild(weightEntryBox)
    createWeightDiv.appendChild(weightEntrySubmit)
    element.appendChild(createWeightDiv)

    // Recreate the graph view - based off of what is already there...
    graph_text = document.getElementById('graphBoxTitle').innerText
    if(graph_text.includes('Month')) {
        createMonthGraph();
    } else if(graph_text.includes('Year')) {
        createYearGraph();
    } else {
        createWeekGraph();
    }
    
}

// Function to be called when the button is clicked
function handleCreateWeightClick() {
    var entry = document.getElementById('createWeightEntry').value
    console.log(`user entered ${entry} into the box`)
    if(isFloatingPointNumber(entry)) {
        // Make call to backend api
        createWeightEntry(entry);
        updateWeightOnProfile(entry);
    } else {
        alert(`Unexpected entry value: ${entry}, please enter a valid weight entry.`)
    }
}

function createWeightEntry(entry) {
    console.log(`creating weight entry for today with ${entry}`)

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", `http://${HOST_ADDRESS}:8081/weight`, true)
    xmlhttp.setRequestHeader("Content-Type", "application/json")

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 201) {
            console.log("successfully created, change to view")
            TODAY_ENTRY = JSON.parse(xmlhttp.response)
            removeCreateWeightInterface();
            showTodayWeightInterface("weight");
        } else if(this.readyState == 4) {
            alert("An unexpected error occurred when creating an entry")
        }
    }

    var data = {
        weight: entry,
        date: getCurrentDateString()
    };
    var jsonData = JSON.stringify(data)

    xmlhttp.send(jsonData);
}

function updateWeightOnProfile(entry) {
    PROFILE.health.weight = entry;
    // gain 10 xp points for entering in daily weight
    PROFILE.xp = PROFILE.xp + 10;
    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", `http://${HOST_ADDRESS}:8079/profile/1`, true)
    xmlhttp.setRequestHeader("Content-Type", "application/json")

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log("successfully updated profile")
            updateStats();
        } else if(this.readyState == 4) {
            console.error("an unexpected error occurred when calling kaizen-profile-api (is it running?)")
        }
    }
    var jsonData = JSON.stringify(PROFILE)
    xmlhttp.send(jsonData);
}

function removeCreateWeightInterface() {
    var createWeightDiv = document.getElementById('createWeightDiv')
    if (createWeightDiv) {
        createWeightDiv.remove();
    }
}

/**
 * =================
 * SHOW WEIGHT FUNCTIONS
 * =================
 */

function showTodayWeightInterface(elementId) {
    console.log('showing today interface')
    var element = document.getElementById(elementId)

    // TODO create a special div with an id that can be styled
    var displayWeightDiv = document.createElement('div')
    displayWeightDiv.id = 'displayWeightDiv'
    displayWeightDiv.innerHTML = `<p>Today's weight: <b>${TODAY_ENTRY.weight}</b></p>`

    element.appendChild(displayWeightDiv)
    createWeekGraph()
}

/**
 * =================
 * HELPER FUNCTIONS
 * =================
 */

function getCurrentDateString() {
    const today = new Date();
    
    // Get year, month, and day
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Month is zero-based, so add 1
    const day = String(today.getDate()).padStart(2, '0');

    // Concatenate them in the "YYYY-mm-dd" format
    const dateString = `${year}-${month}-${day}`;

    return dateString;
}

function getDaysAgoDateString(days) {
    const today = new Date();
    const sevenDaysAgo = new Date(today);
    
    // Subtract seven days
    sevenDaysAgo.setDate(today.getDate() - days);

    // Get year, month, and day
    const year = sevenDaysAgo.getFullYear();
    const month = String(sevenDaysAgo.getMonth() + 1).padStart(2, '0'); // Month is zero-based, so add 1
    const day = String(sevenDaysAgo.getDate()).padStart(2, '0');

    // Concatenate them in the "YYYY-mm-dd" format
    const dateString = `${year}-${month}-${day}`;

    return dateString;
}

function isFloatingPointNumber(input) {
    // Use a regular expression to match a floating-point number
    const floatingPointPattern = /^[-+]?[0-9]*\.?[0-9]+$/;

    // Test the input against the pattern
    return floatingPointPattern.test(input);
}