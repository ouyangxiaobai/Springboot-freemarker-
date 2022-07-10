<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>维修管理-${title!""}</title>
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
                                            <th>名称</th>
                                            <th>联系方式</th>
                                            <th>地址</th>
                                            <th>状态</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                      <#list repairs as rep>
                      <tr>
                          <td style="vertical-align:middle;">
                              <label class="lyear-checkbox checkbox-primary">
                                  <input type="checkbox" name="id" value="${rep.id!""}"><span></span>
                              </label>
                          </td>
                          <#if rep.emp??>
                                 <td style="vertical-align:middle;">${rep.emp.name!""}</td>
                          <#else>
                                 <td style="vertical-align:middle;"></td>
                          </#if>
                          <td style="vertical-align:middle;">${rep.student.mobile!""}</td>
                          <td style="vertical-align:middle;">${rep.dormitory.building.campus!""}${rep.dormitory.building.bno!""}号楼${rep.dormitory.roomNo!""}宿舍</td>
                          <td style="vertical-align:middle;" id="statuss">
                             <#if rep.status==0 >
                                 <font class="text-success">未完成</font>
                             <#elseif rep.status==1>
                              <font class="text-info">完成</font>
                             </#if>
                          </td>
                      </tr>
                      </#list>
                                        </tbody>
                                    </table>
                                </div>
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

    //维修任务改变
    function updateWork(url){

        if($("input[type='checkbox']:checked").length != 1){
            showWarningMsg('请选择一条任务！');
            return;
        }
        var id = $("input[type='checkbox']:checked").val();
        $.confirm({
            title: '确定完成？',
            content: '请保证任务完成质量！',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function(){
                        openmains(id,url);
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
    //调用维修完成方法
    function openmains(id,url){
        $.ajax({
            url:url,
            type:'POST',
            data:{
                id:id
            },
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    showSuccessMsg('任务完成!',function(){
                        window.location.reload();
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