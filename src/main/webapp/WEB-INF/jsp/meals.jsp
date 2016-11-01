<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container">
    <div class="well">
        <div class="caption">
            <!--<a class="btn btn-default btn-xs" role="button" data-toggle="collapse" href="#filter" aria-expanded="false"-->
            <!--aria-controls="filter">-->
            <!--Filter <span class="caret"></span>-->
            <!--</a>-->
            <button class="btn btn-default btn-xs text-uppercase" data-toggle="collapse" data-target="#filterForm">
                <fmt:message key="meals.toggleFilter"/>
                <span class="caret"></span>
            </button>
            <h3 class="text-info text-uppercase"><fmt:message key="meals.title"/></h3>

        </div>

        <form method="post" class="filter collapse" id="filterForm">
            <div class="row">

                <div class="form-group col-xs-6 col-sm-6 col-md-3">
                    <label for="startDate"><fmt:message key="meals.startDate"/></label>
                    <div class="input-group date">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                        <input type="text" class="form-control date-field" id="startDate" name="startDate"
                               placeholder="Specify from date"/>
                    </div>
                </div>

                <div class="form-group col-xs-6 col-sm-6 col-md-3">
                    <label for="endDate"><fmt:message key="meals.endDate"/></label>
                    <div class="input-group date">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                        <input type="text" class="form-control date-field" id="endDate" name="endDate"
                               placeholder="Specify to date"/>
                    </div>
                </div>

                <div class="form-group col-xs-6 col-sm-6 col-md-3">
                    <label for="startTime"><fmt:message key="meals.startTime"/></label>
                    <div class="input-group date">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        <input type="text" class="form-control time-field" id="startTime" name="startTime"
                               placeholder="Specify from time"/>
                    </div>
                </div>

                <div class="form-group col-xs-6 col-sm-6 col-md-3">
                    <label for="endTime"><fmt:message key="meals.endTime"/></label>
                    <div class="input-group date">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                        <input type="text" class="form-control time-field" id="endTime" name="endDate"
                               placeholder="Specify to time"/>
                    </div>
                </div>

            </div>

            <div class="actions">
                <button type="submit" name="mealFilterApply" class="btn btn-primary btn-xs">
                    <fmt:message key="meals.applyFilter"/>
                </button>
                <button id="mealFilterReset" name="mealFilterReset" class="btn btn-default btn-xs" data-toggle="collapse" data-target="#filterForm">
                    <fmt:message key="meals.resetFilter"/>
                </button>
            </div>
        </form>

        <a class="btn btn-sm btn-primary" onclick="add()"><fmt:message key="meals.add"/></a>

        <!--Meals table-->
        <table class="table table-hover" id="mealsTable">
            <!--<caption>Optional table caption.</caption>-->
            <thead>
            <tr>
                <th><fmt:message key="meals.dateTime"/></th>
                <th><fmt:message key="meals.description"/></th>
                <th><fmt:message key="meals.calories"/></th>
            </tr>
            </thead>
            <tbody>
            <%--<c:forEach items="${meals}" var="meal">--%>
                <%--<jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>--%>
                <%--<tr id="${meal.id}" class="${meal.exceed ? 'danger' : ''}">--%>
                    <%--<td class="meal-dateTime">${fn:formatDateTime(meal.dateTime)}</td>--%>
                    <%--<td class="meal-description">${meal.description}</td>--%>
                    <%--<td class="meal-calories">${meal.calories}</td>--%>
            <%--</c:forEach>--%>
                <tr>
                    <td class="meal-dateTime"></td>
                    <td class="meal-description"></td>
                    <td class="meal-calories"></td>
                </tr>
            </tbody>

        </table>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="editRow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel"><fmt:message key="meals.edit"/></h4></div>
                    <form id="detailsForm" method="post">
                        <div class="modal-body">
                            <%--Meal ID--%>
                            <input type="text" hidden="hidden" id="id" name="id" title="id">
                            <div class="row">
                                <fieldset>
                                    <!--<legend class="text-info text-uppercase">-->
                                    <!--<i class="fa fa-check-square-o"></i> Meal-->
                                    <!--</legend>-->
                                    <div class="form-group col-xs-12 col-sm-6">
                                        <label for="dateTime"><fmt:message key="meals.dateTime"/></label>
                                        <div class="input-group date">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                            <input type="text" class="form-control" id="dateTime" name="dateTime" placeholder="Specify a mealtime"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-12 col-sm-6">
                                        <label for="calories"><fmt:message key="meals.calories"/></label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-signal"></i></span>
                                            <input id="calories" name="calories" class="form-control" type="number" min="5" max="5000"
                                                   placeholder="Specify the amount of Meal calories, from 5 to 5000">
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-12">
                                        <label for="description"><fmt:message key="meals.description"/></label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-tags"></i></span>
                                            <input id="description" name="description" class="form-control" type="text" placeholder="Specify Meal description">
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary" data-action="modal"><fmt:message key="common.apply"/></button>
                            <button id="meal-delete" type="button" class="btn btn-default" data-toggle="modal"><fmt:message key="common.delete"/></button>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                        </div>
                    </form>
            </div>
        </div>
    </div>

