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
                    <th><span class="glyphicon glyphicon-check"></span> </th>
                    <th><fmt:message key="users.name"/></th>
                    <th><fmt:message key="users.email"/></th>
                    <th><fmt:message key="users.registered"/></th>
                    <th><fmt:message key="users.dailyrate"/></th>
                    <th><fmt:message key="users.roles"/></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td>
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
                                <div class="form-group col-xs-12 col-sm-6">
                                    <label for="password"><fmt:message key="users.password"/></label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" name="password" class="form-control" type="password" placeholder="<fmt:message key="users.password.placeholder"/>">
                                    </div>
                                </div>

                                <%--User daily calories rate--%>
                                <div class="form-group col-xs-12 col-sm-6">
                                    <label for="dailyrate"><fmt:message key="users.dailyrate"/></label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-signal"></i></span>
                                        <input id="dailyrate" name="caloriesPerDay" class="form-control" type="number" min="5" max="5000" value="2000" placeholder="<fmt:message key="users.dailyrate.placeholder"/>">
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" data-action="modal">
                            <span class="glyphicon glyphicon-ok"></span>
                            <fmt:message key="common.apply"/>
                        </button>
                        <button id="action-delete" type="button" class="btn btn-default" data-toggle="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                            <fmt:message key="common.delete"/>
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                    </div>
                </form>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/pageFooter.jsp"/>

<script type="text/javascript">

    var ajaxUrl = 'ajax/admin/users/';
    var datatable;

    $.fn.dataTable.ext.buttons.add = {
        className: 'btn btn-sm btn-primary',
        action: function ( e, dt, node, config ) {
            add();
        }
    };

    // $(document).ready(function () {
    $(function () {
        datatable = $('#usersTable').DataTable({
            paging: false,
            info: false,
            dom: 'Bft',
            buttons: [
                {extend: 'add', text: '<span class="glyphicon glyphicon-user"></span> <fmt:message key="users.add"/>'}
            ],
            "language": {
                search: "",
                searchPlaceholder: 'Search in table'
            },
            columns: [
                {data: "enabled", className: "user-enabled"},
                {data: "name", className: "user-name"},
                {data: "email", className: "user-email"},
                {data: "registered", className: "user-registered"},
                {data: "caloriesPerDay", className: "user-dailyrate"},
                {data: "roles", className: "user-roles"}
            ],

            order: [[0, "desc"]],

            columnDefs: [{targets: [4, 5], visible: false}],

            rowId: 'id',

            "rowCallback": function ( row, data, index ) {

                if ( data["enabled"] == true ) {
                    inputCheckBox = '<input type="checkbox" checked/>';
                } else {
                    $(row).addClass('text-muted');
                    $(row).css("text-decoration", "line-through");
                    inputCheckBox = '<input type="checkbox"/>';
                }

                if ( data["roles"].indexOf("ROLE_ADMIN") != -1) {
                    $(row).addClass('text-danger');
                }

                $("td.user-enabled", row).html(inputCheckBox);

//                var aEmail = '<a href="mailto:' + data["email"] + '">' + data["email"] + '</a>';
//                $("td.user-email", row).html(aEmail);
            }
        });

        updateTable();

        $("#usersTable tbody").on("click", "tr", function (event) {

            var rowData = datatable.row(this).data();
            var id = rowData["id"];
            var cell = $(event.target).get(0);

            if (cell.tagName == "INPUT") { //$(event.target).hasClass("user-enabled")
                enableDisable(id, cell.checked);
            } else {
                $('#id').val(id);
                $('#name').val(rowData["name"]);
                $('#email').val(rowData["email"]);
                $('#password').val(rowData["password"]);
                $('#dailyrate').val(rowData["caloriesPerDay"]);

                $('#editRow').modal();
            }
        });

        makeEditable();
    });
</script>
</html>