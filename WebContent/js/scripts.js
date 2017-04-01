/*global $*/
	
$(function() {
//	$.get("ajax", function(data) {
//		console.log(data);
//	});
	
});
function placeBet() {
	console.log("works");
	var formdata = new FormData($("#place-bet"));
	$.post("placeBet", formdata).done(function(data) {
		console.log(data);
		$(".content#results").html(data);
	});
	return false;
}
	
	
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


