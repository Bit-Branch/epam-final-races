<%@ include file="header.jspf" %>

<section id="profile">
	<h5><fmt:message key="label.deleteProfile"/></h5>
	<form action="${ pageContext.servletContext.contextPath }/main" method="post">
		<input type="hidden" name="command" value="delete_profile"/>
		<input type="password" name="password" 
			   placeholder="<fmt:message key="label.password"/>" 
			   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
		
		<input type="submit" value="<fmt:message key='button.delete'/>"/>
	</form>
</section>

<%@ include file="footer.jspf" %>
