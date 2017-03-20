<%@ include file="header.jspf" %>
<form id="register-form" action="${ pageContext.servletContext.contextPath }/register" 
		onsubmit="return validateForm()" method="POST">
	<input type="hidden" name="requestType" value="register"/><br/>
	<input type="text" name="login" placeholder="Login" pattern="^\w{5,20}" required/><br/>
	<input type="email" name="email" placeholder="E-mail" disabled/><br/>
	<input type="password" id="pass" name="password" placeholder="Password" 
					pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$" required/><br/> 
	
	<input type="password" id="repeat-pass" name="password-repeat" placeholder="Repeat password"
					pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$" required/><br/> 
	<input type="submit" name="submit" value="Submit"/>
</form>
<p id="message">${ result }</p>
<%@ include file="footer.jspf" %>