</div>
<div>
<%--<section>--%>
<%--<h3><fmt:message key="meals.title"/></h3>--%>

<%--<form method="post" action="meals/filter">--%>
<%--<dl>--%>
<%--<dt><fmt:message key="meals.startDate"/>:</dt>--%>
<%--<dd><input type="date" name="startDate" value="${param.startDate}"></dd>--%>
<%--</dl>--%>
<%--<dl>--%>
<%--<dt><fmt:message key="meals.endDate"/>:</dt>--%>
<%--<dd><input type="date" name="endDate" value="${param.endDate}"></dd>--%>
<%--</dl>--%>
<%--<dl>--%>
<%--<dt><fmt:message key="meals.startTime"/>:</dt>--%>
<%--<dd><input type="time" name="startTime" value="${param.startTime}"></dd>--%>
<%--</dl>--%>
<%--<dl>--%>
<%--<dt><fmt:message key="meals.endTime"/>:</dt>--%>
<%--<dd><input type="time" name="endTime" value="${param.endTime}"></dd>--%>
<%--</dl>--%>
<%--<button type="submit"><fmt:message key="meals.filter"/></button>--%>
<%--</form>--%>
<%--<hr>--%>
<%--<a href="meals/create"><fmt:message key="meals.add"/></a>--%>

<%--<hr>--%>


<%--<table border="1" cellpadding="8" cellspacing="0">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th><fmt:message key="meals.dateTime"/></th>--%>
<%--<th><fmt:message key="meals.description"/></th>--%>
<%--<th><fmt:message key="meals.calories"/></th>--%>
<%--<th></th>--%>
<%--<th></th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<c:forEach items="${meals}" var="meal">--%>
<%--<jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>--%>
<%--<tr class="${meal.exceed ? 'exceeded' : 'normal'}">--%>
<%--<td>--%>
<%--&lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;--%>
<%--&lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;--%>
<%--${fn:formatDateTime(meal.dateTime)}--%>
<%--</td>--%>
<%--<td>${meal.description}</td>--%>
<%--<td>${meal.calories}</td>--%>
<%--<td><a href="meals/update?id=${meal.id}"><fmt:message key="common.update"/></a></td>--%>
<%--<td><a href="meals/delete?id=${meal.id}"><fmt:message key="common.delete"/></a></td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</table>--%>
<%--</section>--%>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/pageFooter.jsp"/>

<script type="text/javascript">
    var ajaxUrl = 'ajax/meals/';
    var datatableApi;

    function fillTableWithData() {
        $.ajax({
            type: "POST",
            url: ajaxUrl + "filter",
            data: $(this).serialize(),
            success: function (data) {
                datatableApi.clear().rows.add(data).draw();
            }
        });
    }

    $(function () {

        datatableApi = $('#mealsTable').DataTable({
            paging: false,
            info: false,
            columns: [
                {data: "dateTime", className: "meal-dateTime"},
                {data: "description", className: "meal-description"},
                {data: "calories", className: "meal-calories"}],
            order: [[0, "desc"]],
            rowId: 'id',
            "createdRow": function ( row, data, index ) {
                if ( data["exceed"] == true ) {
                    $(row).addClass('danger');
                }
            }
        });

        fillTableWithData();

        $.datetimepicker.setLocale('ru');

        $('.date-field').datetimepicker({
            timepicker: false,
            format: 'Y-m-d',
            lang: 'ru',
            dayOfWeekStart: 1
        });

        $('.time-field').datetimepicker({
            datepicker: false,
            format: 'H:i',
            lang: 'ru',
            step: 30
        });

        $('#dateTime').datetimepicker({
            format: 'Y-m-d H:i',
            lang: 'ru',
            dayOfWeekStart: 1,
            step: 30
        });

        // On table row click handle
        // Get the current row data and fire editRow modal dialog
        $("#mealsTable tbody").on("click", "tr", function() {
            $('#id').val($(this).attr("id"));
            $('#dateTime').val($(this).find('td.meal-dateTime').html());
            $('#description').val($(this).find('td.meal-description').html());
            $('#calories').val($(this).find('td.meal-calories').html());

            $('#editRow').modal();
        });

        $('#meal-delete').click(function () {
            var mealId = $('#id').val();
            $('#editRow').modal('hide');
            deleteRow(mealId);
        });

        $('#filterForm').submit(function () {
            $.ajax({
                type: "POST",
                url: ajaxUrl + 'filter',
                data: $(this).serialize(),
                success: function (data) {
                    datatableApi.clear().rows.add(data).draw();
                }
            });
            return false;
        });

        $("#mealFilterReset").click(function () {
            var filterForm = $('#filterForm');
            filterForm.find('input').val(null);
            fillTableWithData.call(this);
            filterForm.collapse('hide');
            return false;
        })

    });

    makeEditable();

</script>
</html>
