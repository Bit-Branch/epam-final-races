<%@ include file="header.jspf" %>

<section class="balance">
	<form action="${ pageContext.servletContext.contextPath }/main" method="POST" >
	<!-- onsubmit="return validateCard()">
	 -->	
	 	<input type="hidden" name="command" value="add_balance"/>
		<input type="number" name="amount" placeholder="<fmt:message key='label.amount'/>" required/><br/><br/>
		<input type="number" name="cardNum" placeholder="<fmt:message key='label.cardNum'/>"
				pattern="(4(\d){12,15})|((51|52|53|54|55)(\d){15,18})" required/><br/>
		<input type="text" name="cardholderName" placeholder="<fmt:message key='label.cardholderName'/>" required/><br/>
		<input type="month" name="validMonth" placeholder="<fmt:message key='label.validMonth'/>" required/><br/>
		<input type="number" name="cvvNum" placeholder="<fmt:message key='label.cvvNum'/>"
				pattern="(\d){3}" required/><br/>
		<input type="submit" name="submit" value="<fmt:message key='button.submit'/>"/><br/>
	</form>
	<p id="message"><m:printMessage/></p>	
	<a href="${pageContext.servletContext.contextPath }"><fmt:message key="button.back"/></a>
</section>

<%@ include file="footer.jspf" %>