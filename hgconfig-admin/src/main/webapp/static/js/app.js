$(function () {
    // remove
    $('.remove').on('click', function () {
        var appId = $(this).attr('appId');

        ComConfirm.show("确认删除应用?", function () {
            $.ajax({
                type: 'GET',
                url: base_url + '/app/remove?appId=' + appId,
                success: function (data) {
                    if((typeof data=='string')&&data.constructor==String){
                        data = JSON.parse(data);
                    }
                    if (data.code == 200) {
                        ComAlert.show(1, '删除成功', function () {
                            window.location.reload();
                        });
                    } else {
                        if (data.msg) {
                            ComAlert.show(2, data.msg);
                        } else {
                            ComAlert.show(2, '删除失败');
                        }
                    }
                },
            });
        });
    });

    // jquery.validate 自定义校验 “英文字母开头，只含有英文字母、数字和下划线”
    jQuery.validator.addMethod("myValid01", function (value, element) {
        var length = value.length;
        var valid = /^[a-z][a-zA-Z0-9-]*$/;
        return this.optional(element) || valid.test(value);
    }, "限制以小写字母开头，由小写字母、数字和中划线组成");

    $('.add').on('click', function () {
        $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    var addModalValidate = $("#addModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            name: {
                required: true,
                rangelength: [4, 100],
                myValid01: true
            },
            description: {
                required: true,
                rangelength: [4, 50]
            },
            email: {
                required: true,
                rangelength: [4, 50]
            }
        },
        messages: {
            name: {
                required: "请输入“应用名”",
                rangelength: "GroupName长度限制为4~100",
                myValid01: "限制以小写字母开头，由小写字母、数字和中划线组成"
            },
            description: {
                required: "请输入“应用描述”",
                rangelength: "长度限制为4~50"
            },
            email: {
                required: "请输入“负责人邮箱”",
                email: "请输入一个有效的Email地址",
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.parent('div').append(error);
        },
        submitHandler: function (form) {
            $.post(base_url + "/app/save", $("#addModal .form").serialize(), function (data, status) {
                if((typeof data=='string')&&data.constructor==String){
                    data = JSON.parse(data);
                }
                if (data.code == "200") {
                    $('#addModal').modal('hide');
                    setTimeout(function () {
                        ComAlert.show(1, "新增成功", function () {
                            window.location.reload();
                        });
                    }, 315);
                } else {
                    if (data.msg) {
                        ComAlert.show(2, data.msg);
                    } else {
                        ComAlert.show(2, "新增失败");
                    }
                }
            });
        }
    });
    $("#addModal").on('hide.bs.modal', function () {
        $("#addModal .form")[0].reset();
        addModalValidate.resetForm();
        $("#addModal .form .form-group").removeClass("has-error");
    });

    $('.update').on('click', function () {
        $("#updateModal .form input[name='name']").val($(this).attr("name"));
        $("#updateModal .form input[name='description']").val($(this).attr("description"));
        $("#updateModal .form input[name='appId']").val($(this).attr("appId"));
        $("#updateModal .form input[name='emails']").val($(this).attr("emails"));

        $('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    var updateModalValidate = $("#updateModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            name: {
                required: true,
                rangelength: [4, 100],
                myValid01: true
            },
            description: {
                required: true,
                rangelength: [4, 50]
            },
            email: {
                required: true,
                rangelength: [4, 50]
            }
        },
        messages: {
            name: {
                required: "请输入“应用名”",
                rangelength: "GroupName长度限制为4~100",
                myValid01: "限制以小写字母开头，由小写字母、数字和中划线组成"
            },
            description: {
                required: "请输入“应用描述”",
                rangelength: "长度限制为4~50"
            },
            email: {
                required: "请输入“负责人邮箱”",
                email: "请输入一个有效的Email地址",
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.parent('div').append(error);
        },
        submitHandler: function (form) {
            $.post(base_url + "/app/update", $("#updateModal .form").serialize(), function (data, status) {
                if((typeof data=='string')&&data.constructor==String){
                    data = JSON.parse(data);
                }
                if (data.code == "200") {
                    $('#addModal').modal('hide');
                    setTimeout(function () {
                        ComAlert.show(1, "更新成功", function () {
                            window.location.reload();
                        });
                    }, 315);
                } else {
                    if (data.msg) {
                        ComAlert.show(2, data.msg);
                    } else {
                        ComAlert.show(2, "更新失败");
                    }
                }
            });
        }
    });
    $("#updateModal").on('hide.bs.modal', function () {
        $("#updateModal .form")[0].reset();
        addModalValidate.resetForm();
        $("#updateModal .form .form-group").removeClass("has-error");
    });


});
