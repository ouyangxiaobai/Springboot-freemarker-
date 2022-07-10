<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>添加学生</title>
<#include "../common/header.ftl"/>

</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="index.html"><img src="/admin/images/logo-sidebar.png" /></a>
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
              <div class="card-header"><h4>添加学生</h4></div>
              <div class="card-body">
                <form action="add" id="student-add-form" method="post" class="row">
                    <div class="form-group col-md-12">
                        <label>头像上传</label>
                        <div class="form-controls">
                            <ul class="list-inline clearfix lyear-uploads-pic">
                                <li class="col-xs-4 col-sm-3 col-md-2">
                                    <figure>
                                        <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                                    </figure>
                                </li>
                                <input type="hidden" name="headPic" id="headPic">
                                <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','headPic')">
                                <li class="col-xs-4 col-sm-3 col-md-2">
                                    <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">姓名</span>
                    <input type="tel" class="form-control  required" id="name" name="studentName" value=""  placeholder="请输入学生名称" tips="请填写学生名称"/>
                  </div>

                  <div class="input-group" style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                    性别：
                    <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                    <input type="radio" name="studentSex" value="0" checked="" >
                    <span>男</span>
                    <label class="lyear-radio radio-inline radio-primary">
                    <input type="radio" name="studentSex" value="1">
                    <span>女</span>
                    <label class="lyear-radio radio-inline radio-primary">
                    <input type="radio" name="studentSex" value="2">
                    <span>未知</span>
                  </label>
                  </div>

                    <div class="input-group m-b-10">
                        <span class="input-group-addon">邮箱地址</span>
                        <input type="email" class="form-control  required" id="email" name="email" value="" placeholder="请输入邮箱地址" tips="请输入正确的邮箱格式" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">联系方式</span>
                        <input type="tel" class="form-control  required" id="mobile" name="mobile" value=""  placeholder="请输入联系方式" tips="请填写联系方式"/>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">辅导员</span>
                        <select name="counsellorId.id" class="form-control" id="type">
                            <#if coachs ?exists>
                     <#list coachs as coach>
                         <option value="${coach.id}"
                         >${coach.name}</option>
                     </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">楼栋</span>

                        <select name="dormitory.building.id" class="form-control" id="building">
                         <#if  buildings ?exists>
                     <#list buildings as building>
                         <option value="${building.id}"
                         >${building.campus}${building.bno}号楼</option>
                     </#list>
                         </#if>
                        </select>

                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">宿舍号</span>

                    <select name="dormitory.id" class="form-control" id="dormitory">
                         <#--<#if  dormitorys ?exists>-->
                     <#--<#list dormitorys as dormitory>-->
                         <#--<option value="${dormitory.id}"-->
                         <#-->${dormitory.roomNo}</option>-->
                     <#--</#list>-->
                         <#--</#if>-->
                    </select>

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
	    var  email=$("#email").val();
        var ePattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if (!ePattern.test(email)){
            showErrorMsg("邮箱格式错误");
            return;
        }
        var  mobile=$("#mobile").val();
        var ePattern = /^1[0-9]{10}$/;
        if (!ePattern.test(mobile)){
            showErrorMsg("手机格式错误");
            return;
        }
        var  dormi=$("#dormitory").val();
        if (dormi==undefined||dormi==""){
            showErrorMsg("请选择宿舍");
            return;
        }

        if(!checkForm("student-add-form")){
			return;
		}
		var data = $("#student-add-form").serialize();
		$.ajax({
			url:'/stu/add',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('学生添加成功!',function(){
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


	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
    $("#building").change(function(){
        var opt=$("#building").val();
        $.ajax({
            url:'/stu/building',
            type:'POST',
            data:{
                id:opt
            },
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    $("#dormitory").text("");
                    // console.log(data);
                    var array=data.data;
                    for (var i = 0; i <array.length ; i++) {
                        $("#dormitory").append(  $("<option>").val(array[i].id).append(array[i].roomNo));
                    }


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