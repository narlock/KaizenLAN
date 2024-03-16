HOST_ADDRESS = "192.168.0.129"

document.addEventListener("DOMContentLoaded", function () {
    setBoxInformation();
    configureChecklistWidget();
    configureProfileWidget();
    configureWeightWidget();
});

function setBoxInformation() {
    // Set checklist information
    var box = document.getElementById('checklist');
    var currentTime = new Date().getHours();

    if (currentTime >= 4 && currentTime < 10) {
        box.innerText = 'Morning Checklist';
    } else if (currentTime >= 10 && currentTime < 17) {
        box.innerText = 'Daily Checklist';
    } else {
        box.innerText = 'Night Checklist';
    }

    box = document.getElementById('schedule');
    box.innerText = 'Schedule'
    box = document.getElementById('profile')
    box.innerHTML = 'Profile'
    box = document.getElementById('weight');
    box.innerHTML = 'Weight'
    box = document.getElementById('water')
    box.innerHTML = 'Water'
}

/**
 * ===============
 * CHECKLIST WIDGET
 * ===============
 */

var CHECKLIST_ITEMS = null;

function configureChecklistWidget() {
    retrieveListInformation();
}

function retrieveListInformation() {
    var currentTime = new Date().getHours();
    if (currentTime >= 4 && currentTime < 10) {
        getList("morning");
    } else if (currentTime >= 10 && currentTime < 17) {
        getList("daily");
    } else {
        getList("night");
    }
}

function getList(checklistName) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            CHECKLIST_ITEMS = JSON.parse(xmlhttp.response);
            populateChecklistItems(checklistName);
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?");
        }
    }

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=${checklistName}`);
    xmlhttp.send();
}

function populateChecklistItems(checklistName) {
    var checklistItemsDiv = document.getElementById('checklistItems');

    // Remove each dom child - they will be dynamically readded
    while (checklistItemsDiv.firstChild) {
        checklistItemsDiv.removeChild(checklistItemsDiv.firstChild);
    }

    CHECKLIST_ITEMS.forEach(element => {
        addItemToChecklistDiv(checklistItemsDiv, element, checklistName);
    });
}

function addItemToChecklistDiv(checklistDiv, element, checklistName) {
    var elementDiv = document.createElement('div')
    elementDiv.classList.add('checklist-element')

    var label = document.createElement('label');
    label.classList.add('checkBoxLabel');
    var checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    label.appendChild(checkbox);
    var checkmark = document.createElement('span')
    checkmark.classList.add('checkmark');
    label.appendChild(checkmark);
    if(element.streak != null && element.streak != 0) {
        label.appendChild(document.createTextNode(`${element.name} [🔥 ${element.streak}]`));
    } else {
        label.appendChild(document.createTextNode(`${element.name}`));
    }
    elementDiv.appendChild(label);
    checklistDiv.appendChild(elementDiv);

    // check completed date
    if(element.lastCompletedDate) {
        sameDay = areDatesEqual(new Date(), new Date(`${element.lastCompletedDate}T00:00:00`));
        if(sameDay) {
            checkbox.checked = true;
            checkbox.disabled = true;
        } else {
            // Not checked, we can complete this today
            label.classList.add('checkBoxIncomplete');
        }
    } else {
        // Not checked, we can complete this today
        label.classList.add('checkBoxIncomplete');
    }

    // action
    checkbox.addEventListener('click', function(event) {
        if(event.shiftKey) {
            checkbox.checked = false;
            var result = window.confirm(`Kaizen Message\n\nWould you like to remove item ${element.name}?`)
            if(result) {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function () {
                    if(this.readyState == 4 && this.status == 204) {
                        console.log("successfully remove item");
                        resetChecklist(checklistName);
                    } else if(this.readyState == 4) {
                        console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
                    }
                }
            }

            xmlhttp.open("DELETE", `http://${HOST_ADDRESS}:8085/checklist-item/${element.id}`, true);
            xmlhttp.send();
        } else {
            // If checkbox is checked, disable it
            if (checkbox.checked) {
                checkbox.disabled = true;
            }
            label.classList.remove('checkBoxIncomplete');

            // Call the streak endpoint to update date and streak
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function () {
                if(this.readyState == 4 && this.status == 200) {
                    console.log("Backend updated");
                } else if(this.readyState == 4) {
                    console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
                }
            }

            xmlhttp.open("PATCH", `http://${HOST_ADDRESS}:8085/checklist-item/${element.id}/streak`, true);
            xmlhttp.send();
        }
    });
}

