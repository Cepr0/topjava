<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--<div class="navbar navbar-default navbar-fixed-top" role="navigation">--%>
    <%--<div class="container">--%>
        <%--<a href="meals" class="navbar-brand"><fmt:message key="app.title"/></a>--%>

        <%--<div class="collapse navbar-collapse">--%>
            <%--<form class="navbar-form navbar-right">--%>
                <%--<a class="btn btn-info" role="button" href="users"><fmt:message key="users.title"/></a>--%>
                <%--<a class="btn btn-primary" role="button" href=""><fmt:message key="app.login"/></a>--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">

            <button type="button" class="dropdown-toggle navbar-toggle" data-toggle="dropdown" aria-expanded="false"
                    aria-haspopup="true">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right">
                <li><a href="meals"><fmt:message key="meals.title"/></a></li>
                <li><a href="users"><fmt:message key="users.title"/></a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Your profile</a></li>
                <li><a href=""><fmt:message key="app.login"/></a></li>
            </ul>

            <a href="" class="navbar-brand"><fmt:message key="app.title"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav">
                <!--<a class="navbar-brand" href="#">My meals tracker</a>-->
                <li><a href="meals"><fmt:message key="meals.title"/></a></li>
                <li><a href="users"><fmt:message key="users.title"/></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">user@topjava.ru <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Profile</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href=""><fmt:message key="app.login"/></a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>