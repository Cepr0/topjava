<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container">
    <div class="well">

        <%--User window caption--%>
        <div class="caption"><h3 class="text-info text-uppercase"><fmt:message key="users.title"/></h3></div>

        <!--Meals table-->
        <table class="table table-hover table-condensed" id="usersTable">
            <thead>
                <tr>
                    <th><fmt:message key="users.name"/></th>
                    <th><fmt:message key="users.email"/></th>
                    <th><fmt:message key="users.roles"/></th>
                    <th><fmt:message key="users.active"/></th>
                    <th><fmt:message key="users.registered"/></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Edit User Dialog-->
<div class="modal fade" id="editRow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"><fmt:message key="users.edit"/></h4></div>
                <form id="detailsForm" method="post">
                    <div class="modal-body">
                        <div class="row">
                            <fieldset>
                                <!--<legend class="text-info text-uppercase">-->
                                <!--<i class="fa fa-check-square-o"></i> Meal-->
                                <!--</legend>-->

                                <%--Meal ID--%>
                                <input type="text" hidden="hidden" id="id" name="id" title="id">

                                <%--User name--%>
                                <div class="form-group col-xs-12 col-sm-6">
                                    <label for="name"><fmt:message key="users.name"/></label>
                                    <div class="input-group date">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="users.name.placeholder"/>"/>
                                    </div>
                                </div>

                                <%--User email--%>
                                <div class="form-group col-xs-12 col-sm-6">
                                    <label for="email"><fmt:message key="users.email"/></label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                        <input id="email" name="email" class="form-control" type="email" placeholder="<fmt:message key="users.email.placeholder"/>">
                                    </div>
                                </div>

                                <%--User password--%>
                                <div class="form-group col-xs-12">
                                    <label for="password"><fmt:message key="users.password"/></label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" name="password" class="form-control" type="password" placeholder="<fmt:message key="users.password.placeholder"/>">
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" data-action="modal"><fmt:message key="common.apply"/></button>
                        <button id="action-delete" type="button" class="btn btn-default" data-toggle="modal"><fmt:message key="common.delete"/></button>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                    </div>
                </form>
        </div>
    </div>
</div>

<div>
    <%--<div class="jumbotron">--%>
    <%--<div class="container">--%>
    <%--<div class="shadow">--%>
    <%--<h3><fmt:message key="users.title"/></h3>--%>

    <%--<div class="view-box">--%>
    <%--<a class="btn btn-sm btn-info" onclick="add()"><fmt:message key="users.add"/></a>--%>

    <%--<table class="table table-striped display" id="datatable">--%>
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th><fmt:message key="users.name"/></th>--%>
    <%--<th><fmt:message key="users.email"/></th>--%>
    <%--<th><fmt:message key="users.roles"/></th>--%>
    <%--<th><fmt:message key="users.active"/></th>--%>
    <%--<th><fmt:message key="users.registered"/></th>--%>
    <%--<th></th>--%>
    <%--<th></th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<c:forEach items="${users}" var="user">--%>
    <%--<jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>--%>
    <%--<tr>--%>
    <%--<td><c:out value="${user.name}"/></td>--%>
    <%--<td><a href="mailto:${user.email}">${user.email}</a></td>--%>
    <%--<td>${user.roles}</td>--%>
    <%--<td>--%>
    <%--<input type="checkbox"--%>
    <%--<c:if test="${user.enabled}">checked</c:if> id="${user.id}"/>--%>
    <%--</td>--%>
    <%--<td><fmt:formatDate value="${user.registered}" pattern="dd-MMMM-yyyy"/></td>--%>
    <%--<td><a class="btn btn-xs btn-primary edit" id="${user.id}"><fmt:message key="common.update"/></a></td>--%>
    <%--<td><a class="btn btn-xs btn-danger delete" id="${user.id}"><fmt:message key="common.delete"/></a></td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</table>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
</div>

<jsp:include page="fragments/footer.jsp"/>


<div>
<%--<div class="modal fade" id="editRow">--%>
    <%--<div class="modal-dialog">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
                <%--<h2 class="modal-title"><fmt:message key="users.edit"/></h2>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--<form class="form-horizontal" method="post" id="detailsForm">--%>
                    <%--<input type="text" hidden="hidden" id="id" name="id">--%>

                    <%--<div class="form-group">--%>
                        <%--<label for="name" class="control-label col-xs-3">Name</label>--%>

                        <%--<div class="col-xs-9">--%>
                            <%--<input type="text" class="form-control" id="name" name="name" placeholder="Name">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="form-group">--%>
                        <%--<label for="email" class="control-label col-xs-3">Email</label>--%>

                        <%--<div class="col-xs-9">--%>
                            <%--<input type="email" class="form-control" id="email" name="email" placeholder="email">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="form-group">--%>
                        <%--<label for="password" class="control-label col-xs-3">Password</label>--%>

                        <%--<div class="col-xs-9">--%>
                            <%--<input type="password" class="form-control" id="password" name="password" placeholder="">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="form-group">--%>
                        <%--<div class="col-xs-offset-3 col-xs-9">--%>
                            <%--<button type="submit" class="btn btn-primary">Save</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</form>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
</div>

</body>

<jsp:include page="fragments/pageFooter.jsp"/>

<script type="text/javascript">

    var ajaxUrl = 'ajax/admin/users/';
    var datatableApi;

    $.fn.dataTable.ext.buttons.add = {
        className: 'btn btn-sm btn-primary',
        action: function ( e, dt, node, config ) {
            add();
        }
    };

    // $(document).ready(function () {
    $(function () {
        datatableApi = $('#usersTable').DataTable({
            paging: false,
            info: false,
            dom: 'Bft',
            buttons: [
                {extend: 'add', text: '<fmt:message key="users.add"/>'}
            ],

            "language": {
                search: "",
                searchPlaceholder: 'Search in table'
            },
            columns: [
                {data: "name", className: "user-name"},
                {data: "email", className: "user-email"},
                {data: "roles", className: "user-roles"},
                {data: "enabled", className: "user-enabled"},
                {data: "registered", className: "user-registered"}
            ], order: [[0, "asc"]],

            rowId: 'id',

            "rowCallback": function ( row, data, index ) {

                var enabler = 'onclick=enableDisable('+ data["id"] + ')';

                if ( data["enabled"] == true ) {
                    inputCheckBox = '<input type="checkbox" checked/>';
                } else {
                    $(row).addClass('text-muted');
                    inputCheckBox = '<input type="checkbox"/>';
                }

                //$("td.user-enabled", row).attr('onclick', enabler);
                $("td.user-enabled", row).html(inputCheckBox);

                var aEmail = '<a href="mailto:' + data["email"] + '">' + data["email"] + '</a>';
                $("td.user-email", row).html(aEmail);
            }
        });

        updateTable();

//        $("#usersTable tbody").on("change", "input[type=checkbox]", function (event) {
//            var id = $(this).parent().parent().attr("id");
//            enableDisable(id);
//            event.stopPropagation();
//        });

        $("#usersTable").on("click", "tr", function (event) {
            var id = $(this).attr("id");
            var cell = $(event.target).get(0);
            if (cell.tagName == "INPUT") { //$(event.target).hasClass("user-enabled")
                var enabled = cell.value = "on" ? false : true;
                enableDisable(id, enabled);
            } else {
                $('#id').val(id);
                $('#name').val($(this).find('td.user-name').text());
                $('#email').val($(this).find('td.user-email').text());
                $('#password').val($(this).find('td.user-password').text());

                $('#editRow').modal();
            }
        });

        makeEditable();
    });
</script>
</html>