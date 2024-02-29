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
});

function setWeightBoxInformation() {
    // Set checklist information
    var box = document.getElementById('weight');
    box.innerText = 'Weight'
    box = document.getElementById('stats')
    box.innerText = 'Statistics'
    box = document.getElementById('graph')
    box.innerText = 'Graph'
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
 * CREATION FUNCTIONS
 * =================
 */

function showCreateWeightInterface(elementId) {
    var element = document.getElementById(elementId)

    // TODO create a special div with an id that can be styled
    
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
    element.appendChild(weightEntryBox)
    element.appendChild(weightEntrySubmit)
    // TODO add the special div that is styled
}

// Function to be called when the button is clicked
function handleCreateWeightClick() {
    var entry = weightEntryBox.value
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

function isFloatingPointNumber(input) {
    // Use a regular expression to match a floating-point number
    const floatingPointPattern = /^[-+]?[0-9]*\.?[0-9]+$/;

    // Test the input against the pattern
    return floatingPointPattern.test(input);
}