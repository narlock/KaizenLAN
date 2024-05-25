/**
 * =================
 * DISPLAY PROFILE ENTRY
 * =================
 */

function displayProfile(profileObj) {
    const profileDiv = document.getElementById('profileWidget');

    const mainDiv = document.createElement('div');
    mainDiv.classList.add("center-container");
    console.log(mainDiv);

    const imageElement = document.createElement('img');
    imageElement.src = profileObj.profile.imageUrl;
    imageElement.style.paddingRight = "10px";

    const contentElement = document.createElement('p');
    contentElement.innerHTML = `
    <span>
        <h3>${profileObj.profile.username}</h3>
        <br>
        <b class="special">Level</b>: <b>${calculateLevel(profileObj.profile.xp)}</b> (${profileObj.profile.xp} XP)<br>
        <b>Birth Date</b>: ${profileObj.profile.birthDate}<br>
        <b>Height</b>: ${profileObj.health.height}<br>
        <b>Weight</b>: ${profileObj.health.weight}<br>
    </span>
    `;

    const xpBarDiv = document.createElement('div');
    xpBarDiv.classList.add('center-container');
    
    const xpBarElement = document.createElement('div');
    xpBarElement.id = 'xpBarProgress';
    const xpBar = document.createElement('div');
    xpBar.id = 'xpBar';
    xpBarElement.appendChild(xpBar);
    xpBarDiv.appendChild(xpBarElement);

    mainDiv.appendChild(imageElement);
    mainDiv.appendChild(contentElement);
    profileDiv.appendChild(mainDiv);
    profileDiv.appendChild(xpBarDiv);

    moveXpBar(profileObj.profile.xp, profileDiv);
}

function moveXpBar(xp, profileDiv) {
    var nextLevelXp = calculateExperienceToNextLevel(xp)
    var percent = xp / nextLevelXp

    const nextLevelElement = document.createElement('p')
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

function displayProfileAndCreateDiv(parentElement, profileObj) {
    const profileDiv = document.createElement('div');
    profileDiv.id = "profileWidget";
    parentElement.appendChild(profileDiv);

    const mainDiv = document.createElement('div');
    mainDiv.classList.add("center-container");
    console.log(mainDiv);

    const imageElement = document.createElement('img');
    imageElement.src = profileObj.profile.imageUrl;
    imageElement.style.paddingRight = "10px";

    const contentElement = document.createElement('p');
    contentElement.innerHTML = `
    <span>
        <h3>${profileObj.profile.username}</h3>
        <br>
        <b class="special">Level</b>: <b>${calculateLevel(profileObj.profile.xp)}</b> (${profileObj.profile.xp} XP)<br>
        <b>Birth Date</b>: ${profileObj.profile.birthDate}<br>
        <b>Height</b>: ${profileObj.health.height}<br>
        <b>Weight</b>: ${profileObj.health.weight}<br>
    </span>
    `;

    const xpBarDiv = document.createElement('div');
    xpBarDiv.classList.add('center-container');
    
    const xpBarElement = document.createElement('div');
    xpBarElement.id = 'xpBarProgress';
    const xpBar = document.createElement('div');
    xpBar.id = 'xpBar';
    xpBarElement.appendChild(xpBar);
    xpBarDiv.appendChild(xpBarElement);

    mainDiv.appendChild(imageElement);
    mainDiv.appendChild(contentElement);
    profileDiv.appendChild(mainDiv);
    profileDiv.appendChild(xpBarDiv);

    return xpBar;
}

function moveXpBarWithElement(xpBar, xp, profileDiv) {
    var nextLevelXp = calculateExperienceToNextLevel(xp)
    var percent = xp / nextLevelXp

    const nextLevelElement = document.createElement('p')
    nextLevelElement.innerHTML = `XP to next level: <b>${nextLevelXp}</b> (${Math.round(percent)}%)`
    nextLevelElement.style.textAlign = "center";
    profileDiv.appendChild(nextLevelElement);

    var i = 0;
    if (i == 0) {
        i = 1;
        var width = 1;
        var id = setInterval(frame, 10);
        function frame() {
        if (width >= percent) {
            clearInterval(id);
            i = 0;
        } else {
            width++;
            xpBar.style.width = width + "%";
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

export { displayProfile, displayProfileAndCreateDiv, moveXpBarWithElement }