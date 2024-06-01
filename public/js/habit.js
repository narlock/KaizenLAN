import * as HabitWidget from '../lib/habitWidget.js'
import * as HabitLoader from '../lib/graph/habitLoader.js'

var HABIT_NAMES;

document.addEventListener('DOMContentLoaded', async function() {
    document.getElementById('startHabit').addEventListener('click', startHabit);
    document.getElementById('deleteHabit').addEventListener('click', deleteHabit);

    try {
        // Retrieve habits
        HABIT_NAMES = await HabitLoader.getHabitNames(1);

        // Sort the list
        HABIT_NAMES.sort((a, b) => a.name.localeCompare(b.name));

        // Add interface for each habit after sorting
        for (let habit of HABIT_NAMES) {
            var info = await HabitLoader.getHabitInformation(habit.name, 1);
            HabitWidget.displayYearView(habit.name, info.entries, info.streak);
        }
    } catch (error) {
        console.error('An unexpected error occurred:', error);
    }
});


async function startHabit() {
    var habitName = window.prompt("Please enter the name of a habit to begin:");

    if(habitName) {
        await HabitLoader.saveHabit(habitName, 1);
        console.log(`Habit '${habitName}' created successfully.`);
        window.location.reload(true);
    } else {
        console.log("No habit name provided. Creation cancelled.");
    }
}

async function deleteHabit() {
    // Prompt the user for the habit name
    var habitName = window.prompt("Please enter the name of the habit to delete:");

    // If a habit name is provided, proceed with deletion
    if (habitName) {
        await HabitLoader.deleteHabit(habitName, 1);
        console.log(`Habit '${habitName}' deleted successfully.`);
        window.location.reload(true);
    } else {
        console.log("No habit name provided. Deletion cancelled.");
    }
}
