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
        var entries = await WaterLoader.getWaterEntries(1);
        setTimeout(() => {
            createMonthCalHeatmap(entries);
        }, 0);

        return displayWaterEntryToday(TODAY_WATER_AMOUNT);
    } else {
        // Entry has not been created
        console.log("Entry did NOT exist for today");
        var response = await WaterLoader.createWaterEntry(1, TODAY_AS_STRING);
        console.log(response);
        TODAY_WATER_AMOUNT = response.entryAmount;

        var entries = await WaterLoader.getWaterEntries(1);
        setTimeout(() => {
            createMonthCalHeatmap(entries);
        }, 0);
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
    span.classList.add('entryWaterSpan');

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

function createMonthCalHeatmap(WATER_ENTRIES) {
    var parent = document.getElementById('waterBox');
    var heatmapDiv = document.createElement('div');
    heatmapDiv.id = 'water-heatmap-widget';
    parent.appendChild(heatmapDiv);

    var cal = new CalHeatMap();
    var now = new Date();
    var startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
    var endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);

    // Prepare the given entries as a map for quick lookup
    var entryMap = new Map(WATER_ENTRIES.map(entry => {
        var date = new Date(entry.entryDate);
        date.setDate(date.getDate() + 1); // Add one day to each entry since cal map is weird
        return [date.setHours(0, 0, 0, 0), entry.entryAmount];
    }));
    console.log(entryMap);

    // Generate data based on the provided entries list
    var calData = {};
    for (var d = new Date(startOfMonth); d <= endOfMonth; d.setDate(d.getDate() + 1)) {
        var timestamp = Math.floor(d.getTime() / 1000); // convert to seconds
        var dayStart = new Date(d).setHours(0, 0, 0, 0); // Normalize to start of the day to match entries
        var entryAmount = entryMap.has(dayStart) ? entryMap.get(dayStart) : 0;
        calData[timestamp] = entryAmount;
    }

    cal.init({
        itemSelector: `#water-heatmap-widget`,
        domain: "month",
        subDomain: "day",
        data: calData,
        start: startOfMonth,  // Start from the beginning of the current year
        cellSize: 15,
        range: 1,  // Set range to cover all months of the year
        domainGutter: 10,
        legend: [0, 10, 30, 50, 70, 90, 110, 130, 150 ],  // Adjust this based on your data's max count
        tooltip: true,
        legendColors: {
            empty: "#e3e3e3",
            min: "#004461",
            max: "#00b3ff"
        },
        legendHorizontalPosition: "right",
        subDomainTextFormat: "",  // No text inside the cells
        domainLabelFormat: "%B", // %Y-%m
        subDomainTitleFormat: {
            empty: "No data on {date}",
            filled: "{date} -- {count} water"
        }
    });
}

export { createWaterInterface }