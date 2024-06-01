import * as ProfileLoader from '../lib/graph/profileLoader.js';
import * as WaterLoader from '../lib/graph/waterLoader.js'
import * as Utils from '../lib/utils/utils.js';

var PROFILE;
var WATER_ENTRIES;
var TODAY_AS_STRING = Utils.todayAsString();
var TODAY_WATER_AMOUNT;

document.addEventListener('DOMContentLoaded', async function() {
    try {
        // retrieve profile
        PROFILE = await ProfileLoader.getKaizenProfileById(1);
        console.log(PROFILE);

        // retrieve water entries for the profile
        WATER_ENTRIES = await WaterLoader.getWaterEntries(1);
        console.log(WATER_ENTRIES);
        await initializeWaterEntryBox();
        await createWaterHeatmap();
    } catch (error) {
        console.error("An unexpected error occurred: ", error);
    }
});

async function initializeWaterEntryBox() {
    var response = await WaterLoader.getWaterEntryAmount(1, TODAY_AS_STRING);
    if(response) {
        TODAY_WATER_AMOUNT = response.entryAmount;
    }
    
    if(TODAY_WATER_AMOUNT) {
        // Entry has been created for today
        console.log("Entry existed for today");
        displayWaterEntryToday(TODAY_WATER_AMOUNT);
    } else {
        // Entry has not been created
        console.log("Entry did NOT exist for today");
        var response = await WaterLoader.createWaterEntry(1, TODAY_AS_STRING);
        console.log(response);
        TODAY_WATER_AMOUNT = response.entryAmount;
        displayWaterEntryToday(TODAY_WATER_AMOUNT);
    }
}

function displayWaterEntryToday(amount) {
    var interfaceDiv = document.getElementById('waterBox');

    // Add header
    var header = document.createElement('h2');
    header.innerText = `You've drank ${amount} water today`;
    interfaceDiv.appendChild(header);

    // Add button interface to add
    var span = document.createElement('span');

    var input = document.createElement('input')
    input.type = 'text';
    var button = document.createElement('button');
    button.innerText = 'Add Water';
    button.addEventListener('click', async () => {
        console.log(input.value);
        await WaterLoader.addWaterToEntry(1, TODAY_AS_STRING, input.value);
        window.location.reload(true);
    });
    span.appendChild(input);
    span.appendChild(button);
    interfaceDiv.appendChild(span);
}

async function createWaterHeatmap() {

}