/**
 * ===============
 * PROFILE WIDGET
 * ===============
 */
function configureProfileWidget() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Show profile details
            PROFILE_ENTRY = JSON.parse(xmlhttp.response)
            console.log(PROFILE_ENTRY)
            displayProfile();
        } else if (this.readyState == 4 && this.status == 404) {
            // Show create profile details
            console.log("Profile not found, showing create profile interface")
        } else if (this.readyState == 4) {
            // Unknown error occurred
            console.error("an unexpected error occurred when calling kaizen-profile-api (is it running?)")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8079/profile/1`, true);
    xmlhttp.send();
}

var mainProfileDiv = null;

function displayProfile() {
    var profileDiv = document.getElementById('profile');

    var mainDiv = document.createElement('div');
    mainDiv.classList.add("center-container");

    var imageElement = document.createElement('img');
    imageElement.src = PROFILE_ENTRY.image_url;

    var contentElement = document.createElement('p');
    contentElement.innerHTML = `
    <span>
        <h3>${PROFILE_ENTRY.username}</h3>
        <br>
        <b class="special">Level</b>: <b>${calculateLevel(PROFILE_ENTRY.xp)}</b> (${PROFILE_ENTRY.xp} XP)<br>
        <b>Height</b>: ${PROFILE_ENTRY.health.height}<br>
        <b>Weight</b>: ${PROFILE_ENTRY.health.weight}<br>
    </span>
    `;

    var xpBarDiv = document.createElement('div');
    xpBarDiv.classList.add('center-container');
    
    var xpBarElement = document.createElement('div');
    xpBarElement.id = 'xpBarProgress';
    var xpBar = document.createElement('div');
    xpBar.id = 'xpBar';
    xpBarElement.appendChild(xpBar);
    xpBarDiv.appendChild(xpBarElement);

    mainDiv.appendChild(imageElement);
    mainDiv.appendChild(contentElement);
    profileDiv.appendChild(mainDiv);
    profileDiv.appendChild(xpBarDiv);

    moveXpBar(PROFILE_ENTRY.xp, profileDiv);
    mainProfileDiv = profileDiv;
}

function moveXpBar(xp, profileDiv) {
    var nextLevelXp = calculateExperienceToNextLevel(xp)
    var percent = xp / nextLevelXp

    var nextLevelElement = document.getElementById('nextLevelElement')
    if(nextLevelElement == null) {
        var nextLevelElement = document.createElement('p')
        nextLevelElement.id = 'nextLevelElement'
        nextLevelElement.innerHTML = `XP to next level: <b>${nextLevelXp}</b> (${Math.round(percent)}%)`
        nextLevelElement.style.textAlign = "center";
        profileDiv.appendChild(nextLevelElement);
    } else {
        nextLevelElement.innerHTML = `XP to next level: <b>${nextLevelXp}</b> (${Math.round(percent)}%)`
    }
    
    var i = 0;
    if (i == 0) {
        i = 1;
        var elem = document.getElementById("xpBar");
        var width = 1;
        var id = setInterval(frame, 10);
        function frame() {
        if (width >= percent) {
            clearInterval(id);
            i = 0;
        } else {
            width++;
            elem.style.width = width + "%";
        }
        }
    }
} 

/**
 * ===============
 * WEIGHT WIDGET
 * ===============
 */

let WEIGHT_TODAY_ENTRY = null
var myWeightChart = null

function configureWeightWidget() {
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

function showTodayWeightInterface(elementId) {
    console.log('showing today interface')
    var element = document.getElementById(elementId)

    // TODO create a special div with an id that can be styled
    var displayWeightDiv = document.createElement('div')
    displayWeightDiv.id = 'displayWeightDiv'
    displayWeightDiv.innerHTML = `<p>Today's weight: <b>${TODAY_ENTRY.weight}</b></p>`

    element.appendChild(displayWeightDiv);
    getGraphEntries(7);
}

