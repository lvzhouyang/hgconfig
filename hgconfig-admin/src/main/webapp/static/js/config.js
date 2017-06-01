$(function () {

    // init date tables
    var confTable = $("#conf_list").dataTable({
        "deferRender": true,
        "processing": true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/config/pageList",
            type: "post",
            data: function (d) {
                var obj = {};
                obj.envId = $('#env').val();
                obj.appId = $('#appName').val();
                obj.configKey = $('#configKey').val();
                obj.start = d.start;
                obj.length = d.length;
                return obj;
            }
        },
        "searching": false,
        "ordering": false,
        //"scrollX": true,	// X轴滚动条，取消自适应
        "columns": [
            {"data": 'configId', "visible": true},
            {"data": 'realConfigKey', "visible": true},
            {
                "data": 'value',
                "visible": true,
                "render": function (data, type, row) {
                    if (row.value == row.zkValue) {
                        var temp = (row.value.length > 20) ? row.value.substring(0, 20) + '...' : row.value;
                        return "<span title='" + row.value + "'>" + temp + "</span>";
                        ;
                    } else {
                        var tips = "Mysql:<hr>" + row.value + "<br><br>ZK:<hr>" + row.zkValue + "</span>";
                        var html = "<span style='color: red'>数据未同步: <a href='javascript:;' class='tecTips' tips='" + tips + "'>查看</a></span>";
                        return html;
                    }
                }
            },
            {"data": 'createTime', "visible": true},
            {"data": 'updateTime', "visible": true},

            {
                "data": '操作',
                "render": function (data, type, row) {
                    return function () {
                        // html
                        var html = '<p id="' + row.id + '" ' +
                            ' configId="' + row.configId + '" ' +
                            ' realConfigKey="' + row.realConfigKey + '" ' +
                            ' configKey="' + row.configKey + '" ' +
                            ' envId="' + row.envId + '" ' +
                            ' appId="' + row.appId + '" ' +
                            ' value="' + row.value + '" ' +
                            ' zkValue="' + row.zkValue + '" ' +
                            '>' +
                            '<textarea name="nodeValue" style="display:none;" >' + row.value + '</textarea>  ' +
                            '<button class="btn btn-warning btn-xs update" type="button">编辑</button>  ' +
                            '<button class="btn btn-danger btn-xs delete" type="button">删除</button>  ' +
                            '<button class="btn btn-success btn-xs history" type="button">修改记录</button>  ' +
                            '</p>';

                        return html;
                    };
                }
            }
        ],
        "language": {
            "sProcessing": "处理中...",
            "sLengthMenu": "每页 _MENU_ 条记录",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
            "sInfoEmpty": "无记录",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        }
    });

    $("#searchBtn").click(function () {
        confTable.fnDraw();
    });

    $("#conf_list").on('click', '.tecTips', function () {
        var tips = $(this).attr("tips");
        ComAlertTec.show(tips);
    });

    // 删除
    $("#conf_list").on('click', '.delete', function () {
        var configId = $(this).parent('p').attr("configId");
        var realConfigKey = $(this).parent('p').attr("realConfigKey");
        ComConfirm.show("确定要删除配置：" + realConfigKey, function () {
            $.get(
                base_url + "/config/remove?configId=" + configId,
                function (data, status) {
                    if ((typeof data == 'string') && data.constructor == String) {
                        data = JSON.parse(data);
                    }
                    if (data.code == "200") {
                        ComAlert.show(1, "删除成功", function () {
                            confTable.fnDraw();
                        });
                    } else {
                        ComAlert.show(2, data.msg);
                    }
                }
            );
        });
    });

    // jquery.validate 自定义校验 “英文字母开头，只含有英文字母、数字和下划线”
    jQuery.validator.addMethod("myValid01", function (value, element) {
        var length = value.length;
        var valid = /^[a-z][a-z0-9.]*$/;
        return this.optional(element) || valid.test(value);
    }, "KEY只能由小写字母、数字和.组成,须以小写字母开头");

    // 新增
    $("#add").click(function () {
        $('#addModal').modal('show');
    });
    var addModalValidate = $("#addModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            configKey: {
                required: true,
                minlength: 4,
                maxlength: 100,
                myValid01: true
            },
            value: {
                required: true
            }
        },
        messages: {
            configKey: {
                required: '请输入"Config Key".',
                minlength: '"KEY"不应低于4位',
                maxlength: '"KEY"不应超过100位'
            },
            value: {
                required: '请输入"value".',
            },
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
            $.post(base_url + "/config/add", $("#addModal .form").serialize(), function (data, status) {
                if ((typeof data == 'string') && data.constructor == String) {
                    data = JSON.parse(data);
                }
                if (data.code == "200") {
                    ComAlert.show(1, "新增配置成功", function () {
                        confTable.fnDraw();
                        $('#addModal').modal('hide');
                    });
                } else {
                    ComAlert.show(2, data.msg);
                }
            });
        }
    });
    $("#addModal").on('hide.bs.modal', function () {
        $("#addModal .form")[0].reset()
    });

    // 更新
    $("#conf_list").on('click', '.update', function () {

        $("#updateModal .form input[name='configId']").val($(this).parent('p').attr("configId"));
        $("#updateModal .form input[name='realConfigKey']").val($(this).parent('p').attr("realConfigKey"));
        $("#updateModal .form input[name='configKey']").val($(this).parent('p').attr("configKey"));
        $("#updateModal .form textarea[name='value']").val($(this).parent('p').attr("value"));
        $("#updateModal .form input[name='appId']").val($(this).parent('p').attr("appId"));
        $("#updateModal .form input[name='envId']").val($(this).parent('p').attr("envId"));

        $('#updateModal').modal('show');
    });

    $("#conf_list").on('click', '.history', function () {

        window.open('/config/history?configId=' + $(this).parent('p').attr("configId"));

    });
    var updateModalValidate = $("#updateModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            value: {
                required: true
            }
        },
        messages: {
            value: {
                required: '请输入"value".'
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
            $.post(base_url + "/config/update", $("#updateModal .form").serialize(), function (data, status) {
                if ((typeof data == 'string') && data.constructor == String) {
                    data = JSON.parse(data);
                }
                if (data.code == "200") {
                    ComAlert.show(1, "更新配置成功", function () {
                        confTable.fnDraw();
                        $('#updateModal').modal('hide');
                    });
                } else {
                    ComAlert.show(2, data.msg);
                }
            });
        }
    });
    $("#updateModal").on('hide.bs.modal', function () {
        $("#updateModal .form")[0].reset()
    });

});