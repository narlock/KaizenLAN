import * as ChecklistLoader from '../lib/graph/checklistLoader.js';
import * as ChecklistWidget from '../lib/checklistWidget.js';

var CHECKLISTS;

document.addEventListener('DOMContentLoaded', async function() {
    document.getElementById('createChecklist').addEventListener('click', createChecklist);
    document.getElementById('deleteChecklist').addEventListener('click', deleteChecklist);

    try {
        CHECKLISTS = await ChecklistLoader.getChecklists();
        console.log(CHECKLISTS);
        for(let checklist of CHECKLISTS) {
            ChecklistWidget.displayChecklist(checklist);
        }
    } catch (error) {
        console.error('An unexpected error occurred:', error);
    }
});

async function createChecklist() {
    var checklistName = window.prompt('Please enter the name of the checklist to create:');

    if (checklistName) {
        await ChecklistLoader.createChecklist(checklistName);
        window.location.reload(true);
    } else {
        console.log('No checklist name entered. Creation cancelled.');
    }
}

async function deleteChecklist() {
    var checklistName = window.prompt('Please enter the name of the checklist to delete:');

    if (checklistName) {
        await ChecklistLoader.deleteChecklist(checklistName);
        window.location.reload(true);
    } else {
        console.log('No checklist name entered. Deletion cancelled');
    }
}