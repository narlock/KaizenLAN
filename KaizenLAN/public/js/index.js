import * as ProfileUtils from '../lib/graph/profileLoader.js';
import * as ProfileWidget from '.././lib/profileWidget.js';
import * as HabitLoader from '.././lib/graph/habitLoader.js';
import * as HabitWidget from '.././lib/habitWidget.js';
import * as WeightLoader from '.././lib/graph/weightTrackLoader.js';
import * as WeightWidget from '.././lib/weightWidget.js';
import * as WaterWidget from '../lib/waterWidget.js';
import * as ChecklistLoader from '../lib/graph/checklistLoader.js';
import * as ChecklistWidget from '../lib/checklistWidget.js';

var PROFILE;

document.addEventListener('DOMContentLoaded', async function() {
    console.log('DOM fully loaded and parsed, let\'s initialize our widgets');
    
    // Initialize Profile
    try {
        PROFILE = await ProfileUtils.getKaizenProfileById(1);
        console.log(PROFILE);

        // Assuming no error reading the profile, we can populate the widgets
        readProfileDataAndConfigureWidgets();
    } catch (error) {
        console.error('Failed to load profile:', error);
    }
});

function readProfileDataAndConfigureWidgets() {
    const mainElement = document.getElementById('main');
    mainElement.innerHTML = ''; // Clear existing content

    // Iterate over the number of rows specified in the profile
    for (let i = 0; i < PROFILE.profile.numRows; i++) {
        // Create a new container for each row
        const container = document.createElement('div');
        container.className = 'container';

        // Check if there is corresponding rowInfo for the current index
        const rowInfo = PROFILE.rowInfoList.find(row => row.rowIndex === i + 1);
        if (rowInfo) {
            // Split the widgets string into an array of widget names
            const widgets = rowInfo.widgets.split(',');
            // Create a box for each widget
            widgets.forEach(async widgetName => {
                const box = document.createElement('div');
                box.className = 'box';
                // Add an h2 element with the widget's name inside the box
                await populateWidgetData(box, widgetName);
                // Append the box to the container
                container.appendChild(box);
            });
        }

        // Append the container to the main element
        mainElement.appendChild(container);
    }
}

async function populateWidgetData(boxElement, widgetName) {
    // Depending on the widgetName, populate data accordingly
    switch(widgetName) {
        case 'Profile':
            // Add header to box
            const profileHeader = document.createElement('h2');
            profileHeader.textContent = 'Profile';
            boxElement.appendChild(profileHeader);
            
            // Add widget to box and display
            var xpBar = ProfileWidget.displayProfileAndCreateDiv(boxElement, PROFILE);

            // Ensure DOM is updated - before moving the xp bar in widget
            setTimeout(() => {
                ProfileWidget.moveXpBarWithElement(xpBar, PROFILE.profile.xp, boxElement);
            }, 0);
            break;
        case 'Weight':
            const WEIGHT_ENTRIES = await WeightLoader.getWeightEntriesById(1);
            var weightWidget = await WeightWidget.createWidgetInterface(WEIGHT_ENTRIES);
            boxElement.appendChild(weightWidget);

            setTimeout(async () => {
                await WeightWidget.displayWeightChart();
            }, 0);

            break;
        case 'Water':
            // Add header to box
            var waterWidget = await WaterWidget.createWaterInterface();
            console.log(waterWidget);
            boxElement.appendChild(waterWidget);
            break;
        default:
            // Check for habit widget
            if(widgetName.startsWith("Habit")) {
                var habitName = widgetName.substring(5);
                var info = await HabitLoader.getHabitInformation(habitName, 1);
                HabitWidget.displayMonthViewWidget(boxElement, habitName, info.entries, info.streak);
            } else if(widgetName.startsWith("Checklist")) {
                var checklistName = widgetName.substring(9);
                var checklistItems = await ChecklistLoader.getChecklistItems(checklistName);
                await ChecklistWidget.displayChecklistWidget(boxElement, checklistName, checklistItems.checklistItems);
            }    
            else {
                const defaultHeader = document.createElement('h2');
                defaultHeader.textContent = `Widget: ${widgetName}`;
                boxElement.appendChild(defaultHeader);
            }
    }
}

function capitalizeFirstLetter(string) {
    if (string.length === 0) return string; // Handle empty string
    return string.charAt(0).toUpperCase() + string.slice(1);
}