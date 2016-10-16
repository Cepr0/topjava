<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3>
        <%--<c:set var="page_title" value='meal.edit.title'/>--%>
        <%--<c:if test="${param.action == 'create'}">--%>
        <%--<c:set var="page_title" value='meal.create.title'/>--%>
        <%--</c:if>--%>
        <%--<fmt:message key="${page_title}"/>--%>
        Message: ${exception.message}
    </h3>
    <p>Request Uri: ${url}</p>
    <p></p>
    <hr>
    <c:forEach items="${exception.stackTrace}" var="ste">
        ${ste}
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
