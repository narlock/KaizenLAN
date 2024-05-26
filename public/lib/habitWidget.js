import * as HabitLoader from '.././lib/graph/habitLoader.js'
import * as ProfileLoader from '.././lib/graph/profileLoader.js'

/**
 * Returns a div for the specific habit - full year heatmap
 * @param {*} habitName 
 * @param {*} entries 
 * @param {*} streak 
 */
function displayHabitWidgetYear(habitName, entries, streak) {
    const habitDiv = document.createElement('div');
    habitDiv.id = `${habitName}Div`;
    habitDiv.className = 'center';

    // Get streak text
    var streakText = streak != 0 ? ` 🔥${streak}` : '';

    // Create and add habit header with name
    const habitHeader = document.createElement('h2');
    habitHeader.textContent = habitName + streakText;
    habitDiv.appendChild(habitHeader);
    // If an entry for the habit does not exist today, let's create the interface

    var today = new Date();
    var year = today.getFullYear();
    var month = (today.getMonth() + 1).toString().padStart(2, '0');
    var day = today.getDate().toString().padStart(2, '0');
    var todayAsString = `${year}-${month}-${day}`;
    
    if(!entries.includes(todayAsString)) {
        // Create span
        const completeInterface = document.createElement('p');

        // Create button with event action
        const completeHabitButton = document.createElement('button');
        completeHabitButton.innerText = `Complete ${habitName} Today`;
        completeHabitButton.id = `${habitName}CompleteButton`;
        completeHabitButton.addEventListener('click', function () {
            completeHabitForToday(todayAsString, habitName, 1);
        });

        // Add button to span
        completeInterface.appendChild(completeHabitButton);

        // Add to header div
        habitDiv.appendChild(completeInterface);
    } else {
        const completed = document.createElement('p');
        completed.innerText = "Completed Today";
        habitDiv.appendChild(completed);
    }
    
    
    // Create and add the heatmap to the widget
    const habitCalMapDiv = document.createElement('div');
    habitCalMapDiv.id = `${habitName}cal-heatmap`;
    habitDiv.appendChild(habitCalMapDiv);
    
    return habitDiv;
}

async function completeHabitForToday(todayAsString, habitName, profileId) {
    await HabitLoader.saveHabitEntry(habitName, profileId, todayAsString);
    await ProfileLoader.addXpToProfile(profileId, 5);
    window.location.reload(true);
}

function showCalHeatmap(habitName, entries) {
    // Initialize heatmap
    var mainColor = "#44A340";
    var cal = new CalHeatMap();
    var now = new Date();
    var startOfYear = new Date(now.getFullYear(), 0, 1);
    var endOfYear = new Date(now.getFullYear() + 1, 0, 0);

    // Prepare the given entries as a set for quick lookup
    var entrySet = new Set(entries.map(dateStr => {
        var date = new Date(dateStr);
        date.setDate(date.getDate() + 1); // Add one day to each entry since cal map weird
        return date.setHours(0, 0, 0, 0);
    }));

    // Generate data based on the provided entries list
    var calData = {};
    for (var d = new Date(startOfYear); d <= endOfYear; d.setDate(d.getDate() + 1)) {
        var timestamp = Math.floor(d.getTime() / 1000); // convert to seconds
        var dayStart = new Date(d).setHours(0,0,0,0); // Normalize to start of the day to match entries
        calData[timestamp] = entrySet.has(dayStart) ? 1 : 0;
        // console.log("Date: " + d.toDateString() + ", Count: " + calData[timestamp]);
    }

    cal.init({
        itemSelector: `#${habitName}cal-heatmap`,
        domain: "year",
        subDomain: "day",
        data: calData,
        start: startOfYear,  // Start from the beginning of the current year
        cellSize: 10,
        range: 1,  // Set range to cover all months of the year
        domainGutter: 10,
        legend: [0, 1, 2],  // Adjust this based on your data's max count
        tooltip: true,
        legendColors: {
            empty: "#e3e3e3",
            base: mainColor,
            min: mainColor,
            max: mainColor
        },
        legendHorizontalPosition: "right",
        subDomainTextFormat: "",  // No text inside the cells
        domainLabelFormat: "%Y", // %Y-%m
        subDomainTitleFormat: {
            empty: "No data on {date}",
            filled: "{date}"
        }
    });
}

