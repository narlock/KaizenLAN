import * as ProfileUtils from '.././lib/graph/profileLoader.js'
import * as ProfileWidget from '.././lib/profileWidget.js'

var PROFILE;

document.addEventListener('DOMContentLoaded', () => {
    retrieveProfileDetailsAndPopulatePage();
});

async function retrieveProfileDetailsAndPopulatePage() {
    PROFILE = await ProfileUtils.getKaizenProfileById(1);
    console.log(PROFILE);
    ProfileWidget.displayProfile(PROFILE);
}
