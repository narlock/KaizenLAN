import * as KaizenGraphQL from './kaizenGraphInterface.js'

async function createChecklist(name) {
    const mutation = `
        mutation {
            createChecklist(profileId: 1, name: "${name}", repeatEvery: "DAY") {
                profileId
                name
            }
        }
    `;
    const createChecklistResponse = await KaizenGraphQL.request(mutation);
    return createChecklistResponse.createChecklist;
}

async function deleteChecklist(name) {
    const mutation = `
        mutation {
            deleteChecklist(profileId: 1, name: ${name})
        }
    `;
    const deleteChecklistResponse = await KaizenGraphQL.request(mutation);
    return deleteChecklistResponse;
}

async function getChecklists() {
    const query = `
        query {
            checklists(profileId: 1) {
                name
            }
        }
    `;
    const getChecklistsResponse = await KaizenGraphQL.request(query);
    return getChecklistsResponse.checklists;
}

async function createChecklistItem(name, checklistName) {
    const mutation = `
        mutation {
            createChecklistItem(checklistItem: {
                profileId: 1,
                checklistName: "${checklistName}",
                name: "${name}"
            }) {
                id
                name
                checklistName
                lastCompletedDate
            }
        }
    `;
    const createChecklistItemResponse = await KaizenGraphQL.request(mutation);
    return createChecklistItemResponse.createChecklistItem;
}

async function completeChecklistItem(id, checklistName) {
    const mutation = `
        mutation {
            completeChecklistItem(
                id: ${id},
                profileId: 1,
                checklistName: "${checklistName}"
            ){
                id
                name
                checklistName
                lastCompletedDate
            }
        }
    `;
    const completeChecklistItemResponse = KaizenGraphQL.request(mutation);
    return completeChecklistItemResponse.completeChecklistItem;
}

async function getChecklistItems(checklistName) {
    const query = `
        query {
            checklistItems(checklistName: "${checklistName}", profileId: 1) {
                id
                checklistName
                profileId
                name
                description
                lastCompletedDate
                excludeDays
                streak
            }
        }
    `;
    const getChecklistItemsResponse = KaizenGraphQL.request(query);
    return getChecklistItemsResponse.checklistItems;
}