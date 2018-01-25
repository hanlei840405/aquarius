$(function () {
    $.get("department/tree", function (data) {
        $('#tree').jstree({
            'core': {
                'data': data
            }
        });
    });
});