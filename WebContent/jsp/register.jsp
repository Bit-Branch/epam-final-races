<%@ include file="header.jspf" %>
<form action="main" method="POST">
	<input type="hidden" name="requestType" value="register"/><br/>
	<input type="text" name="login" pattern="^\w{5,20}" required/><br/>
	<input type="password" name="password" pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
	<input type="password" name="password-repeat" pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
	<input type="file" formenctype="multipart/form-data" value="fileToSubmit"/>
	<input type="submit" value="Submit"/>
</form>
<p>${ result }</p>
<a href="/register.jsp"></a>
<%@ include file="footer.jspf" %>