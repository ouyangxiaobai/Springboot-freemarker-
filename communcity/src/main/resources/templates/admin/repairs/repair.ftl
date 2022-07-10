<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>管理员分配</title>
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
                            <div class="card-header"><h4>修改上报</h4></div>
                            <div class="card-body">
                                <form action="add" id="repairs-add-form" method="post" class="row">
                                    <input type="hidden" name="id" value="${repairs.id}">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">学生姓名</span>
                                        <input type="text" class="form-control required" readonly=“readonly” id="name"
                                               name="student.studentName" value="${repairs.student.studentName}"
                                               placeholder="请输入学生姓名" tips="请填写学生姓名"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">学生学号</span>
                                        <input type="text" class="form-control required" readonly=“readonly” id="name"
                                               name="student.studentNo" value="${repairs.student.studentNo}"
                                               placeholder="请输入学生学号" tips="请填写学生学号"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">联系方式</span>
                                        <input type="text" class="form-control required" readonly=“readonly” id="name" name="student.mobile"
                                               value="${repairs.student.mobile}" placeholder="请输入联系方式" tips="请填写联系方式"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">宿舍号</span>
                                        <select name="dormitory.id" class="form-control" readonly=“readonly” id="type">
                                            <option value="${repairs.dormitory.id}"
                                            >${repairs.dormitory.roomNo}</option>
                                        </select>

                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">所属类别</span>

                                        <select name="empType" class="form-control" readonly=“readonly” id="emptype">
                                            <option value="${repairs.empType}">${repairs.empType.getName()}</option>
                                        </select>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">维修工分配</span>
                                        <select name="emp.id" class="form-control" id="emp">
                                                    <#list emps as emp>
                                                         <option value="${emp.getId()}"
                                                                 <#if repairs.getEmp()??>

                                                                     <#if repairs.getEmp().getId()=emp.getId()>
                                                                     selected="selected"
                                                                     </#if>

                                                                 </#if>

                                                         >${emp.getName()}</option>
                                                    </#list>

                                        </select>
                                    </div>


                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">备注</span>
                                        <input type="text" class="form-control" readonly=“readonly” id="name" name="remark"
                                               value="${repairs.remark!}"/>
                                    </div>



                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post"
                                                id="add-form-submit-btn">确 定
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="javascript:history.back(-1);return false;">返 回
                                        </button>
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
    $(document).ready(function () {
        //提交按钮监听事件
        $("#add-form-submit-btn").click(function () {
            if (!checkForm("repairs-add-form")) {
                return;
            }
            var data = $("#repairs-add-form").serialize();
            $.ajax({
                url: '/req/repairs',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg('管理员分配成功!', function () {
                            window.location.href = 'list';
                        })
                    } else {
                        showErrorMsg(data.msg);
                    }
                },
                error: function (data) {
                    alert('网络错误!');
                }
            });
        });
        //监听上传图片按钮
        $("#add-pic-btn").click(function () {
            $("#select-file").click();
        });
    });
</script>
</body>
</html>