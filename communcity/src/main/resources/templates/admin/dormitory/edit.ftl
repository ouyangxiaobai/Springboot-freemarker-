<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|宿舍管理-编辑用户</title>
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
              <div class="card-header"><h4>编辑宿舍</h4></div>
              <div class="card-body">
                <form action="add" id="dormitory-add-form" method="post" class="row">
                  <input type="hidden" name="id" value="${dormitory.id}">
                  <div class="form-group col-md-12">
                      <div class="input-group m-b-10">
                          <span class="input-group-addon">所属楼栋</span>
                          <select name="building.id" class="form-control" id="building">
                    	<#list buils as buil>
                            <option value="${buil.id}" <#if dormitory.building.id == buil.id>selected</#if> >${buil.campus}${buil.bno}号楼</option>
                        </#list>
                          </select>
                      </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">房间名</span>
                    <input type="text" class="form-control required" id="roomNo" name="roomNo" value="${dormitory.roomNo}" placeholder="请输入房间号" tips="请填写房间号" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">备注</span>
                    <input type="text" class="form-control required" id="note" name="note" value="${dormitory.note}" placeholder="请输入备注" tips="请填写备注" />
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
		if(!checkForm("dormitory-add-form")){
			return;
		}
		var data = $("#dormitory-add-form").serialize();
		$.ajax({
			url:'/dormitory/edit',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('宿舍编辑成功!',function(){
                        window.location.href = '/dormitory/list';
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
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
});
</script>
</body>
</html>