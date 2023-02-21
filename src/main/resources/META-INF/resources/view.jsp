<%@ include file="./init.jsp"%>

<c:choose>
	
	<c:when test="${empty paso}">
		<jsp:include page="pantallas/paso1.jsp" />
	</c:when>

	<c:when test="${paso == 'paso1'}">
		<jsp:include page="pantallas/paso1.jsp" />
	</c:when>
	
	<c:when test="${paso == 'paso2'}">
		<jsp:include page="pantallas/paso2.jsp" />
	</c:when>
	
	<c:when test="${paso == 'paso3'}">
		<jsp:include page="pantallas/paso3.jsp" />
	</c:when>

	<c:when test="${paso == 'paso4'}">
		<jsp:include page="pantallas/paso4.jsp" />
	</c:when>
	
	<c:otherwise>
		<jsp:include page="pantallas/paso1.jsp" />
	</c:otherwise>

</c:choose>