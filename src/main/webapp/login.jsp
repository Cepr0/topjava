<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>TopJava Calories Manager - Sign in</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Calories Manager</h2>
<a href="https://github.com/JavaWebinar/topjava08" target="_blank">Java Enterprise (Topjava) 2016</a>
<br><br>
<hr>
<h2>Sign in form</h2>
<%--<jsp:useBean id="userList" scope="request" type="java.util.List"/>--%>
<form method="post">
    <input type="hidden" name="form" value="login">
    <dl>
        <dt>User:</dt>
        <dd>
            <select name="userId">
                <c:forEach items="${userList}" var="user">
                    <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                    <option value="${user.id}">${user.name}</option>
                </c:forEach>
            </select>
        </dd>
    </dl>
    <button type="submit">Sign in</button>

</form>
</body>
</html>
