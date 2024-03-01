const HOST_ADDRESS = "192.168.0.129"
let TODAY_ENTRY = null

/**
 * =================
 * ON LOAD FUNCTIONS
 * =================
 */

document.addEventListener("DOMContentLoaded", function () {
    setWeightBoxInformation()
    todayEntry()
    createGraph();
});



function setWeightBoxInformation() {
    // Set checklist information
    var box = document.getElementById('weight');
    box.innerText = 'Weight'
    box = document.getElementById('stats')
    box.innerText = 'Statistics'

    // set Graph settings
    var graphBox = document.getElementById('graph')
    graphBox.innerText = 'Graph'

    var weekViewButton = document.createElement('button')
    weekViewButton.innerText = 'Week View'
    weekViewButton.id = 'weightGraphWeekView'
    weekViewButton.addEventListener('click', weightGraphWeekView);

    var monthViewButton = document.createElement('button')
    monthViewButton.innerText = 'Month View'
    monthViewButton.id = 'weightGraphMonthView'
    monthViewButton.addEventListener('click', weightGraphWeekView);

    var yearViewButton = document.createElement('button')
    yearViewButton.innerText = 'Year View'
    yearViewButton.id = 'weightGraphYearView'
    yearViewButton.addEventListener('click', weightGraphWeekView);

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
            console.error(`an unexpected error occurred when calling simple-time-block-api ${response.error}`)
        }
    };

    xmlhttp.open("GET", `http://${HOST_ADDRESS}:8081/weight?date=${today}`, true)
    xmlhttp.send();
}

/**
 * =================
 * INITIALIZE GRAPH
 * =================
 */

let WEEKLY_RESPONSE = null;

function createGraph() {
    // Default to show Week View
    var today = getCurrentDateString()
    var sevenDaysAgo = getDaysAgoDateString(7)
    console.log(`attempting to retrieve /weight/range?startDate=${sevenDaysAgo}&endDate=${today}`)
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            // There is an entry for today
            WEEKLY_RESPONSE = JSON.parse(xmlhttp.response)
            console.log(`weekly response successfully returned`)
            displayWeekGraph("myChart")
        } else if (this.readyState == 4) {
            // Some error occurred`
            var response = JSON.parse(xmlhttp.response)
            console.error(`an unexpected error occurred when calling simple-time-block-api ${response.error}`)
        }
    };

    xmlhttp.open("GET", `http://${HOST_ADDRESS}:8081/weight/range?startDate=${sevenDaysAgo}&endDate=${today}`, true)
    xmlhttp.send();
}

function displayWeekGraph(element) {
    const xValues = [];
    const yValues = [];

    console.log(WEEKLY_RESPONSE)
    WEEKLY_RESPONSE.entries.forEach(element => {
        xValues.push(element.date)
        yValues.push(element.weight)
    });

    const myChart = new Chart(element, {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                label: "Weight Trend (Week View)",
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

    // TODO create a special div with an id that can be styled
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
    // TODO add the special div that is styled
}

// Function to be called when the button is clicked
function handleCreateWeightClick() {
    var entry = document.getElementById('createWeightEntry').value
    console.log(`user entered ${entry} into the box`)
    if(isFloatingPointNumber(entry)) {
        // Make call to backend api
        createWeightEntry(entry);
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

function removeCreateWeightInterface() {
    var createWeightDiv = document.getElementById('createWeightDiv')
    if (createWeightDiv) {
        document.removeChild(createWeightDiv);
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
}

/**
 * =================
 * VIEW GRAPH FUNCTIONS
 * =================
 */

function weightGraphWeekView() {

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