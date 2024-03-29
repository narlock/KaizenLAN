HOST_ADDRESS = "192.168.0.129"

var MORNING_ITEMS = null;
var DAILY_ITEMS = null;
var NIGHT_ITEMS = null;

document.addEventListener("DOMContentLoaded", function () {
    getProfileEntry();
    retrieveListInformation();
    addButtonsToDom();
});

function retrieveListInformation() {
    getMorningList();
    getDailyList();
    getNightList();
}

function addButtonsToDom() {
    addButtonToDom(document.getElementById('morningChecklist'), 'morning');
    addButtonToDom(document.getElementById('dayChecklist'), 'daily');
    addButtonToDom(document.getElementById('nightChecklist'), 'night')
}

function addButtonToDom(rootElement, checklistName) {
    var addItemDiv = document.createElement('div');
    addItemDiv.id = `${checklistName}ButtonDiv`;
    addItemDiv.classList.add("center-container");

    var button = document.createElement('button');
    button.classList.add("add-button")
    button.innerText = '+';
    var itemTextField = document.createElement('input');
    itemTextField.type = 'text';
    itemTextField.id = `${checklistName}TextField`;
    addItemDiv.appendChild(button);
    addItemDiv.appendChild(itemTextField);
    rootElement.appendChild(addItemDiv);

    button.addEventListener('click', () => {
        // input validation against textfield
        // then add the item to the list on backend
        // then call the getList again
        // if success, make sure to set itemTextField's text to nothing
        if(itemTextField.value.trim() == '') {
            alert("Kaizen Message\n\nYou must enter a in the text field to add a new item to a checklist.")
        } else {
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("POST", `http://${HOST_ADDRESS}:8085/checklist-item`, true);
            xmlhttp.setRequestHeader("Content-Type", "application/json");

            xmlhttp.onreadystatechange = function() {
                if(this.readyState == 4 && this.status == 201) {
                    
                    console.log(`Successfully created new item for ${checklistName} checklist`)
                    
                    // Refresh the list
                    resetChecklist(checklistName);
                }
            }

            var requestPayload = {
                checklistName: checklistName,
                name: itemTextField.value
            };
            var jsonData = JSON.stringify(requestPayload);
            xmlhttp.send(jsonData);

            itemTextField.value = '';
        }
    });
}

function resetChecklist(name) {
    // Refresh the list
    if(name == 'morning') {
        getMorningList();
    } else if(name == 'daily') {
        getDailyList();
    } else {
        getNightList();
    }
}

function printMorningList() {
    console.log(MORNING_ITEMS);
}

/**
 * =================
 * API METHODS
 * =================
 */

function getMorningList() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Retrieved the list
            MORNING_ITEMS = JSON.parse(xmlhttp.response);
            console.log(`retrieved morning items: ${MORNING_ITEMS}`)
            populateMorningChecklistItems();
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=morning`);
    xmlhttp.send();
}

function populateMorningChecklistItems() {
    var morningChecklistItemsDiv = document.getElementById('morningChecklistItems');

    // Remove each dom child - they will be dynamically readded
    while (morningChecklistItemsDiv.firstChild) {
        morningChecklistItemsDiv.removeChild(morningChecklistItemsDiv.firstChild);
    }

    MORNING_ITEMS.forEach(element => {
        addItemToChecklistDiv(morningChecklistItemsDiv, element, 'morning');
    });
}

function getDailyList() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Retrieved the list
            DAILY_ITEMS = JSON.parse(xmlhttp.response);
            console.log(`retrieved daily items: ${DAILY_ITEMS}`)
            populateDailyChecklistItems();
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=daily`);
    xmlhttp.send();
}

function populateDailyChecklistItems() {
    var dailyChecklistItemsDiv = document.getElementById('dayChecklistItems');

    // Remove each dom child - they will be dynamically readded
    while(dailyChecklistItemsDiv.firstChild) {
        dailyChecklistItemsDiv.removeChild(dailyChecklistItemsDiv.firstChild);
    }

    DAILY_ITEMS.forEach(element => {
        addItemToChecklistDiv(dailyChecklistItemsDiv, element, 'daily');
    });
}

function getNightList() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Retrieved the list
            NIGHT_ITEMS = JSON.parse(xmlhttp.response);
            console.log(`retrieved night items: ${NIGHT_ITEMS}`)
            populateNightChecklistItems();
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=night`);
    xmlhttp.send();
}

function populateNightChecklistItems() {
    var nightChecklistItemsDiv = document.getElementById('nightChecklistItems');

    // Remove each dom child - they will be dynamically readded
    while(nightChecklistItemsDiv.firstChild) {
        nightChecklistItemsDiv.removeChild(nightChecklistItemsDiv.firstChild);
    }

    NIGHT_ITEMS.forEach(element => {
        addItemToChecklistDiv(nightChecklistItemsDiv, element, 'night');
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
                    giveExperienceToProfile(PROFILE_ENTRY, 5);
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
 * =================
 * HELPER METHODS
 * =================
 */

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

function giveExperienceToProfile(profile, amount) {
    profile.xp = profile.xp + amount;
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
}

let PROFILE_ENTRY = null;
function getProfileEntry() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Show profile details
            PROFILE_ENTRY = JSON.parse(xmlhttp.response)
        } else if (this.readyState == 4 && this.status == 404) {
            // Show create profile details
            console.log("Profile not found, showing create profile interface")

            // TODO add create profile logic
        } else if (this.readyState == 4) {
            // Unknown error occurred
            console.error("an unexpected error occurred when calling kaizen-profile-api (is it running?)")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8079/profile/1`, true);
    xmlhttp.send();
}