var nameBox = document.getElementById('nameBox');

var cookies = document.cookie.split('=');

nameBox.innerHTML = cookies[1];