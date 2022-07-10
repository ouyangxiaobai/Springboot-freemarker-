<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>维修工维护-添加上报</title>
<#include "../common/header.ftl"/>

</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="index.html"><img src="/admin/images/logo-sidebar.png"/></a>
      </div>
      <div class="lyear-layout-sidebar-scroll"> 
        <#include "../common/left-menu.ftl"/>
      </div>
      
    </aside>
    <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>
    
     <!--页面主要内容-->
    <main class="lyear-layout-content">
      
      <div class="container-fluid">
        
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-header"><h4>维修上报</h4></div>
              <div class="card-body">
                <form action="add" id="repairs-add-form" method="post" class="row">

                  <div class="input-group m-b-10">
                    <span class="input-group-addon">学生姓名</span>
                    <input readonly=“readonly” type="text" class="form-control required" id="name" name="student.studentName" value="${bysj_user.username!""}" placeholder="请输入学生姓名" tips="请填写学生姓名" />
                  </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">学生学号</span>
                        <input type="text" class="form-control required" readonly=“readonly” id="name" name="student.studentNo" value="${admin_stu.studentNo!""}" placeholder="请输入学生学号" tips="请填写学生学号" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">联系方式</span>
                        <input type="text" class="form-control required"  readonly=“readonly” id="name" name="student.mobile" value="${bysj_user.mobile!""}" placeholder="请输入联系方式" tips="请填写联系方式" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">宿舍号</span>
                        <select name="dormitory.id" class="form-control" id="type"  readonly=“readonly”>
                         <option value="${admin_stu.dormitory.id}"
                         >${admin_stu.dormitory.roomNo}</option>
                        </select>
                    </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">所属类别</span>
                    <select name="empType" class="form-control" id="emptype">
                     <#list empTypes as empType>
                      <option value="${empType}" >${empType.getName()}</option>
                      </#list>
                    </select>
                  </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">备注</span>
                        <input type="text" class="form-control" id="name" name="remark" value=""  />
                    </div>

                  <div class="form-group col-md-12">
                    <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                  </div>
                </form>
       
              </div>
            </div>
          </div>
          
        </div>
        
      </div>
      
    </main>
    <!--End 页面主要内容-->
  </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("repairs-add-form")){
			return;
		}
		var data = $("#repairs-add-form").serialize()
		$.ajax({
			url:'add',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('上报成功!',function(){
						window.location.href = 'list';
					})
				}else{
					showErrorMsg(data.msg);
				}
			},
			error:function(data){
				alert('网络错误!');
			}
		});
	});

});

</script>
</body>
</html>