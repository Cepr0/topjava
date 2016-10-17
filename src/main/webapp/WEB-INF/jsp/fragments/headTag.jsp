<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="app.title"/></title>
    <%--<spring:url value="/resources/css/style.css" var="mainCss" />--%>

    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">--%>
    <c:set var="root">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(root, 0, fn:length(root) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
    <script>var base = document.getElementsByTagName("base")[0].href;</script>
    <%--<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">--%>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <%--<link href="${mainCss}" rel="stylesheet" />--%>
</head>