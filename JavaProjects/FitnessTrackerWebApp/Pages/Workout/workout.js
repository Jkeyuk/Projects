var workTypeInput = document.getElementById('workTypeInput');
var exerSubBut = document.getElementById('exerSubBut');
var finishedExerBut = document.getElementById('finishedExerBut');
var workTypeWrap = document.getElementById('workTypeWrap');
var weightTableWrap = document.getElementById('weightTableWrap');
var formSetup = document.getElementById('formSetup');
var weightForm = document.getElementById('weightForm');
var exerNameHeader = document.getElementById('exerNameHeader');
var dateHeader = document.getElementById('dateHeader');
//EVENT FOR BUTTONS AND INPUTS
workTypeInput.addEventListener('change', selectForm, false);
exerSubBut.addEventListener('click', setWeightForm, false);
finishedExerBut.addEventListener('click', sendWeightExer, false);
//change workout form based on type selected
function selectForm() {
    if (workTypeInput.value === 'weightlifting') {
        weightForm.style.display = 'inherit';
        weightTableWrap.style.display = 'none';
        formSetup.style.display = 'inherit';
    }
}
//initialize weightlifting log table
function setWeightForm() {
    var exerNameInput = document.getElementById('exerNameInput');
    var dateInput = document.getElementById('dateInput');
    var dataTable = document.getElementById('dataTable');
    var numOfSets = document.getElementById('setNumInput').value || 1;
    exerNameHeader.innerHTML = exerNameInput.value.toUpperCase() || "Exercise";
    dateHeader.innerHTML = dateInput.value || "No Date";
    dataTable.innerHTML = '<tr><th>Set #</th><th>Reps</th><th>Weight</th></tr>';
    for (var i = 1; i <= numOfSets; i++) {
        dataTable.innerHTML += '<tr class="dataRow"><td>Set ' + i +
                '</td><td><input type="number"></td><td><input type="number"></td></tr>';
    }
    formSetup.style.display = 'none';
    finishedExerBut.style.display = 'inherit';
    workTypeWrap.style.display = 'none';
    weightTableWrap.style.display = 'inherit';
}
//Send Exercise to database
function sendWeightExer() {
    //get table values
    var arrayToHoldSets = parseWeightTableData();
    //get header values
    var dateOfExercise = dateHeader.innerHTML;
    var nameOfExercise = exerNameHeader.innerHTML;
    //build exercise object with table data and 
    var exerciseObject = new exercise(dateOfExercise, nameOfExercise, arrayToHoldSets);
    //turn exercise object to json string
    var objectAsJsonString = JSON.stringify(exerciseObject);
    //send json to server
    ajaxPostWeightData(objectAsJsonString);
}
//object constructors***************************************************
function set(name, reps, weight) {
    this.name = name;
    this.reps = reps || 0;
    this.weight = weight || 0;
}
function exercise(date, name, sets) {
    this.date = date;
    this.name = name;
    this.sets = sets;
}
//AJAX REQUESTS***********************************************************
function ajaxPostWeightData(string) {
    var http = new XMLHttpRequest();
    http.onreadystatechange = function () {
        if (http.readyState === 4 && http.status === 201) {
            //reset display
            resetNewWorkoutDisplay();
            ajaxGetRequest();
        }
    };
    http.open("POST", "./Workout", true);
    http.setRequestHeader("Content-type", "application/json");
    http.send(string);
}
function ajaxGetRequest() {
    var xhttp = new XMLHttpRequest();
    var date = buildDate();
    var url = "./Workout?date=" + date;
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            var summaryData = parseSummaryData(xhttp.responseText);
            buildSummaryTable(summaryData);
        } else if (xhttp.readyState === 4 && xhttp.status === 204) {
            document.getElementById('summaryWrap').innerHTML =
                    "<h3>No Workouts Yet Today</h3>";
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}
//utility functions******************************************************
//reset the forms to select another exercise
function resetNewWorkoutDisplay() {
    var workTypeOptions = document.getElementsByTagName('option');
    workTypeWrap.style.display = 'inherit';
    weightForm.style.display = 'none';
    workTypeInput.value = workTypeOptions[0].value;
}
//gets the data from the weight log table and returns as array
function parseWeightTableData() {
    var arrayToHoldSets = [];
    var setData = document.getElementsByClassName('dataRow');
    //parse table data and add to array
    for (var i = 0; i < setData.length; i++) {
        var childNodes = setData[i].children;
        var setName = childNodes[0].innerHTML;
        var numOfReps = childNodes[1].firstChild.value;
        var weight = childNodes[2].firstChild.value;
        var setToAdd = new set(setName, numOfReps, weight);
        arrayToHoldSets.push(setToAdd);
    }
    return arrayToHoldSets;
}
//parse summary data from server response return data as array
function parseSummaryData(data) {
    var arrayOfJsonData = data.split("?");
    var arrayOfObjects = [];
    for (var i = 0; i < (arrayOfJsonData.length - 1); i++) {
        var objectFromJson = JSON.parse(arrayOfJsonData[i]);
        arrayOfObjects.push(objectFromJson);
    }
    return arrayOfObjects;
}
//build summary table from data
function buildSummaryTable(data) {
    var summaryWrap = document.getElementById('summaryWrap');
    summaryWrap.innerHTML = "<h4>Summary Of Todays Workouts</h4>";
    for (var i = 0; i < data.length; i++) {
        summaryWrap.innerHTML += "<p>" + data[i].name + "</p>";
        for (var j = 0; j < data[i].sets.length; j++) {
            summaryWrap.innerHTML += "<table><tr><td>" + data[i].sets[j].name
                    + "</td><td>" + "Reps " + data[i].sets[j].reps + "</td><td>" +
                    "@ " + data[i].sets[j].weight + " lbs" + "</td></tr></table>";
        }
    }
}
//builds the date to be compliant with server
function buildDate() {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth() + 1;
    var year = today.getFullYear();
    if (day < 10) {
        day = "0" + day;
    }
    if (month < 10) {
        month = "0" + month;
    }
    var date = year + "-" + month + "-" + day;
    return date;
}
ajaxGetRequest();

