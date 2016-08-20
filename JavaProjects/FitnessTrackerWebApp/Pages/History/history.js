//ajax requests
function ajaxGetRequest() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            var parsedData = parseResponseData(xhttp.responseText);
            buildHistoryTable(parsedData);
        } else if (xhttp.readyState === 4 && xhttp.status === 204) {
            document.getElementById("historyWrap").innerHTML =
                    "<h3>No History Yet, you can add a workout to start your history</h3>";
        }
    };
    xhttp.open("GET", "./History", true);
    xhttp.send();
}
//parse json response and return array of javascript objects
function parseResponseData(data) {
    var arrayOfJsonData = data.split("?");
    var arrayOfObjects = [];
    for (var i = 0; i < (arrayOfJsonData.length - 1); i++) {
        var objectFromJson = JSON.parse(arrayOfJsonData[i]);
        arrayOfObjects.push(objectFromJson);
    }
    return arrayOfObjects;
}
//build history table from array of javascript objects
function buildHistoryTable(arrayOfData) {
    var historyWrap = document.getElementById("historyWrap");
    historyWrap.innerHTML = "<h1>Your History</h1>";
    for (var i = 0; i < arrayOfData.length; i++) {
        historyWrap.innerHTML += "<h3>" + arrayOfData[i].date + " "
                + arrayOfData[i].name + "</h3>";
        for (var j = 0; j < arrayOfData[i].sets.length; j++) {
            historyWrap.innerHTML += "<table><tr><td>" + arrayOfData[i].sets[j].name
                    + "</td><td>" + "Reps " + arrayOfData[i].sets[j].reps + "</td><td>" +
                    "@ " + arrayOfData[i].sets[j].weight + " lbs" + "</td></tr></table>";
        }
    }
}
ajaxGetRequest();