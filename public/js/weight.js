import * as ProfileLoader from '../lib/graph/profileLoader.js';
import * as WeightLoader from '../lib/graph/weightTrackLoader.js';
import * as WeightWidget from '../lib/weightWidget.js';
import * as Utils from '../lib/utils/utils.js';

var PROFILE;
var WEIGHT_ENTRIES;
var TODAY_AS_STRING = Utils.todayAsString();
var TODAY_WEIGHT_AMOUNT;
var ENTERED_TODAY;
var myChart = null;

document.addEventListener('DOMContentLoaded', async function() {
    try {
        // Retrieve profile
        PROFILE = await ProfileLoader.getKaizenProfileById(1);
        console.log(PROFILE);

        // Retrieve weight entries for the profile
        WEIGHT_ENTRIES = await WeightLoader.getWeightEntriesById(1);
        console.log(WEIGHT_ENTRIES);
        await initializeWeightEntryBox();
        await initializeWeightStatsBox();

        // Initialize weight chart to week view
        createWeightBoxInterface(6);
        await createWeightGraphBox(6);
    } catch (error) {
        console.error("An unexpected error occurred: ", error);
    }
});

async function initializeWeightEntryBox() {
    // Check if today is in string
    ENTERED_TODAY = Utils.isDateInList(TODAY_AS_STRING, WEIGHT_ENTRIES);

    if(ENTERED_TODAY) {
        console.log('Entry was entered for today, showing on screen');
        TODAY_WEIGHT_AMOUNT = await WeightLoader.getWeightEntryAmount(1, TODAY_AS_STRING);
        displayWeightEntryToday(TODAY_WEIGHT_AMOUNT);
    } else {
        console.log('Entry was NOT entered for today, creating interface');
        createWeightEntryInterface(); 
    }
}

/**
 * Instruction to create weight entry interface
 */
function createWeightEntryInterface() {
    // Retrieve weight entry box
    var interfaceDiv = document.getElementById('weightEntryBox');

    // Create a span for interface
    var span = document.createElement('span');

    // Create text box element for creating weight
    var weightEntryBox = document.createElement('input');
    weightEntryBox.type = 'text';
    weightEntryBox.id = 'createWeightEntryText';
    weightEntryBox.placeholder = 'Enter weight here...';

    // Create a button element for submitting the entry weight
    var weightSubmitButton = document.createElement('button');
    weightSubmitButton.innerText = 'Submit Entry';
    weightSubmitButton.id = 'createWeightEntryButton';
    weightSubmitButton.addEventListener('click', async () => {
        await WeightWidget.handleCreateWeightClick(TODAY_AS_STRING, weightEntryBox.value);
        window.location.reload(true);
    });

    // Add element to dom
    var header = document.createElement('h2');
    header.innerText = 'Enter your weight';

    // Construct interface
    interfaceDiv.appendChild(header);
    span.appendChild(weightEntryBox);
    span.appendChild(weightSubmitButton);
    interfaceDiv.appendChild(span);
}

function displayWeightEntryToday(todaysWeightString) {
    // Retrieve weight entry box
    var interfaceDiv = document.getElementById('weightEntryBox');

    // Create header element
    var header = document.createElement('h2');
    header.innerText = `Today\'s weight: ${todaysWeightString}`;

    // Add header to interface
    interfaceDiv.appendChild(header);
}

async function initializeWeightStatsBox() {

    if(ENTERED_TODAY) {
        var interfaceDiv = document.getElementById('weightStatsBox');

        // Body mass index
        console.log(TODAY_WEIGHT_AMOUNT);
        var bmi = Utils.calculateBMI(TODAY_WEIGHT_AMOUNT, PROFILE.health.height);
        var bmiString = Utils.getBmiString(bmi);
        var node = document.createElement('h2')
        node.innerHTML = `BMI: <b>${bmi}</b> [<b class="bmi">${bmiString}</b>]`;

        // Add to interface
        interfaceDiv.appendChild(node);

        // Append color styling
        var bmiElement = document.querySelector('.bmi');
        bmiElement.classList.remove('underweight', 'normal', 'overweight', 'obese');
        bmiElement.classList.add(bmiString.toLowerCase());
    } else {
        // Calculate BMI given current weight
        var header = document.createElement('h2');
        header.innerText = 'No statistics available for today';
        interfaceDiv.appendChild(header);
    }
}

function createWeightBoxInterface(daysAgo) {
    var parent = document.getElementById('weightGraphBox');
    var interfaceDiv = document.createElement('div');
    interfaceDiv.id = 'weightBoxInterfaceDiv';

    // Create header
    var header = document.createElement('h2');
    header.id = 'weightBoxHeader';
    header.innerText = `Weight Chart (${Utils.getDaysAgoDateString(daysAgo)} - ${TODAY_AS_STRING})`;
    interfaceDiv.appendChild(header);

    // Span for buttons
    var span = document.createElement('span');
    
    // Week View button
    var weekViewButton = document.createElement('button');
    weekViewButton.innerText = 'Week View';
    weekViewButton.addEventListener('click', async () => {
        await createWeightGraphBox(6);
        document.getElementById('weightBoxHeader').innerText = `Weight Chart (${Utils.getDaysAgoDateString(6)} - ${TODAY_AS_STRING})`;
    });
    span.appendChild(weekViewButton);

    // Month View button
    var monthViewButton = document.createElement('button');
    monthViewButton.innerText = 'Month View';
    monthViewButton.addEventListener('click', async () => {
        await createWeightGraphBox(29);
        document.getElementById('weightBoxHeader').innerText = `Weight Chart (${Utils.getDaysAgoDateString(29)} - ${TODAY_AS_STRING})`;
    });
    span.appendChild(monthViewButton);

    // Year View button
    var yearViewButton = document.createElement('button');
    yearViewButton.innerText = 'Year View';
    yearViewButton.addEventListener('click', async () => {
        await createWeightGraphBox(364);
        document.getElementById('weightBoxHeader').innerText = `Weight Chart (${Utils.getDaysAgoDateString(364)} - ${TODAY_AS_STRING})`;
    });
    span.appendChild(yearViewButton);
    
    interfaceDiv.appendChild(span);
    // Insert interface div
    parent.insertBefore(interfaceDiv, parent.firstChild);
}

async function createWeightGraphBox(daysAgo) {
    // Destroy the chart if it exists for some reason
    if(myChart) {
        myChart.destroy();
        myChart = null;
    }

    // Populate x and y values for chart
    const RANGE_WEIGHT_ENTRIES = await WeightLoader.getWeightEntriesRange(1, Utils.getDaysAgoDateString(daysAgo), TODAY_AS_STRING);
    const xValues = [];
    const yValues = [];
    RANGE_WEIGHT_ENTRIES.forEach(element => {
        console.log(element);
        xValues.push(element.entryDate);
        yValues.push(element.entryAmount);
    });

    console.log(xValues);
    console.log(yValues);

    // Create and display chart
    myChart = new Chart("weightChart", {
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

function initializeWeightHabitHeatmap() {

}