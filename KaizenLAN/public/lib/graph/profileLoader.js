import * as KaizenGraphQL from './kaizenGraphInterface.js'

/**
 * 
 * @param {*} profileId 
 * @returns 
 */
async function getKaizenProfileById(profileId) {
    const query = `
        query {
            profile(id: ${profileId}) {
                profile {
                    id
                    username
                    birthDate
                    imageUrl
                    xp
                    numRows
                    pin
                }
                health {
                    weight
                    height
                    goalWeight
                    goalWater
                }
                rowInfoList {
                    rowIndex
                    widgets
                }
            }
        }
    `;
    const getResponse = await KaizenGraphQL.request(query);
    return getResponse.profile;
}

async function updateKaizenProfile(data) {
    const rowInfoListString = data.rowInfoList.map(rowInfo => `{
        profileId: ${rowInfo.profileId}
        rowIndex: ${rowInfo.rowIndex}
        widgets: "${rowInfo.widgets}"
    }`);

    const mutation = `
        mutation {
            updateProfile(input: {
                profile: {
                    id: ${data.profile.id}
                    username: "${data.profile.username}"
                    birthDate: "${data.profile.birthDate}"
                    imageUrl: "${data.profile.imageUrl}"
                    xp: ${data.profile.xp}
                    numRows: ${data.profile.numRows}
                    pin: "${data.profile.pin}"
                }
                health: {
                    height: ${data.health.height}
                    weight: ${data.health.weight}
                    goalWeight: ${data.health.goalWeight}
                    goalWater: ${data.health.goalWater}
                }
                rowInfoList: [
                    ${rowInfoListString}
                ]
            }) {
                profile {
                    id
                    username
                    birthDate
                    imageUrl
                    xp
                    numRows
                    pin
                }
                health {
                    weight
                    height
                    goalWeight
                    goalWater
                }
                rowInfoList {
                    rowIndex
                    widgets
                }
            }
        }
    `;
    const updateResponse = await KaizenGraphQL.request(mutation);
    return updateResponse.profile;
}

async function addXpToProfile(profileId, xpToAdd) {
    const mutation = `
        mutation {
            addXpToProfile(id: ${profileId}, xp: ${xpToAdd}) {
                profile {
                    xp
                }
            }
        }
    `;
    return await KaizenGraphQL.request(mutation);
}

/**
 * 
 * @param {*} div where we want to create the widget
 * @param {*} profile : the profile model from the backend system
 */
function createKaizenProfileWidget(div, profile) {
    console.log("Hello");
}

export { getKaizenProfileById, createKaizenProfileWidget, updateKaizenProfile, addXpToProfile }