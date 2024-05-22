import * as ProfileUtils from '.././lib/profileLoader.js'

document.addEventListener('DOMContentLoaded', async function() {
    console.log('DOM fully loaded and parsed, let\'s initialize our widgets');
    try {
        var profile = await ProfileUtils.getKaizenProfileById(2);
        console.log(profile);
    } catch (error) {
        console.error('Failed to load profile:', error);
    }
});
