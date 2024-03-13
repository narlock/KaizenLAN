const HOST_ADDRESS = "192.168.0.129";
let PROFILE_ENTRY = null;

/**
 * =================
 * ON LOAD FUNCTIONS
 * =================
 */

document.addEventListener("DOMContentLoaded", function () {
    getProfileEntry();
});

/**
 * =================
 * GET PROFILE ENTRY
 * =================
 */

// At the moment, we only support one profile, so we will just search for /profile/1
function getProfileEntry() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            // Show profile details
            PROFILE_ENTRY = JSON.parse(xmlhttp.response)
            console.log(PROFILE_ENTRY)
            displayProfile();
        } else if (this.readyState == 4 && this.status == 404) {
            // Show create profile details
            console.log("Profile not found, showing create profile interface")

            // TODO add create profile logic
        } else if (this.readyState == 4) {
            // Unknown error occurred
            console.error("an unexpected error occurred when calling kaizen-profile-api (is it running?)")
        }
    };

    xmlhttp.open('GET', `http://${HOST_ADDRESS}:8079/profile/1`, true);
    xmlhttp.send();
}

/**
 * =================
 * CREATE PROFILE ENTRY
 * =================
 */

// TODO

/**
 * =================
 * DISPLAY PROFILE ENTRY
 * =================
 */

function displayProfile() {
    var profileDiv = document.getElementById('profile');

    var mainDiv = document.createElement('div');
    mainDiv.classList.add("center-container");

    var imageElement = document.createElement('img');
    imageElement.src = PROFILE_ENTRY.image_url;

    var contentElement = document.createElement('p');
    contentElement.innerHTML = `
    <span>
        <h3>${PROFILE_ENTRY.username}</h3>
        <br>
        <b class="special">Level</b>: <b>${calculateLevel(PROFILE_ENTRY.xp)}</b> (${PROFILE_ENTRY.xp} XP)<br>
        <b>Age</b>: ${PROFILE_ENTRY.age}<br>
        <b>Birth Date</b>: ${PROFILE_ENTRY.birth_date}<br>
        <b>Height</b>: ${PROFILE_ENTRY.health.height}<br>
        <b>Weight</b>: ${PROFILE_ENTRY.health.weight}<br>
    </span>
    `;

    var xpBarDiv = document.createElement('div');
    xpBarDiv.classList.add('center-container');
    
    var xpBarElement = document.createElement('div');
    xpBarElement.id = 'xpBarProgress';
    var xpBar = document.createElement('div');
    xpBar.id = 'xpBar';
    xpBarElement.appendChild(xpBar);
    xpBarDiv.appendChild(xpBarElement);

    mainDiv.appendChild(imageElement);
    mainDiv.appendChild(contentElement);
    profileDiv.appendChild(mainDiv);
    profileDiv.appendChild(xpBarDiv);

    moveXpBar(PROFILE_ENTRY.xp, profileDiv);
}

function moveXpBar(xp, profileDiv) {
    var nextLevelXp = calculateExperienceToNextLevel(xp)
    var percent = xp / nextLevelXp

    var nextLevelElement = document.createElement('p')
    nextLevelElement.innerHTML = `XP to next level: <b>${nextLevelXp}</b> (${Math.round(percent)}%)`
    nextLevelElement.style.textAlign = "center";
    profileDiv.appendChild(nextLevelElement);

    var i = 0;
    if (i == 0) {
        i = 1;
        var elem = document.getElementById("xpBar");
        var width = 1;
        var id = setInterval(frame, 10);
        function frame() {
        if (width >= percent) {
            clearInterval(id);
            i = 0;
        } else {
            width++;
            elem.style.width = width + "%";
        }
        }
    }
} 

/**
 * =================
 * HELPER METHODS
 * =================
 */

function calculateLevel(experience) {
    // Define the base experience required for level 1 and the experience growth factor
    const baseExperience = 100;
    const experienceGrowthFactor = 1.2;

    // Ensure the input is a non-negative integer
    const validatedExperience = Math.max(0, Math.floor(experience));

    // Calculate the level using the exponential formula
    const level = Math.floor(Math.log(validatedExperience / baseExperience) / Math.log(experienceGrowthFactor)) + 1;

    return level;
}

function calculateExperienceToNextLevel(currentExperience) {
    // Define the base experience for level 1 and the experience growth factor
    const baseExperience = 100;
    const experienceGrowthFactor = 1.2;

    // Ensure the input is a non-negative integer
    const validatedExperience = Math.max(0, Math.floor(currentExperience));

    // Calculate the current level using the same formula as before
    const currentLevel = Math.floor(Math.log(validatedExperience / baseExperience) / Math.log(experienceGrowthFactor)) + 1;

    // Calculate the experience required for the next level
    const experienceForNextLevel = Math.ceil(baseExperience * Math.pow(experienceGrowthFactor, currentLevel));

    // Calculate the difference between the experience for the next level and the current experience
    const experienceNeededForNextLevel = Math.max(0, experienceForNextLevel - validatedExperience);

    return experienceNeededForNextLevel;
}