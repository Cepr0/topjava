function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#editRow').on('hidden.bs.modal', function (e) {
        $('#detailsForm').find('input').val(null);
        $("#action-delete").show();
    });

    // In edit form on Delete click handle
    $('#action-delete').click(function () {
        var mealId = $('#id').val();
        $('#editRow').modal('hide');
        deleteRow(mealId);
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $("#action-delete").hide();
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable($("#filterForm"));
            successNoty('Deleted');
        }
    });
}

function updateTable(filterForm) {
    if (filterForm != null && filterForm.attr("id") == "filterForm") {
        $.ajax({
            type: "POST",
            url: ajaxUrl + "filter",
            data: filterForm != null ? $(filterForm).serialize() : "",
            success: function (data) {
                datatable.clear().rows.add(data).draw();
            }
        });
    } else {
        // $.ajax({
        //     type: "GET",
        //     url: ajaxUrl,
        //     success: function (data) {
        //         datatable.clear().rows.add(data).draw();
        //     }
        // })
        $.get(ajaxUrl, function (data) {
            datatable.clear().rows.add(data).draw();
        });
    }
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable($("#filterForm"));
            successNoty('Saved');
        }
    });
}

function enableDisable(id, enabled) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        data: "enabled="+enabled,
        success: function () {
            updateTable();
            successNoty('Enable toggled: ' + enabled);
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
