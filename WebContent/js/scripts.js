//$(function() {
//	$(".lang").click( function() {
//		
//	});
//});

function validateForm() {
    var form = document.getElementById("register-form");
    if (document.getElementById("pass").value 
        !== document.getElementById("repeat-pass").value) {
        document.getElementById("message").innerHTML = "Passwords do not match";
        return false;
    } else {
        document.getElementById("message").innerHTML = "Submitted";
        return true;
    }
}

