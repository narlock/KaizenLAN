import * as WeightLoader from '../lib/graph/weightTrackLoader.js';
import * as Utils from '../lib/utils/utils.js';

async function handleCreateWeightClick(date, amount) {
    await WeightLoader.createWeightEntry(1, date, amount);
}

// Creates div with widget information
async function createWidgetInterface(weightEntries) {
    const weightDiv = document.createElement('div');
    weightDiv.id = 'weightWidget';
    weightDiv.className = 'center';

    // Initialize interface
    var todayAsString = Utils.todayAsString();
    var weightEnteredToday = Utils.isDateInList(todayAsString, weightEntries);
    if(weightEnteredToday) {
        // Grab today's entry amount
        var todayWeightAmount = await WeightLoader.getWeightEntryAmount(1, todayAsString);
        var header = document.createElement('h2');
        header.innerText = `Today\'s weight: ${todayWeightAmount}`;

        // Add interface
        weightDiv.appendChild(header);
    } else {
        // Create a span for interface
        var span = document.createElement('span');

        // Create text box element for creating weight
        var weightEntryBox = document.createElement('input');
        weightEntryBox.type = 'text';
        weightEntryBox.placeholder = 'Enter weight here...';

        // Create a button element for submitting the entry weight
        var weightSubmitButton = document.createElement('button');
        weightSubmitButton.innerText = 'Submit Entry';
        weightSubmitButton.addEventListener('click', async () => {
            await handleCreateWeightClick(todayAsString, weightEntryBox.value);
            window.location.reload(true);
        });

        // Add header
        var header = document.createElement('h2');
        header.innerText = 'Enter your weight';

        // Add interface to widget
        weightDiv.appendChild(header);
        span.appendChild(weightEntryBox);
        span.appendChild(weightSubmitButton);
        weightDiv.appendChild(span);
    }

    // Add week view
    var canvas = document.createElement('canvas');
    canvas.id = 'weightChart';
    canvas.style = 'width:100%;max-height:400px';
    weightDiv.appendChild(canvas);

    return weightDiv;
}

async function displayWeightChart() {
    // Get x and y values and make chart
    const RANGE_WEIGHT_ENTRIES = await WeightLoader.getWeightEntriesRange(1, Utils.getDaysAgoDateString(7), Utils.todayAsString());
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

export { handleCreateWeightClick, createWidgetInterface, displayWeightChart }