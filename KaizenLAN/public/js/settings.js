import * as ProfileUtils from '.././lib/graph/profileLoader.js';
import * as HabitLoader from '.././lib/graph/habitLoader.js';

export async function saveChanges() {
    var profile = {
        id: 1,
        username: document.getElementById('username').value,
        birthDate: document.getElementById('birthdate').value,
        imageUrl: document.getElementById('userImage').src,
        xp: PROFILE.profile.xp,
        numRows: document.getElementById('numberOfRows').value,
        pin: PROFILE.profile.pin
    };

    var health = {
        profileId: 1,
        height: parseFloat(document.getElementById('height').value),
        weight: PROFILE.health.weight,
        goalWeight: parseFloat(document.getElementById('goalWeight').value),
        goalWater: parseFloat(document.getElementById('goalWater').value)
    };

    var rowInfoList = [];
    var rows = document.querySelectorAll('#widgetContainer .widget-row');
    rows.forEach((row, index) => {
        var widgets = Array.from(row.querySelectorAll('select')).map(select => select.value).join(',');
        rowInfoList.push({ profileId: 1, rowIndex: index + 1, widgets: widgets });
    });

    var data = {
        profile: profile,
        health: health,
        rowInfoList: rowInfoList
    };

    console.log('Changes saved!', JSON.stringify(data, null, 2));
    PROFILE = await ProfileUtils.updateKaizenProfile(data);
    window.location.reload(true);
}

export async function populateForm(data) {
    document.getElementById('username').value = data.profile.username;
    document.getElementById('birthdate').value = data.profile.birthDate;
    document.getElementById('userImage').src = data.profile.imageUrl == null ? "res/placeholder.png" : data.profile.imageUrl; // Sets the placeholder if no image
    document.getElementById('numberOfRows').value = data.profile.numRows;
    await updateWidgets();

    document.getElementById('height').value = data.health.height;
    document.getElementById('goalWeight').value = data.health.goalWeight;
    document.getElementById('goalWater').value = data.health.goalWater;

    // If this throws an error, make sure that the size of rowInfoList equals profile.numRows
    data.rowInfoList.forEach((rowInfo, index) => {
        console.log(rowInfo);
        console.log(index);
        let rowDiv = document.querySelector(`#widgetContainer .widget-row:nth-child(${index + 1})`);
        let widgets = rowInfo.widgets.split(',');
        widgets.forEach((widget, widgetIndex) => {
            if (widgetIndex > 0) {
                addWidget(rowDiv.querySelector('button[onclick="addWidget(this)"]'));
            }
            try {
                rowDiv.querySelectorAll('select')[widgetIndex].value = widget;
            } catch (error) {
                console.warn("Expected warning occurred when trying to dynamically populate number of rows. " +
                    "There are entries that exist in the database for rows that are not being shown. " +
                    "If you don't want to see this, match the numRows field with rowInfoList size.");
            }
        });
    });
}

window.updateWidgets = async function () {
    const habitNameResponse = await HabitLoader.getHabitNames(1);
    var HABIT_NAMES = habitNameResponse.map(item => "Habit" + item.name);

    var options = ["Profile", "Weight", "Water"];
    HABIT_NAMES.forEach(e => {
        options.push(e);
    });

    var container = document.getElementById('widgetContainer');
    container.innerHTML = '';
    var numberOfRows = document.getElementById('numberOfRows').value;
    for (let i = 0; i < numberOfRows; i++) {
        let rowDiv = document.createElement('div');
        rowDiv.className = 'widget-row';
        
        let select = document.createElement('select');
        select.name = `widget${i}`;
        options.forEach(optionText => {
            let option = document.createElement('option');
            option.value = optionText.toLowerCase();
            option.innerText = optionText;
            select.appendChild(option);
        });

        rowDiv.innerHTML = `
            <button type="button" onclick="addWidget(this)">+</button>
            <button type="button" onclick="removeWidget(this)">-</button>
        `;
        rowDiv.appendChild(select);
        container.appendChild(rowDiv);
    }
}

window.addWidget = function(button) {
    let parent = button.parentNode;
    let select = parent.querySelector('select').cloneNode(true);
    parent.appendChild(select);
}

window.removeWidget = function(button) {
    let parent = button.parentNode;
    if (parent.children.length > 3) {
        parent.removeChild(parent.lastChild);
    }
}

/**
 * Document on load, will load existing profile information
 */

var PROFILE;

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById("submitButton").onclick = saveChanges;

    await populateFormFromProfile();
});

async function populateFormFromProfile() {
    PROFILE = await ProfileUtils.getKaizenProfileById(1);
    console.log(JSON.stringify(PROFILE, null, 2));
    populateForm(PROFILE);
}
