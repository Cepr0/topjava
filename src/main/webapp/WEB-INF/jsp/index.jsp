<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<%--<div class="jumbotron">--%>
    <%--<div class="container">--%>
        <%--<p/>--%>

        <%--<form method="post" action="users">--%>
            <%--<fmt:message key="app.login"/>: <select name="userId">--%>
            <%--<option value="100000" selected>User</option>--%>
            <%--<option value="100001">Admin</option>--%>
        <%--</select>--%>
            <%--<button type="submit"><fmt:message key="common.select"/></button>--%>
        <%--</form>--%>
        <%--<ul>--%>
            <%--<li><a href="users"><fmt:message key="users.title"/></a></li>--%>
            <%--<li><a href="meals"><fmt:message key="meals.title"/></a></li>--%>
        <%--</ul>--%>
    <%--</div>--%>
<%--</div>--%>

<div class="container">
    <div class="jumbotron well">
        <h2 class="text-info text-uppercase"><fmt:message key="app.title"/></h2>
        <hr>
        <p class="text-info">Your personal meals tracker that helps you track and control your per day meal intake</p>

        <form class="form-horizontal" method="post" action="users">

            <div class="form-group">
                <label class="col-sm-2 control-label" for="userId"><fmt:message key="app.login"/></label>
                <div class="col-sm-8">
                    <select class="form-control" id="userId" name="userId" placeholder=".input-sm">
                        <option value="100000" selected>User</option>
                        <option value="100001">Admin</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-8">
                    <input type="password" class="form-control" id="password" placeholder="Password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Remember me
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-primary" type="submit"><fmt:message key="common.select"/></button>
                    <a class="btn" href="#" role="button">Sign up</a>
                </div>
            </div>

        </form>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/pageFooter.jsp"/>
</html>
