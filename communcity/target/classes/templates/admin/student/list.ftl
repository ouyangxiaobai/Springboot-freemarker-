<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>学生管理</title>
<#include "../common/header.ftl"/>
<style>
td{
	vertical-align:middle;
}
</style>
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
              <div class="card-toolbar clearfix">
                <form class="pull-right search-bar" method="get" action="list" student="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                      <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                      学生名称 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">学生名称</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${name!""}" name="studentName" placeholder="请输入学生名称">
                  	<span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                  </div>
                </form>
                    <#include "../common/third-menu.ftl"/>
                <#--<div class="toolbar-btn-action">-->
                  <#--<a class="btn btn-primary m-r-5" href="/student/add"><i class="mdi mdi-account-plus"></i>添加</a>-->
                  <#--<a class="btn btn-primary m-r-5" href="javascript:void(0)" onClick="edit('/student/edit')"><i class="mdi mdi-account-edit"></i>编辑</a>-->
                  <#--<a class="btn btn-primary m-r-5" href="javascript:void(0)" onClick="del('/student/delete')"><i class="mdi mdi-account-remove"></i>删除</a>-->
                <#--</div>-->
              </div>
              <div class="card-body">
                
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <thead>
                      <tr>
                        <th>
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" id="check-all"><span></span>
                          </label>
                        </th>
                          <th>学生头像</th>
                        <th>学生名称</th>
                        <th>学生性别</th>
                          <th>学生学号</th>
                          <th>学生邮箱</th>
                          <th>联系方式</th>
                          <th>辅导员</th>
                          <th>楼栋号</th>
                        <th>宿舍号</th>

                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as student>
                      <tr>
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${student.id}"><span></span>
                          </label>
                        </td>

                          <td style="vertical-align:middle;">
                        	<#if student.headPic??>
                        		<#if student.headPic?length gt 0>
                        		<img src="/photo/view?filename=${student.headPic}" width="60px" height="60px">
                                <#else>
                        		<img src="/admin/images/default-head.jpg" width="60px" height="60px">
                                </#if>
                            </#if>
                          </td>
                        <td style="vertical-align:middle;">${student.studentName}</td>
<#--                        <td style="vertical-align:middle;">-->
<#--                        	<#if student.status == 1>-->
<#--                        	<font class="text-success">正常</font>-->
<#--                        	<#else>-->
<#--                        	<font class="text-warning">冻结</font>-->
<#--                        	</#if>-->
<#--                        </td>-->
                          <td style="vertical-align:middle;">
                                  <#if student.studentSex == 0>
                                      男
                                 </#if>
                              	<#if student.studentSex == 1>
                                    女
                                </#if>
                          </td>
                          <td align="center">${student.studentNo!""}</td>
                          <td align="center">${student.email!""}</td>
                          <td align="center">${student.mobile!""}</td>
                          <td align="center">${student.counsellorId.getName()!""}</td>
                          <#if student.dormitory?? >
                          <td align="center">${student.dormitory.building.campus!""}${student.dormitory.building.bno!""}号楼</td>
                           <td align="center">${student.dormitory.roomNo!""}</td>
                          <#else >
                           <td align="center"></td>
                           <td align="center"></td>
                          </#if>


                        <#--<td style="vertical-align:middle;"><font class="text-success">${student.birthday} </font></td>-->
                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="9">这里空空如也！</td></tr>
					</#if>                      
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=${pageBean.totalPage}">»</a></li>
                  </#if>
                  <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                </ul>
                </#if>
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
	
});
function del(url){
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择一条数据进行删除！');
		return;
	}
	var id = $("input[type='checkbox']:checked").val();
	$.confirm({
        title: '确定删除？',
        content: '删除后数据不可恢复，请慎重！',
        buttons: {
            confirm: {
                text: '确认',
                action: function(){
                    deleteReq(id,url);
                }
            },
            cancel: {
                text: '关闭',
                action: function(){
                    
                }
            }
        }
    });
}
//打开编辑页面
function edit(url){
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择一条数据进行编辑！');
		return;
	}
	window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
}
//调用删除方法
function deleteReq(id,url){
	$.ajax({
		url:url,
		type:'POST',
		data:{id:id},
		dataType:'json',
		success:function(data){
			if(data.code == 0){
				showSuccessMsg('学生删除成功!',function(){
					$("input[type='checkbox']:checked").parents("tr").remove();
				})
			}else{
				showErrorMsg(data.msg);
			}
		},
		error:function(data){
			alert('网络错误!');
		}
	});
}
</script>
</body>
</html>