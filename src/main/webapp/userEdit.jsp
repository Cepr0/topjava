<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <a onclick="window.history.back()" href="#">Back</a>
    <h3>${param.action == 'create' ? 'Create user' : 'Edit user'}</h3>
    <hr>
    <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User" scope="request"/>
    <form method="post" action="users">
        <input type="hidden" name="form" value="edit">
        <input type="hidden" name="id" value="${user.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${user.name}" size=30 name="name"></dd>
            <dt>Password:</dt>
            <dd><input type="password" value="${user.password}" size=20 name="password"></dd>
            <dt>Email:</dt>
            <dd><input type="email" value="${user.email}" size=20 name="email"></dd>
            <dt>Calories:</dt>
            <dd><input type="number" value="${user.caloriesPerDay}" name="caloriesPerDay"></dd>
        </dl>
        <button type="submit" name="state" value="ok">Save</button>
        <button onclick="window.history.back()" name="state" value="cancel">Cancel</button>
    </form>
</section>
</body>
</html>
