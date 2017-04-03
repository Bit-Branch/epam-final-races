/*global $*/
	
$(function() {
//	$.get("ajax", function(data) {
//		console.log(data);
//	});
	$.getScript('js/pagination.js', function() {
	    $("#races-table") 
		    .tablesorter({widthFixed: true, widgets: ['zebra']}) 
		    .tablesorterPager({container: $("#pager")}); 
	});
});
function placeBet() {
	var formdata = new FormData($("#place-bet")[0]);
	$.post("placeBet", formdata).done(function(data) {
		console.log(data);
		$(".content#results").html(data);
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


