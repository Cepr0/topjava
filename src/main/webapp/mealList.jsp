<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .normal {
            color: green;
        }
        .exceeded {
            color: red;
        }
        /*dl {*/
            /*margin: 8px 0;*/
            /*padding: 0;*/
        /*}*/
        /*dt {*/
            /*display: inline-block;*/
            /*!*width: 30px;*!*/
        /*}*/
        /*dd {*/
            /*display: inline-block;*/
            /*margin-left: 10px;*/
            /*margin-right: 15px;*/
            /*vertical-align: middle;*/
        /*}*/
    </style>
</head>
<body>
<section>
    <h2><a href=".">Home</a></h2>
    <h3>Meal list</h3>
    <form method="post" action="meals">
        <input type="hidden" name="form" value="filter">
        <dl>
            <dt>From date:</dt>
            <c:set var="fromDate" scope="session" value="${fromDate}"/>
            <dd><input type="date" value="${fromDate}" name="fromDate"></dd>
            <dt>To date:</dt>
            <c:set var="toDate" scope="session" value="${toDate}"/>
            <dd><input type="date" value="${toDate}" name="toDate"></dd>
        </dl>
        <dl>
            <dt>From  time:</dt>
            <c:set var="fromTime" scope="session" value="${fromTime}"/>
            <dd><input type="time" value="${fromTime}" name="fromTime"></dd>
            <dt>To time:</dt>
            <c:set var="toTime" scope="session" value="${toTime}"/>
            <dd><input type="time" value="${toTime}" name="toTime"></dd>
        </dl>
        <button type="submit" name="state" value="ok">Filter</button>
        <button type="submit" name="state" value="reset">Reset</button>
    </form>
    <hr>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>