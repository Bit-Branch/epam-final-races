<%@ include file="header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<main class="content">
	
		<c:if test="${ sessionScope.User.role eq 'USER' }">
			<%@ include file="user.jspf" %>
		</c:if>
		<c:if test="${ sessionScope.User.role eq 'ADMIN' }">
			<%@ include file="admin.jspf" %>
		</c:if>
		<c:if test="${ empty sessionScope.User }">
			<jsp:forward page="/index.jsp"></jsp:forward>
		</c:if>
    </main>
<%@ include file="footer.jspf" %>

