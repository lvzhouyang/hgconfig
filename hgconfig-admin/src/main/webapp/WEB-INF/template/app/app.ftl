<!DOCTYPE html>
<html>
<head>
  	<title>分布式配置管理平台</title>
  	<#import "/common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
	<!-- DataTables -->
  	<link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.css">
  	<!-- daterangepicker -->
  	<link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/daterangepicker/daterangepicker-bs3.css">
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && "off" == cookieMap["adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>应用管理</h1>
		</section>

		<!-- Main content -->
	    <section class="content">
			
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
                        <div class="box-header">
                            <h3 class="box-title">应用列表</h3>&nbsp;&nbsp;
                            <button class="btn btn-info btn-xs pull-left2 add" >+新增应用</button>
                        </div>
			            <div class="box-body">
			              	<table id="joblog_list" class="table table-bordered table-striped display" width="100%" >
				                <thead>
					            	<tr>
                                        <th name="appId" >ID</th>
                                        <th name="appName" >应用名</th>
                                        <th name="appDesc" >应用描述</th>
                                        <th name="createTime" >创建时间</th>
                                        <th name="updateTime" >更新时间</th>
                                        <th name="email" >负责人邮箱</th>
					                </tr>
				                </thead>
                                <tbody>
								<#if list?exists && list?size gt 0>
								<#list list as app>
									<tr>
                                        <td>${app.appId}</td>
                                        <td>${app.name}</td>
                                        <td>${app.description}</td>
                                        <td>${app.createTime}</td>
                                        <td>${app.updateTime}</td>
                                        <td>${app.emails}</td>
										<td>
                                            <button class="btn btn-warning btn-xs update" appId="${app.appId}" name="${app.name}"  description="${app.description}" emails="${app.emails}"  " >编辑</button>
                                            <button class="btn btn-danger btn-xs remove"  appId="${app.appId}" >删除</button>
										</td>
									</tr>
								</#list>
								</#if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	    </section>
	</div>

    <!-- 新增.模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" >新增应用</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form" >
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">应用名<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="name" placeholder="请输入“应用名”" maxlength="20" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">描述<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="description" placeholder="请输入“分组名”" maxlength="500" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">邮箱<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="emails" placeholder="请输入“负责人邮箱”" maxlength="30" ></div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary"  >保存</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- 更新.模态框 -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" >编辑分组</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form" >
                        <input type="hidden" name="appId">
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">应用名<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="name" placeholder="请输入“应用名”" maxlength="20" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">描述<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="description" placeholder="请输入“分组名”" maxlength="500" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">邮箱<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="emails" placeholder="请输入“负责人邮箱”" maxlength="30" ></div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary"  >保存</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
	
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>
<script src="${request.contextPath}/static/js/app.js"></script>
</body>
</html>
