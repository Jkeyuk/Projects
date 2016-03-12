var mailList = document.getElementById("mailList");
var addForm = document.getElementById("addForm");
var addItemSubmitButton = document.getElementById("addItemSubmitButton");
var addItemButton = document.getElementById("addItemButton");

addItemSubmitButton.addEventListener('click', addMail, false);
addItemButton.addEventListener('click', showForm, false);

function getMail() {
    //make request object
    var xhttp = new XMLHttpRequest();
    //on recieve response do..
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            mailList.innerHTML = "<tr> <td>Address/Unit</td>\n\
                                       <td>Recipient Name</td>\n\
                                       <td>Tracking #</td>\n\
                                       <td>Package Type</td>\n\
                                 </tr>";
            mailList.innerHTML += xhttp.responseText;
            addRemoveButEvents();
        }
    };//config request and send
    xhttp.open("GET", "./MailServlet", true);
    xhttp.send();
}
function addMail() {
    //parse data
    var address = document.getElementById("inAddress").value;
    var name = document.getElementById("inName").value;
    var tracking = document.getElementById("inTrack").value;
    var type = document.getElementById("inType").value;
    //build request body
    var paraValues = "address=" + address + "&" + "name=" + name + "&" + "tracking=" + tracking + "&" + "type=" + type;
    //make request object
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 201) {
            getMail();
            addForm.style.display = "none";
            addItemButton.style.display = "inherit";
        }
    };//config request
    xhttp.open("POST", "./MailServlet", true);
    //set content type
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //send request
    xhttp.send(paraValues);
}
function removeItem() {
    var value = this.parentNode.parentNode.childNodes[2].innerHTML;
    //make request object
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            getMail();
        }
    };
    //config request
    xhttp.open("DELETE", "./MailServlet?tracking=" + value, true);
    //send request
    xhttp.send();
}
function showForm() {//shows form to add another mail item
    if (addForm.style.display === "none") {
        addForm.style.display = "inherit";
        addItemButton.style.display = "none";
    }
}
function addRemoveButEvents() {//attaches events to remove buttons
    var removeButs = document.getElementsByClassName("removeBut");
    for (i = 0; i < removeButs.length; i++) {
        removeButs[i].addEventListener('click', removeItem, false);
    }
}
getMail();