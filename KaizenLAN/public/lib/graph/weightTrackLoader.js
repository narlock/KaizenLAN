import * as KaizenGraphQL from './kaizenGraphInterface.js';

/**
 * @param {int} profileId 
 * @returns a list of weight entries that match profileId
 */
async function getWeightEntriesById(profileId) {
    const query = `
    query {
        weightEntries(profileId: ${profileId}) {
            entryAmount
            entryDate
        }
    }
    `;
    const getEntriesResponse = await KaizenGraphQL.request(query);
    return getEntriesResponse.weightEntries;
}

/**
 * Asynchronous function to create a weight entry for a specific profile.
 * 
 * @async
 * @function createWeightEntry
 * @param {number} profileId - The unique identifier of the profile.
 * @param {string} entryDate - The date of the weight entry in the format "YYYY-MM-DD".
 * @param {number} entryAmount - The weight amount to be recorded.
 * @returns {Object} The response from the GraphQL API containing the created weight entry details.
 */
async function createWeightEntry(profileId, entryDate, entryAmount) {
    const mutation = `
        mutation {
            createWeightEntry(profileId: 1, entryDate: "${entryDate}", entryAmount: ${entryAmount}) {
                profileId
                entryAmount
                entryDate
            }
        }
    `;
    const createWeightEntryResponse = await KaizenGraphQL.request(mutation);
    return createWeightEntryResponse.createWeightEntry;
}

/**
 * @param {number} profileId 
 * @param {string} date 
 */
async function getWeightEntryAmount(profileId, date) {
    const query = `
        query {
            weightEntry(profileId: ${profileId}, entryDate: "${date}") {
                entryAmount
            }
        }
    `;
    const getWeightEntryResponse = await KaizenGraphQL.request(query);
    return getWeightEntryResponse.weightEntry.entryAmount;
}

async function getWeightEntriesRange(profileId, startDate, endDate) {
    console.log(`Attempting to retrieve weight entries from ${startDate} to ${endDate}`);
    const query = `
        query {
            weightEntries: weightEntriesByRange(profileId: ${profileId},
            startDate: "${startDate}", endDate: "${endDate}") {
                entryDate
                entryAmount
            }
        }
    `;
    const getWeightEntriesRangeResponse = await KaizenGraphQL.request(query);
    return getWeightEntriesRangeResponse.weightEntries;
}

export { getWeightEntriesById, createWeightEntry, getWeightEntryAmount, getWeightEntriesRange }