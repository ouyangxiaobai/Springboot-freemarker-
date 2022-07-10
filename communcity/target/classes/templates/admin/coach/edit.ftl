<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, coach-scalable=no"/>
    <title>${siteName!""}|辅导员管理-编辑辅导员</title>
<#include "../common/header.ftl"/>

</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="index.html"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}"
                                          alt="${siteName!""}"/></a>
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
                            <div class="card-header"><h4>编辑辅导员</h4></div>
                            <div class="card-body">
                                <form action="add" id="coach-add-form" method="post" class="row">
                                    <input type="hidden" name="id" value="${coach.id}">

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">辅导员名</span>
                                        <input type="text" class="form-control required" id="name" name="name"
                                               value="${coach.name}" placeholder="辅导员名" tips="辅导员名"/>
                                    </div>


                                    <div class="input-group"
                                         style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                                        性别：
                                        <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                                            <input type="radio" name="sex" value="1" <#if coach.sex == 1>
                                                   checked </#if> >
                                            <span>男</span>
                                            <label class="lyear-radio radio-inline radio-primary">
                                                <input type="radio" name="sex" value="2" <#if coach.sex == 2>
                                                       checked </#if>>
                                                <span>女</span>
                                                <label class="lyear-radio radio-inline radio-primary">
                                                    <input type="radio" name="sex" value="0" <#if coach.sex == 0>
                                                           checked </#if>>
                                                    <span>未知</span>
                                                </label>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">手机号码</span>
                                        <input type="tel" class="form-control" id="phone" name="phone"
                                               value="${coach.phone}"/>
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
            if (!checkForm("coach-add-form")) {
                return;
            }
            var data = $("#coach-add-form").serialize();
            $.ajax({
                url: 'edit',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg('辅导员编辑成功!', function () {
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