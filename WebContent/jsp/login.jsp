<%@ include file="header.jspf" %>
<div id="login">
<form action="${ pageContext.servletContext.contextPath }/login" method="POST">
	<input type="hidden" name="command" value="login"/><br/>
	<input type="text" name="login" placeholder="<fmt:message key="label.login"/>" 
		pattern="^\w{5,20}" 
		required/><br/>
	<input type="password" class="pass" name="password" placeholder="<fmt:message key="label.password"/>" 
		pattern="^(?=.*\d)(?=.*[a-zA-Z]).{10,}$" 
		required/>
	<span class="tooltiptext"><fmt:message key="label.passPattern"/></span><br/>
	<input type="submit" value="<fmt:message key="button.submit"/>"/>
</form>
<p id="message">
	<c:if test="${ !empty Message }">
		<fmt:message key='${ Message.text }'/><br>
	</c:if>
</p>
<a href="${ pageContext.servletContext.contextPath }/jsp/register.jsp">
<fmt:message key="label.register"/><br>
</a><br>
</div>
<%@ include file="footer.jspf" %>
