<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="footer">
    <hr>
    <!--<h4 class="bump text-uppercase"><a href="#">JavaRush</a>, <a href="#">Java Online Project</a></h4>-->
    <h4 class="bump"><fmt:message key="app.footer"/></h4>
    <%--<h4 class="bump">This application is the part of online Java courses</h4>--%>
    <%--<h4 class="bump"><a href="#">JavaRush</a> and <a href="#">Java Online Project</a></h4>--%>
    <h4 class="bump">&copy; <script>document.write(new Date().getFullYear())</script> <a href="https://github.com/Cepr0/topjava" target="_blank">Cepr0</a></h4>
</div>
