HOST_ADDRESS = "192.168.0.129"

var MORNING_ITEMS = null;
var DAILY_ITEMS = null;
var NIGHT_ITEMS = null;

document.addEventListener("DOMContentLoaded", function () {
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

    var button = document.createElement('button');
    button.innerText = '+';
    button.addEventListener('click', () => {
        // TODO input validation against textfield
        // then add the item to the list on backend
        // then call the getList again
        // if success, make sure to set itemTextField's text to nothing
        console.log(`${checklistName} Button clicked!`);
    });

    var itemTextField = document.createElement('input');
    itemTextField.type = 'text';
    itemTextField.id = `${checklistName}TextField`;

    addItemDiv.appendChild(button);
    addItemDiv.appendChild(itemTextField);

    rootElement.appendChild(addItemDiv);
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

            // TODO call method to dynamically show the list
            // make sure it deletes if some div already exists so that
            // we can just call this method to "refresh" the list
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=morning`);
    xmlhttp.send();
}

function getDailyList() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Retrieved the list
            DAILY_ITEMS = JSON.parse(xmlhttp.response);

            // TODO call method to dynamically show the list
            // make sure it deletes if some div already exists so that
            // we can just call this method to "refresh" the list
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=daily`);
    xmlhttp.send();
}

function getNightList() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Retrieved the list
            NIGHT_ITEMS = JSON.parse(xmlhttp.response);

            // TODO call method to dynamically show the list
            // make sure it deletes if some div already exists so that
            // we can just call this method to "refresh" the list
        } else if(this.readyState == 4) {
            console.error("An unexpected error occurred when calling simple-checklist-api. Is it running?")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8085/checklist-item?checklistName=night`);
    xmlhttp.send();
}