import * as ProfileUtils from '../lib/graph/profileLoader.js'
import * as ProfileWidget from '.././lib/profileWidget.js'

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
            widgets.forEach(widgetName => {
                const box = document.createElement('div');
                box.className = 'box';
                // Add an h2 element with the widget's name inside the box
                populateWidgetData(box, widgetName);
                // Append the box to the container
                container.appendChild(box);
            });
        }

        // Append the container to the main element
        mainElement.appendChild(container);
    }
}

function populateWidgetData(boxElement, widgetName) {
    // Depending on the widgetName, populate data accordingly
    switch(widgetName) {
        case 'profile':
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
        case 'habit':
            // Add header to box
            const habitHeader = document.createElement('h2');
            habitHeader.textContent = 'Habit Tracker';
            boxElement.appendChild(habitHeader);
            // Add widget to box and display
            break;
        case 'weight':
            // Add header to box
            const weightHeader = document.createElement('h2');
            weightHeader.textContent = 'Weight Management';
            boxElement.appendChild(weightHeader);
            // Add widget to box and display
            break;
        case 'water':
            // Add header to box
            const waterHeader = document.createElement('h2');
            waterHeader.textContent = 'Water Management';
            boxElement.appendChild(waterHeader);
            // Add widget to box and display
            break;
        default:
            const defaultHeader = document.createElement('h2');
            defaultHeader.textContent = `Widget: ${widgetName}`;
            boxElement.appendChild(defaultHeader);
            // Add widget to box and display
    }
}
