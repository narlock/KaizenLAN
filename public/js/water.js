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
    console.log('Inside of createWaterHeatmap - water-heatmap is the div id');
    var cal = new CalHeatMap();
    var now = new Date();
    var startOfYear = new Date(now.getFullYear(), 0, 1);
    var endOfYear = new Date(now.getFullYear() + 1, 0, 0);

    // Prepare the given entries as a map for quick lookup
    var entryMap = new Map(WATER_ENTRIES.map(entry => {
        var date = new Date(entry.entryDate);
        date.setDate(date.getDate() + 1); // Add one day to each entry since cal map is weird
        return [date.setHours(0, 0, 0, 0), entry.entryAmount];
    }));
    console.log(entryMap);

    // Generate data based on the provided entries list
    var calData = {};
    for (var d = new Date(startOfYear); d <= endOfYear; d.setDate(d.getDate() + 1)) {
        var timestamp = Math.floor(d.getTime() / 1000); // convert to seconds
        var dayStart = new Date(d).setHours(0, 0, 0, 0); // Normalize to start of the day to match entries
        var entryAmount = entryMap.has(dayStart) ? entryMap.get(dayStart) : 0;
        calData[timestamp] = entryAmount;
    }

    cal.init({
        itemSelector: `#water-heatmap`,
        domain: "year",
        subDomain: "day",
        data: calData,
        start: startOfYear,  // Start from the beginning of the current year
        cellSize: 10,
        range: 1,  // Set range to cover all months of the year
        domainGutter: 10,
        legend: [0, 10, 30, 50, 70, 90, 110, 130, 150 ],  // Adjust this based on your data's max count
        tooltip: true,
        legendColors: {
            empty: "#e3e3e3",
            min: "#004461",
            max: "#00b3ff"
        },
        legendHorizontalPosition: "left",
        subDomainTextFormat: "",  // No text inside the cells
        domainLabelFormat: "%Y", // %Y-%m
        subDomainTitleFormat: {
            empty: "No data on {date}",
            filled: "{date} -- {count} water"
        }
    });
}