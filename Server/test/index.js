
var menu = document.getElementById('menu');
var titleBox = document.getElementById('titleBox');
var noteBox = document.getElementById('noteBox');
var notesMenuButton = document.getElementById('notesMenuButton');
var deleteNote = document.getElementById('deleteNote');
var savedNotes = [];
//NOTE OBJECT constructor**********************************
var note = function (name, content, index) {
    this.name = name;
    this.content = content;
    this.index = index;

    //display note data
    this.displayData = function () {
        titleBox.value = '';
        noteBox.value = '';
        titleBox.value = this.name;
        noteBox.value = this.content;
    };
    //store note data 
    this.storeData = function () {
        this.name = titleBox.value;
        this.content = noteBox.value;
        savedNotes[this.index] = this;
        var objectAsJson = JSON.stringify(savedNotes);
        localStorage.setItem('notes', objectAsJson);
    };

};
//********INITIALIZE*******************

function newNote() {
    var x = savedNotes.length;
    noteObject = new note('', '', x);
    noteObject.displayData();
    menu.style.width = '0%';
}
//************Display Saves in Menu*******************
notesMenuButton.addEventListener('click', displaySavesToMenu, false);

function displaySavesToMenu() {
    if (localStorage.getItem('notes')) {
        var noteData = JSON.parse(localStorage.getItem('notes'));
        savedNotes = noteData;
    }
    var x = savedNotes.length; //counter
    var y = '<button class="menuItem">+New Note</button>';

    menu.innerHTML = '';
    for (var i = 0; i < x; i++) {
        menu.innerHTML += '<button class="menuItem">' + savedNotes[i].name + '</button>';
    }
    menu.innerHTML += y;

    //add events to menu buttons
    function events() {
        menuItem = document.getElementsByClassName("menuItem");
        var newbuttonspot = (menuItem.length - 1);
        var x = savedNotes.length;
        menuItem[newbuttonspot].addEventListener('click', newNote, false);
        for (var i = 0; i < x; i++) {
            menuItem[i].addEventListener('click', loadObject, false);
        }
    }
    events();
    menu.style.width = '100%';
}
//load object***********************************
function loadObject() {
    var x = this.innerHTML;
    var counter = savedNotes.length;
    var savedLength = savedNotes.length;

    function findIndex(name) {
        for (var i = 0; i < counter; i++) {
            if (savedNotes[i].name === x) {
                return i;
            }
        }
    }

    var tempObj = savedNotes[findIndex(x)];
    noteObject = new note(tempObj.name, tempObj.content, findIndex(x));
    noteObject.displayData();
    menu.style.width = '0%';
    noteObject.storeData();
}
//AUTO SAVE feature*************************
titleBox.addEventListener('input', autoSave, false);
noteBox.addEventListener('input', autoSave, false);

function autoSave() {
    if (titleBox.value.length === 0 || titleBox.value === " ") {
        titleBox.value += '*';
    }
    noteObject.storeData();
}
//DELETE NOTE******************************************
deleteNote.addEventListener('click', removeNote, false);

function removeNote() {
    var x = window.confirm('ARE YOU SURE YOU WANT TO DELETE');
    if (x) {
        savedNotes.splice(noteObject.index, 1);
        var objectAsJson = JSON.stringify(savedNotes);
        localStorage.setItem('notes', objectAsJson);
        displaySavesToMenu();
    } else {
        return;
    }
}
/////dev tools
function test() {
    window.alert(titleBox.value.length);
    // window.alert(savedNotes[0]);
    // window.alert(savedNotes);

}

displaySavesToMenu();
//loadObject();
//test();
//localStorage.clear();