function getGraphEntries(daysAgo) {
    if(myWeightChart) {
        myWeightChart.destroy();
        myWeightChart = null;
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

            displayGraph("myWeightChart", xValues, yValues);
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
    myWeightChart = new Chart(element, {
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
    getGraphEntries(7);
}

// Function to be called when the button is clicked
function handleCreateWeightClick() {
    var entry = document.getElementById('createWeightEntry').value
    console.log(`user entered ${entry} into the box`)
    if(isFloatingPointNumber(entry)) {
        // Make call to backend api
        createWeightEntry(entry);
        syncProfileCurrentWeight(entry);
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

function syncProfileCurrentWeight(entry) {
    var PROFILE_ENTRY = null;
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Show profile details
            PROFILE_ENTRY = JSON.parse(xmlhttp.response)
            console.log(PROFILE_ENTRY)
            updateWeightOnProfile(PROFILE_ENTRY, entry);
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

function updateWeightOnProfile(profile, entry) {
    profile.health.weight = entry;
    // gain 10 xp points for entering in daily weight
    profile.xp = profile.xp + 10;
    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", `http://${HOST_ADDRESS}:8079/profile/1`, true)
    xmlhttp.setRequestHeader("Content-Type", "application/json")

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log("successfully updated profile")
        } else if(this.readyState == 4) {
            console.error("an unexpected error occurred when calling kaizen-profile-api (is it running?)")
        }
    }
    var jsonData = JSON.stringify(profile)
    xmlhttp.send(jsonData);

    moveXpBar(profile.xp, mainProfileDiv);
}

function removeCreateWeightInterface() {
    var createWeightDiv = document.getElementById('createWeightDiv')
    if (createWeightDiv) {
        createWeightDiv.remove();
    }
}

/**
 * =================
 * HELPER METHODS
 * =================
 */

function calculateLevel(experience) {
    // Define the base experience required for level 1 and the experience growth factor
    const baseExperience = 100;
    const experienceGrowthFactor = 1.2;

    // Ensure the input is a non-negative integer
    const validatedExperience = Math.max(0, Math.floor(experience));

    // Calculate the level using the exponential formula
    const level = Math.floor(Math.log(validatedExperience / baseExperience) / Math.log(experienceGrowthFactor)) + 1;

    return level;
}

function calculateExperienceToNextLevel(currentExperience) {
    // Define the base experience for level 1 and the experience growth factor
    const baseExperience = 100;
    const experienceGrowthFactor = 1.2;

    // Ensure the input is a non-negative integer
    const validatedExperience = Math.max(0, Math.floor(currentExperience));

    // Calculate the current level using the same formula as before
    const currentLevel = Math.floor(Math.log(validatedExperience / baseExperience) / Math.log(experienceGrowthFactor)) + 1;

    // Calculate the experience required for the next level
    const experienceForNextLevel = Math.ceil(baseExperience * Math.pow(experienceGrowthFactor, currentLevel));

    // Calculate the difference between the experience for the next level and the current experience
    const experienceNeededForNextLevel = Math.max(0, experienceForNextLevel - validatedExperience);

    return experienceNeededForNextLevel;
}

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

function areDatesEqual(date1, date2) {
    console.log(date1);
    console.log(date2);
    if(date1 == null || date2 == null) {
        return false;
    }

    return date1.getFullYear() === date2.getFullYear() &&
           date1.getMonth() === date2.getMonth() &&
           date1.getDate() === date2.getDate();
}