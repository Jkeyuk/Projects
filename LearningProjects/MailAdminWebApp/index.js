var userSubButton = document.getElementById("userSubButton");
var signUpBut = document.getElementById("signUpBut");
var signUpFormBut = document.getElementById("signUpFormBut");
var responseBox = document.getElementById("responseBox");
var signForm = document.getElementById("signForm");

userSubButton.addEventListener('click', login, false);
signUpBut.addEventListener('click', signUp, false);
signUpFormBut.addEventListener('click', showSignForm, false);

function login() {
    //parse login data
    var username = document.getElementById("userIn").value;
    var password = document.getElementById("passIn").value;

    //build request body
    var paraValues = "username=" + username + "&" + "password=" + password;
    //make request object
    var xhttp = new XMLHttpRequest();
    //on recieve response do..
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 403) {
            responseBox.innerHTML = "<h1>Username and Password DO NOT MATCH!</h1>";
        } else if (xhttp.readyState === 4 && xhttp.status === 200) {
            window.location = "./pages/restricted/home.html";
        }
    };
    //config request
    xhttp.open("POST", "./LoginServlet", true);
    //set content type
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //send request
    xhttp.send(paraValues);
}
function signUp() {
    //parse login data
    var username = document.getElementById("signUpUser").value;
    var password = document.getElementById("signUpPass").value;
    //build request body
    var paraValues = "username=" + username + "&" + "password=" + password;
    //make request object
    var xhttp = new XMLHttpRequest();
    //on recieve response do..
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 409) {
            responseBox.innerHTML = "<h1>UserName In Use, Pick Another</h1>";
        } else if (xhttp.readyState === 4 && xhttp.status === 201) {
            responseBox.innerHTML = "<h1>User Added! Log In";
            signForm.style.display = "none";
            signUpFormBut.style.display = "inherit";
        }
    };
    //config request
    xhttp.open("POST", "./UserAccounts", true);
    //set content type
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //send request
    xhttp.send(paraValues);
}

function showSignForm() {
    if (signForm.style.display === "none") {
        signForm.style.display = "inherit";
        signUpFormBut.style.display = "none";
    }
}