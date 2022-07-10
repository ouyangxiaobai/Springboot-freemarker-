<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>维修管理</title>
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

                    <#include "../common/third-menu.ftl"/>

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
                        <th>学生姓名</th>
                        <th>学生学号</th>
                          <th>联系方式</th>
                          <th>任务状态</th>
                        <th>宿舍号</th>
                          <th>维修工分类</th>
                          <th>维修工</th>

                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as repairs>
                      <tr>
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${repairs.id}"  data-status="${repairs.status}"><span></span>
                          </label>
                        </td>
                        <td style="vertical-align:middle;">${repairs.student.studentName}</td>
                          <td style="vertical-align:middle;">${repairs.student.studentNo}</td>
                          <td style="vertical-align:middle;">${repairs.student.mobile}
                          <td style="vertical-align:middle;">
                        	<#if repairs.status == 0>
                                <font class="text-success">未完成</font>
                            <#else>
                        	<font class="text-warning">已完成</font>
                            </#if>
                          </td>
                          <td style="vertical-align:middle;">${repairs.dormitory.roomNo}</td>
                          <td style="vertical-align:middle;">${repairs.empType.getName()}</td>

                          <#if repairs.emp??>
                          <td style="vertical-align:middle;">${repairs.emp.name!""}</td>
                          <#else >
                            <td style="vertical-align:middle;"></td>
                          </#if>
                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="8">这里空空如也！</td></tr>
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
    var inputChecked=$("input[type='checkbox']:checked");
	if(inputChecked.length != 1){
		showWarningMsg('请选择一条数据进行编辑！');
		return;
	}
	if(inputChecked.attr("data-status")!=0){

        showWarningMsg('该任务已完成！');
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
				showSuccessMsg('撤销上报成功!',function(){
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