async function getKaizenProfileById(profileId) {
    const url = "http://localhost:8080/graphql"; // Ensure 'http://' is included
    const query = `
        query {
            profile(id: ${profileId}) {
                id
                username
                birthDate
                imageUrl
                xp
                numRows
                pin
                health {
                    weight
                    height
                    goalWeight
                    goalWater
                }
                rows {
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
        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching data:', error);
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

export { getKaizenProfileById, createKaizenProfileWidget }