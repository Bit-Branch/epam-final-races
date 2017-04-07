/*global $*/
	
$(function() {
//	$.get("ajax", function(data) {
//		console.log(data);
//	});
	
	var iframe = $('#results');
	setTimeout(function() { iframe.src = iframe.src; }, 30000);
	
	
});
function placeBet() {
	var formdata = new FormData($("#place-bet"));
	$.post("placeBet", formdata, function(data) {
		console.log(data);
		$("#content").html(data);
	});
	console.log("works");
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


