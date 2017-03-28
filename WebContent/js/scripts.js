/*global $*/
	
$(function() {
	console.log("works");
	$.get("ajax", function(data) {
		console.log(data);
	});
});

function validateForm() {
    var form = document.getElementById("register-form");
    if (document.getElementById("pass").value 
        !== document.getElementById("repeat-pass").value) {
        document.getElementById("message").innerHTML = "Passwords do not match";
        return false;
    }
}

function toggleLang() {
}


