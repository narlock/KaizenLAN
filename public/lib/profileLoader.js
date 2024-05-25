const GRAPH_ENDPOINT = "http://localhost:8080/graphql";

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
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ query })
    };
    try {
        const response = await fetch(GRAPH_ENDPOINT, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        var responseBody = await response.json();
        return responseBody.data.profile
    } catch (error) {
        console.error('Error fetching data:', error);
        alert("An unexpected error occurred retrieivng profile information - are the APIs running?")
    }
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
    console.log(mutation);
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ query: mutation })
    };
    try {
        const response = await fetch(GRAPH_ENDPOINT, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        var responseBody = await response.json();
        return responseBody.data.profile
    } catch (error) {
        console.error('Error fetching data:', error);
        alert("An unexpected error occurred retrieivng profile information - are the APIs running?")
    }
}


/**
 * 
 * @param {*} div where we want to create the widget
 * @param {*} profile : the profile model from the backend system
 */
function createKaizenProfileWidget(div, profile) {
    console.log("Hello");
}

export { getKaizenProfileById, createKaizenProfileWidget, updateKaizenProfile }