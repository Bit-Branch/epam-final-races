<%@ include file="header.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:if test="${ empty sessionScope.role }">
	<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>
	<nav>
		<ul>
			<li><a href="#">Schedule</a></li>				
			<li><a href="#">Results</a></li>
			<li><a href="#">My bets</a></li>
			<li><a href="#">Account</a></li>
			<li><a href="${ pageContext.servletContext.contextPath }/logout">Logout</a></li>
		</ul>
	</nav>
	<main class="content">
		<section>
			Welcome ${ sessionScope.login }!<br/>
			
		</section>
    </main>
<%@ include file="footer.jspf" %>


