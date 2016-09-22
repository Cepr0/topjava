<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TopJava Calories Manager - Info</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Calories Manager</h2>
<a href="https://github.com/JavaWebinar/topjava08" target="_blank">Java Enterprise (Topjava) 2016</a>
<br><br>
<a onclick="window.history.back()" href="#">Back</a>
<hr>
<jsp:useBean id="message" scope="request" type="java.lang.String"/>
<p>${message}</p>
</body>
</html>
