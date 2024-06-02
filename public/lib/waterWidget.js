import * as WaterLoader from '../lib/graph/waterLoader.js';
import * as Utils from '../lib/utils/utils.js';
import * as ProfileUtils from '../lib/graph/profileLoader.js';

var TODAY_WATER_AMOUNT;
var TODAY_AS_STRING = Utils.todayAsString();

async function createWaterInterface() {
    var response = await WaterLoader.getWaterEntryAmount(1, TODAY_AS_STRING);
    if(response) {
        TODAY_WATER_AMOUNT = response.entryAmount;
    }
    
    if(TODAY_WATER_AMOUNT) {
        // Entry has been created for today
        console.log("Entry existed for today");
        return displayWaterEntryToday(TODAY_WATER_AMOUNT);
    } else {
        // Entry has not been created
        console.log("Entry did NOT exist for today");
        var response = await WaterLoader.createWaterEntry(1, TODAY_AS_STRING);
        console.log(response);
        TODAY_WATER_AMOUNT = response.entryAmount;
        return displayWaterEntryToday(TODAY_WATER_AMOUNT);
    }
}

function displayWaterEntryToday(amount) {
    var interfaceDiv = document.createElement('div');
    interfaceDiv.id = 'waterBox';

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
    return interfaceDiv;
}

export { createWaterInterface }