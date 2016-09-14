<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meal list</title>
    <style>
        body {
            font-family: sans-serif;
            padding: 1em;
        }

        table {
            border-spacing: 0;
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

        td a {
            text-decoration: none;
        }

        td.datetime {
            min-width: 8em;
        }

        td.description {
            min-width: 12em;
            max-width: 16em;
        }

        tr.meal-row {
            cursor: pointer;
        }

        tr.exceeded {
            color: red;
        }

        tr.normal {
            color: green;
        }

        tr.meal-row:hover {
            background-color: #f5f5f5;
        }

        /*Form part start*/
        form {
            margin: 0.5em 0;
            padding: 0;
            /*float: left;*/
            max-width: 31em;
            min-width: 31em;
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
            margin: 0.6em 1em 0.5em 0;
            width: 14%;
            float: left;
            clear: left;
            text-align: right;
        }

        form dd {
            margin: 0;
            padding: 0.5em 0;
            width: 80%;
            float: left;
            line-height: 1.5;
        }

        form input {
            font-family: inherit;
            font-size: inherit;
            border: 1px solid #e8edff;
            color: inherit;
            margin-left: 0.5em;
        }

        form.meal-form #description {
            min-width: 100%;
        }

        form.meal-form .form-actions-block {
            margin: 8em 0 0 5.5em;
        }

        form .submit-button {
            clear: left;
            font-size: inherit;
            font-family: inherit;
        }

        form .cancel-button {
            margin-left: 0.5em;
        }

        form .delete-button {
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

        /*Form part end*/

        div.actions-block {
            padding: 0 0 1em 1em;
            min-width: 20em;
        }

        .page-title {
            font-weight: bold;
            text-transform: uppercase;
            color: #039;
        }

        a:link, a:visited {
            color: inherit;
        }

        span.action-create {
            color: darkred;
            font-weight: bold;
        }

        span.action-populate {
            color: #404040;
        }

    </style>
</head>
<body>
<jsp:useBean id="defaultLocale" scope="request" type="java.lang.String"/>
<fmt:setLocale value="${defaultLocale}" />

<h2 class="nav-menu-block"><a href="./">Home</a></h2>
<h2 class="page-title">Meal list</h2>
<div class="actions-block">
    <span class="action-create"><a class="action-create" href="meals?action=create">Add new meal</a></span> |
    <span class="action-populate"><a class="actions-populate"
                                     href="meals?action=populate">Populate new data</a></span>
</div>
<jsp:useBean id="isEditFormVisible" scope="request" type="java.lang.String"/>

<c:catch var="curMealException">
    <jsp:useBean id="curMeal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
</c:catch>

<c:if test="${curMeal != null}">

    <div class="editForm" style="display: ${isEditFormVisible};">
        <form class="meal-form" method="post" action="meals">
            <fieldset>
                <legend>Edit meal</legend>

                <div class="form-fields-block">
                    <c:if test="${empty curMeal.id}"><input type="hidden" name="id" value="${null}"></c:if>
                    <c:if test="${not empty curMeal.id}"><input type="hidden" name="id" value="${curMeal.id}"></c:if>

                    <dl>
                        <dt><label for="dateTime">Date&Time</label></dt>
                        <dd><input id="dateTime" type="datetime-local" name="dateTime" value="${curMeal.dateTime}"></dd>
                        <dt><label for="description">Description</label></dt>
                        <dd><input class="description" id="description" type="text" name="description" value="${curMeal.description}"></dd>
                        <dt><label for="calories">Calories</label></dt>
                        <dd><input id="calories" type="number" name="calories" value="${curMeal.calories}"></dd>
                    </dl>
                </div>
                <div class="form-actions-block">
                    <button class="submit-button" type="submit">Save</button>
                    <c:if test="${param.action != 'create'}">
                        <a class="delete-button" href="meals?action=delete&id=${curMeal.id}">Delete</a>
                    </c:if>
                    <a class="cancel-button" href="meals">Cancel</a>
                </div>
            </fieldset>
        </form>
    </div>

</c:if>

<table class="meal-table">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
    </tr>
    </thead>
    <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr valign="top" class="meal-row ${meal.exceed ? "exceeded" : "normal"}">
            <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>
            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="formatedDate"/>
            <td onclick="location.href='meals?action=update&id=${meal.id}'" class="datetime">${formatedDate}</td>
            <td onclick="location.href='meals?action=update&id=${meal.id}'" class="description">${meal.description}</td>
            <fmt:formatNumber value="${meal.calories}" var="formatedCalories" type="number"/>
            <td onclick="location.href='meals?action=update&id=${meal.id}'" class="calories">${formatedCalories}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
