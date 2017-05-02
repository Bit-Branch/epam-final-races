/*global $*/
/*global window*/
	
$(function() {
	// get race results no matter whether user placed a bet or not
	$.getJSON("/horserace-web/results", function(data) {
		// better to user some template language here
		var content = "<table id='fin-results'><thead><tr><td>Finish</td>" +
				"<td>Horse number</td>" +
				"<td>Horse name</td></tr></thead><tbody>" +
				"<tr class='row-1'></tr><tr class='row-2'></tr><tr class='row-3'></tr>" +
//				"<tr class='row-4'></tr><tr class='row-5'></tr>" +
//				"<tr class='row-6'></tr><tr class='row-7'></tr>" +
				"</tbody></table>";
		
		var gif = "<img src='img/horse-racing.gif' alt='running' id='race-gif'/>";
		$("#results-content").html(gif);

		window.setTimeout(
			function() {
				$("#results-content").html(content);
				data["horseUnits"].forEach( function(unit) {
					var fin = unit.finalPosition;
					if (fin == 1 || fin == 2 || fin == 3) {
						var row = "<td>" + unit.finalPosition + "</td>" 
						+ "<td>" + unit.numberInRace  + "</td>"
						+ "<td>" + unit.horse.name    + "</td>";
						$("#fin-results .row-"+unit.finalPosition).html(row);
					}
				});
			}, 10000);
	});
	
	$("#place-bet-form").on("submit", function(event) { placeBet(event); });
});


function placeBet(event) {
	event.preventDefault();
	var formData = $("#place-bet-form").serialize();
	console.log(formData);

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			console.log(response);
			if (response.betId !== undefined) {
				var bet = response;
				window.setTimeout( function() {
					$("#balance").html(bet.user.balance);
					if (bet.winning != 0) {
						$("#bet-message").html("You won $" + bet.winning + "!");
					}
				}, 10000);
			} 
		}
	};
	xhttp.open("POST", "/horserace-web/placeBet", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(formData);
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
	$("#ul-lang").css("visibility", function() {
		return $(this).css("visibility") === "visible" 
										   ? "hidden" 
										   : "visible";
	});
}

function displayMenu() {
	$("nav").css("display", function() {
		return $(this).css("display") === "none"
										? "inline-block" 
										: "none"; 
	});
}



