HOST_ADDRESS = "192.168.0.129"

document.addEventListener("DOMContentLoaded", function () {
    getWeightTwo()
    setBoxInformation()
});

function getWeightTwo() {
    console.log('attempting to retrieve /weight/1')
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            var div = document.createElement('div');
            var paragraph = document.createElement('p');
            var textNode = document.createTextNode(xmlhttp.responseText);
            paragraph.appendChild(textNode);
            div.appendChild(paragraph);
            document.body.appendChild(div);
        }
    };

    xmlhttp.open("GET", `http://${HOST_ADDRESS}:8081/weight/1`, true)
    xmlhttp.send();
}

function setBoxInformation() {
    // Set checklist information
    var box = document.getElementById('checklist');
    var currentTime = new Date().getHours();

    if (currentTime >= 4 && currentTime < 10) {
        box.innerText = 'Morning Checklist';
    } else if (currentTime >= 10 && currentTime < 17) {
        box.innerText = 'Daily Checklist';
    } else {
        box.innerText = 'Night Checklist';
    }

    box = document.getElementById('schedule');
    box.innerText = 'Schedule'
    box = document.getElementById('weight');
    box.innerHTML = 'Weight'
    box = document.getElementById('water')
    box.innerHTML = 'Water'
}