function showCalHeatmapWidget(habitName, entries) {
    // Initialize heatmap
    var mainColor = "#44A340";
    var cal = new CalHeatMap();
    var now = new Date();
    var startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
    var endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);


    // Prepare the given entries as a set for quick lookup
    var entrySet = new Set(entries.map(dateStr => {
        var date = new Date(dateStr);
        date.setDate(date.getDate() + 1); // Add one day to each entry since cal map weird
        return date.setHours(0, 0, 0, 0);
    }));

    // Generate data based on the provided entries list
    var calData = {};
    for (var d = new Date(startOfMonth); d <= endOfMonth; d.setDate(d.getDate() + 1)) {
        var timestamp = Math.floor(d.getTime() / 1000); // convert to seconds
        var dayStart = new Date(d).setHours(0, 0, 0, 0); // Normalize to start of the day to match entries
        calData[timestamp] = entrySet.has(dayStart) ? 1 : 0;
    }

    cal.init({
        itemSelector: `#${habitName}cal-heatmap`,
        domain: "month",
        subDomain: "day",
        data: calData,
        start: startOfMonth,  // Start from the beginning of the current year
        cellSize: 15,
        range: 1,  // Set range to cover all months of the year
        domainGutter: 10,
        legend: [0, 1, 2],  // Adjust this based on your data's max count
        tooltip: true,
        legendColors: {
            empty: "#e3e3e3",
            base: mainColor,
            min: mainColor,
            max: mainColor
        },
        legendHorizontalPosition: "right",
        subDomainTextFormat: "",  // No text inside the cells
        domainLabelFormat: "%B", // %Y-%m
        subDomainTitleFormat: {
            empty: "No data on {date}",
            filled: "{date}"
        }
    });
}


function displayYearView(habitName, entries, streak) {
    // Initialize container
    const habitContainer = document.createElement('div');
    habitContainer.className = 'container';

    // Initialize box for habit
    const habitBox = document.createElement('div');
    habitBox.className = 'box';

    // Add box to container
    habitContainer.appendChild(habitBox);

    // Add container to main div
    document.getElementById('main').appendChild(habitContainer);

    // Add content to habit box
    const habitDiv = displayHabitWidgetYear(habitName, entries, streak);
    habitBox.appendChild(habitDiv);

    // Add cal heatmap after this has been completed
    setTimeout(() => {
        if(window.innerWidth <= 700) {
            showCalHeatmapWidget(habitName, entries);
        } else {
            showCalHeatmap(habitName, entries);
        }
    }, 0);
}

function displayMonthView(habitName, entries, streak) {
    // Initialize container
    const habitContainer = document.createElement('div');
    habitContainer.className = 'container';

    // Initialize box for habit
    const habitBox = document.createElement('div');
    habitBox.className = 'box';

    // Add box to container
    habitContainer.appendChild(habitBox);

    // Add container to main div
    document.getElementById('main').appendChild(habitContainer);

    // Add content to habit box
    const habitDiv = displayHabitWidgetYear(habitName, entries, streak);
    habitBox.appendChild(habitDiv);

    // Add cal heatmap after this has been completed
    setTimeout(() => {
        showCalHeatmapWidget(habitName, entries);
    }, 0);
}

function displayMonthViewWidget(element, habitName, entries, streak) {
    // Add content to habit box
    const habitDiv = displayHabitWidgetYear(habitName, entries, streak);
    element.appendChild(habitDiv);

    // Add cal heatmap after this has been completed
    setTimeout(() => {
        showCalHeatmapWidget(habitName, entries);
    }, 0);
}

export { displayYearView, displayMonthView, displayMonthViewWidget }

function formatDateToString(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}