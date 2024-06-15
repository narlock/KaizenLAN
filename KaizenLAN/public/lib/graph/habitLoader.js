import * as KaizenGraphQL from './kaizenGraphInterface.js'

async function getHabitNames(profileId) {
    const query = `
        query {
            habits(profileId: ${profileId}) {
                name
            }
        }
    `;
    const getHabitNamesResponse = await KaizenGraphQL.request(query);
    return getHabitNamesResponse.habits;
}

async function getHabitInformation(habitName, profileId) {
    const query = `
        query {
            entries: habitEntries(name: "${habitName}", profileId: ${profileId})
            streak: habitStreak(name: "${habitName}", profileId: ${profileId})
        }
    `;
    return await KaizenGraphQL.request(query);
}

async function saveHabit(habitName, profileId) {
    habitName = habitName.replace(/ /g, '_');
    const mutation = `
        mutation {
            createHabit(name: "${habitName}", profileId: ${profileId}) {
                name
            }
        }
    `
    return await KaizenGraphQL.request(mutation);
}

async function saveHabitEntry(habitName, profileId, date) {
    const mutation = `
        mutation {
            createHabitEntry(name: "${habitName}", profileId: ${profileId}, date: "${date}") {
                name
                profileId
                date
            }
        }
    `;
    return await KaizenGraphQL.request(mutation);
}

async function deleteHabit(habitName, profileId) {
    habitName = habitName.replace(/ /g, '_');
    const mutation = `
        mutation {
            deleteHabit(name: "${habitName}", profileId: ${profileId})
        }
    `;
    return await KaizenGraphQL.request(mutation);
}

export { getHabitNames, getHabitInformation, saveHabit, saveHabitEntry, deleteHabit }