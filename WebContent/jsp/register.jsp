<%@ include file="header.jspf" %>
<section class="authorize" id="register">
	<form id="register-form" action="${ pageContext.servletContext.contextPath }/register" 
			onsubmit="return validateForm()" method="POST">
		<input type="hidden" name="command" value="register"/><br/>
		<input type="text" name="login" placeholder="<fmt:message key="label.login"/>" 
						pattern="^\w{5,20}" required/><br/>
		<input type="password" class="pass" name="password" placeholder="<fmt:message key='label.password'/>" 
	 					pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$"
						required/>
	  	<span class="tooltiptext"><fmt:message key="label.passPattern"/></span><br/> 
		<input type="password" class="pass" name="repeatPassword" 
						placeholder="<fmt:message key='label.repeatPassword'/>"
	 					pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$" 
						required/><br/> 
		<input type="submit" name="submit" value="Submit"/>
	</form>
	<p id="message"><m:printMessage/></p>

	<a href="${pageContext.servletContext.contextPath }"><fmt:message key="button.back"/></a>
</section>
<%@ include file="footer.jspf" %>