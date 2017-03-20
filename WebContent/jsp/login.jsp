<%@ include file="header.jspf" %>
<form action="${ pageContext.servletContext.contextPath }/main" method="POST">
	<input type="hidden" name="requestType" value="login"/><br/>
	<input type="text" name="login" placeholder="<fmt:message key="label.login"/>" pattern="^\w{5,20}" required/><br/>
	<input type="password" name="password" placeholder="<fmt:message key="label.password"/>" pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
	<input type="submit" value="<fmt:message key="button.submit"/>"/>
</form>
<p>${ result }</p>
<a href="${ pageContext.servletContext.contextPath }/jsp/register.jsp">
<fmt:message key="label.register"/>
</a><br>
<%@ include file="footer.jspf" %>
