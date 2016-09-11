<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .exceeded {
            color: red;
        }

        .normal {
            color: green;
        }

        body {
            font-family: sans-serif;
            padding: 1em;
        }

        th {
            font-weight: bold;
            text-transform: uppercase;
            text-align: left;
            color: #039;
            padding: 10px 15px;
        }

        td {
            border-top: 1px solid #e8edff;
            padding: 10px 15px;
        }

        /*Form part*/
        form {
            margin: 0.5em 0;
            padding: 0;
            /*float: left;*/
            width: 600px;
            color: #404040;
        }

        form dl {
            margin: 0;
            padding: 0;
            float: left;
            width: 100%;
        }

        form dt {
            padding: 0;
            margin: 0.7em 1em 0.5em 0;
            width: 14%;
            float: left;
            clear: left;
            text-align: right;
        }

        form dd {
            margin: 0;
            padding: 0.5em 0;
            width: 70%;
            float: left;
            line-height: 1.5;
        }

        form input {
            font-family: inherit;
            font-size: inherit;
            border: 1px solid #e8edff;
            color: inherit;
        }

        form .submit-button {
            clear: left;
            /*padding: 0.5em 0 1em 1.2em;*/
            margin-left: 6em;
            margin-top: 1em;
            font-size: inherit;
            font-family: inherit;
        }

        form .cancel-button {
            margin-left: 0.5em;
        }

        form .submit-button input {
            margin-right: 0.5em;
        }

        form label {
            cursor: pointer;
        }

        form fieldset {
            clear: left;
            border: 1px solid #e8edff;
            padding: 0.5em;
            margin: 1em 0;
        }

        form fieldset legend {
            color: #039;
            font-weight: bold;
            text-transform: uppercase;

        }

        form fieldset dt {
            /*margin-left: -0.25em;*/
        }
    </style>
</head>
<body>
<h2><a href="./">Home</a></h2>
<h2>Meal list</h2>
<br/>
<a href="meals?action=populate">Populate new data</a>
<br/>
<jsp:useBean id="isEditFormVisible" scope="request" type="java.lang.String"/>
<div class="editForm" style="display: ${isEditFormVisible};">
    <form method="post" action="meals">
        <fieldset>
            <legend>Edit meal</legend>
            <jsp:useBean id="curMeal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
            <input type="hidden" name="id" value="${curMeal.id}">
            <dl>
                <dt><label for="dateTime">Date&Time</label></dt>
                <%--<fmt:parseDate value="${curMeal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedCurDate"/>--%>
                <%--<fmt:formatDate value="${parsedCurDate}" pattern="yyyy-MM-dd HH:mm" var="formatedCurDate"/>--%>
                <dd><input id="dateTime" type="datetime-local" name="dateTime" value="${curMeal.dateTime}" size="20"></dd>
                <dt><label for="description">Description</label></dt>
                <dd><input id="description" type="text" name="description" value="${curMeal.description}" size="50">
                </dd>
                <dt><label for="calories">Calories</label></dt>
                <dd><input id="calories" type="number" name="calories" value="${curMeal.calories}"></dd>
            </dl>
            <button class="submit-button" type="submit">Save</button>
            <a class="cancel-button" href="meals">Cancel</a>
        </fieldset>
    </form>
</div>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Actions</th>
        <th></th>
    </tr>
    </thead>
    <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? "exceeded" : "normal"}">
            <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>
            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="formatedDate"/>
            <td>${formatedDate}</td>
                <%--<td>${TimeUtil.timeToString(meal.dateTime)}</td>--%>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
