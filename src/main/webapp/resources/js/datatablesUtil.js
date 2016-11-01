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
        $("#meal-delete").show();
    });
    
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $("#meal-delete").hide();
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
                datatableApi.clear().rows.add(data).draw();
            }
        });
    } else {
        $.get(ajaxUrl, function (data) {
            datatableApi.clear().rows.add(data).draw();
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
