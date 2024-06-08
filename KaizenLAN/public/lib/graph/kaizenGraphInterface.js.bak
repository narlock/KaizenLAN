const GRAPH_ENDPOINT = "http://localhost:8080/graphql";

async function request(query) {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({query})
    };
    try {
        const response = await fetch(GRAPH_ENDPOINT, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        var responseBody = await response.json();
        return responseBody.data
    } catch (error) {
        console.error('Error fetching data:', error);
        alert("An unexpected error occurred when querying kaizen graph - are the APIs running?")
    }
}

export { request }