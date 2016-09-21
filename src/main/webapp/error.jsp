<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TopJava Calories Manager - Error</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Calories Manager</h2>
<a href="https://github.com/JavaWebinar/topjava08" target="_blank">Java Enterprise (Topjava) 2016</a>
<br><br>
<h2><a href="meals">Back</a></h2>
<hr>
<h2>Error</h2>
<jsp:useBean id="errorMsg" scope="request" type="java.lang.String"/>
<p>${errorMsg}</p>
</body>
</html>
