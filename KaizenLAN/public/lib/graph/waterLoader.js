import * as KaizenGraphQL from './kaizenGraphInterface.js'

async function createWaterEntry(profileId, entryDate) {
    const mutation = `
        mutation {
            createWaterEntry(profileId: ${profileId}, entryDate: "${entryDate}", entryAmount: 0) {
                profileId
                entryAmount
                entryDate
            }
        }
    `;
    const createWaterEntryResponse = await KaizenGraphQL.request(mutation);
    return createWaterEntryResponse.createWaterEntry;
}

async function addWaterToEntry(profileId, entryDate, amountToAdd) {
    const mutation = `
        mutation {
            addWaterToEntry(profileId: ${profileId}, entryDate: "${entryDate}", entryAmount: ${amountToAdd}) {
                profileId
                entryAmount
                entryDate
            }
        }
    `;
    const addWaterToEntryResponse = await KaizenGraphQL.request(mutation);
    console.log(addWaterToEntryResponse);
    return addWaterToEntryResponse.addWaterToEntry;
}

async function getWaterEntryAmount(profileId, entryDate) {
    const query = `
        query {
            waterEntry(profileId: ${profileId}, entryDate: "${entryDate}") {
                entryAmount
            }
        }
    `;
    const getWaterEntryAmountResponse = await KaizenGraphQL.request(query);
    return getWaterEntryAmountResponse.waterEntry;
}

async function getWaterEntries(profileId) {
    const query = `
        query {
            waterEntries(profileId: ${profileId}) {
                entryAmount
                entryDate
            }
        }
    `;
    const getWaterEntriesResponse = await KaizenGraphQL.request(query);
    return getWaterEntriesResponse.waterEntries;
}

export { createWaterEntry, addWaterToEntry, getWaterEntryAmount, getWaterEntries }