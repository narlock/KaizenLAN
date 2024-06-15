function todayAsString() {
    var today = new Date();
    var year = today.getFullYear();
    var month = (today.getMonth() + 1).toString().padStart(2, '0');
    var day = today.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}

function getDaysAgoDateString(days) {
    const today = new Date();
    const xDaysAgo = new Date(today);
    xDaysAgo.setDate(today.getDate() - days);
    const year = xDaysAgo.getFullYear();
    const month = String(xDaysAgo.getMonth() + 1).padStart(2, '0');
    const day = String(xDaysAgo.getDate()).padStart(2, '0');
    const dateString = `${year}-${month}-${day}`;
    return dateString;
}

function isDateInList(dateString, entries) {
    return entries.some(entry => entry.entryDate === dateString);
}

function calculateBMI(weightInPounds, heightInInches) {
    const POUNDS_TO_KILOGRAMS = 0.45359237;
    const INCHES_TO_METERS = 0.0254;
    const weightInKilograms = weightInPounds * POUNDS_TO_KILOGRAMS;
    const heightInMeters = heightInInches * INCHES_TO_METERS;
    const bmi = weightInKilograms / (heightInMeters * heightInMeters);
    return Math.round(bmi * 100) / 100;
}

function getBmiString(bmi) {
    if (bmi < 18.5) {
        return "Underweight";
    } else if (bmi >= 18.5 && bmi < 25) {
        return "Normal";
    } else if (bmi >= 25 && bmi < 30) {
        return "Overweight";
    } else {
        return "Obese";
    }
}

function areDatesEqual(date1, date2) {
    console.log(date1);
    console.log(date2);
    if(date1 == null || date2 == null) {
        return false;
    }

    return date1.getFullYear() === date2.getFullYear() &&
           date1.getMonth() === date2.getMonth() &&
           date1.getDate() === date2.getDate();
}

export { todayAsString, getDaysAgoDateString, isDateInList, calculateBMI, getBmiString, areDatesEqual }