$(function () {
    var load = function () {
        $.get("department/tree", function (data) {
            $('#tree').jstree({
                'core': {
                    'data': data
                }
            });
        });
        $.get("location/findAll", function (data) {
            var array = [];
            for (var i = 0; i < data.length; i++) {
                array.push({'id': data[i].code, 'text': data[i].name});
            }
            $('#location').select2({
                data: array
            })
        });
        $.get("department/findAll", function (data) {
            var array = [];
            for (var i = 0; i < data.length; i++) {
                array.push({'id': data[i].code, 'text': data[i].name});
            }
            $('#departmentCode').select2({
                data: array
            })
        });

        $('#departmentForm').bind('submit', function () {

        });
    };
    load();
});

function resetForm() {
    $('#departmentForm')[0].reset();
}

function submitForm() {
    $('#departmentForm').ajaxSubmit();
    $.post("department/save", { name: $('#name').val()}, function (data) {
        debugger;
        console.log(data);
    } );
    return false;
}

function deleteDepartment() {
    debugger;
    e.preventDefault();
    return false;
}

function reuseDepartment() {
    debugger;
    e.preventDefault();
    return false;
}