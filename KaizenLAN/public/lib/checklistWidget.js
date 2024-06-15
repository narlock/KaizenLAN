import * as ChecklistLoader from '../lib/graph/checklistLoader.js';
import * as Utils from '../lib/utils/utils.js';

function displayChecklist(checklist) {
    // Create container
    const checklistContainer = document.createElement('div');
    checklistContainer.className = 'container';

    // Create checklist box
    const checklistBox = document.createElement('div');
    checklistBox.className = 'box';

    // Add box to container
    checklistContainer.appendChild(checklistBox);

    // Add container to main div
    document.getElementById('main').appendChild(checklistContainer);

    const checklistContentDiv = createChecklistContentDiv(checklist);
    checklistBox.appendChild(checklistContentDiv);
}

function createChecklistContentDiv(checklist) {
    const checklistContentDiv = document.createElement('div');
    checklistContentDiv.id = `${checklist.name}Div`;
    checklistContentDiv.className = 'center';

    // Create header
    const header = document.createElement('h2');
    header.textContent = checklist.name.replace(/_/g, ' ');
    checklistContentDiv.appendChild(header);

    // Populate current items
    for(let item of checklist.items) {
        var elementDiv = document.createElement('div')
        elementDiv.classList.add('checklist-element')
        var label = document.createElement('label');
        label.classList.add('checkBoxLabel');
        var checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        label.appendChild(checkbox);
        var checkmark = document.createElement('span')
        checkmark.classList.add('checkmark');
        label.appendChild(checkmark);
        label.appendChild(document.createTextNode(`${item.name}`));
        elementDiv.appendChild(label);
        checklistContentDiv.appendChild(elementDiv);

        // check completed date
        if(item.lastCompletedDate) {
            var sameDay = Utils.areDatesEqual(new Date(), new Date(`${item.lastCompletedDate}T00:00:00`));
            if(sameDay) {
                checkbox.checked = true;
                checkbox.disabled = true;
            } else {
                // Not checked, we can complete this today
                label.classList.add('checkBoxIncomplete');
            }
        } else {
            // Not checked, we can complete this today
            label.classList.add('checkBoxIncomplete');
        }

        // action
        checkbox.addEventListener('click', async function(event) {
            if(event.shiftKey) {
                // Ask to delete the checklist item
                checkbox.checked = false;
                var result = window.confirm(`Kaizen Message\n\nWould you like to remove item ${item.name}?`)
                if(result) {
                    await ChecklistLoader.deleteChecklistItem(item.id);
                    window.location.reload(true);
                }
            } else {
                // If checkbox is checked, disable it
                if (checkbox.checked) {
                    checkbox.disabled = true;
                }
                label.classList.remove('checkBoxIncomplete');
                await ChecklistLoader.completeChecklistItem(item.id, checklist.name);
                window.location.reload(true);
            }
        });

        label.addEventListener('click', async function(event) {
            if(event.shiftKey) {
                var result = window.confirm(`Kaizen Message\n\nWould you like to remove item ${item.name}?`)
                if(result) {
                    await ChecklistLoader.deleteChecklistItem(item.id);
                    window.location.reload(true);
                }
            }
        });
    }

    // Add item
    var addItemDiv = document.createElement('div');
    addItemDiv.id = `${checklist.name}ButtonDiv`;
    addItemDiv.classList.add("center-container");
    var button = document.createElement('button');
    button.classList.add("add-button")
    button.innerText = '+';
    var itemTextField = document.createElement('input');
    itemTextField.type = 'text';
    itemTextField.id = `${checklist.name}TextField`;
    addItemDiv.appendChild(button);
    addItemDiv.appendChild(itemTextField);
    checklistContentDiv.appendChild(addItemDiv);
    button.addEventListener('click', async () => {
        var textFieldValue = itemTextField.value.trim();
        if(textFieldValue) {
            await ChecklistLoader.createChecklistItem(textFieldValue, checklist.name);
            window.location.reload(true);
        }
    });
    return checklistContentDiv;
}

export { displayChecklist }