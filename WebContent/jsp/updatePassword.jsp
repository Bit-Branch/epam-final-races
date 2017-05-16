<%@ include file="header.jspf" %>

<section class="profile" id="update-password">
	<h5><fmt:message key="label.updatePassword"/></h5>
	<form action="${ pageContext.servletContext.contextPath }/updatePass" method="post">
		<input type="hidden" name="command" value="update_password"/>
		<input type="hidden"  name="login" value="${ sessionScope.User.login }"/>
		<input type="password" name="oldPassword" class="pass"
			   placeholder="<fmt:message key="label.oldPassword"/>" 
			   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
		<input type="password" name="password" class="pass"
			   placeholder="<fmt:message key="label.newPassword"/>" 
			   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
		<input type="password" name="repeatPassword" class="pass"
			   placeholder="<fmt:message key="label.repeatPassword"/>" 
			   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{6,}$" required/><br/>
		<input type="submit" value="<fmt:message key='button.update'/>"/><br/>
	</form>
	<c:if test="${ !empty Message }">
		<fmt:message key="${ Message.text }"/><br/>
	</c:if>
	<a href="${pageContext.servletContext.contextPath }"><fmt:message key="button.back"/></a>
</section>


<%@ include file="footer.jspf" %>
