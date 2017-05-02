<%@ include file="header.jspf" %>

<section class="profile" id="delete-profile">
	<h5><fmt:message key="label.deleteProfile"/></h5>
	<form action="${ pageContext.servletContext.contextPath }/main" method="post">
		<input type="hidden" name="command" value="delete_profile"/>
		<input type="hidden"  name="login" value="${ sessionScope.User.login }"/>
		<input type="password" name="password" 
			   placeholder="<fmt:message key="label.password"/>" 
			   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
		<input type="submit" value="<fmt:message key='button.delete'/>"/>
	</form>
</section>

<c:if test="${ !empty Message }">
	<fmt:message key="${ Message.text }"/><br>
</c:if>
<a href="${ pageContext.servletContext.contextPath }"><fmt:message key="button.back"/></a>

<%@ include file="footer.jspf" %>
