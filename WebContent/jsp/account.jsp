<%@ include file="header.jspf" %>

<section id="account">
	<h5><fmt:message key="label.deleteAccount"/></h5>
	<form method="post">
		<input type="hidden" name="requestType" value="deleteAccount"/>
		<input type="submit" value="<fmt:message key='button.delete'/>"/>
	</form>
</section>

<%@ include file="footer.jspf" %>
