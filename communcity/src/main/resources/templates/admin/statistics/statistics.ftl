<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>维修任务统计</title>
    <#include "../common/header.ftl"/>
    <style>
        td {
            vertical-align: middle;
        }
    </style>
    <script type="text/javascript" src="/admin/js/echarts/echarts.min.js"></script>
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
                    <div class="col-sm-12 col-lg-12">
                        <div id="building" style="height: 400px"></div>
                    </div>

                    <div class="col-sm-12 col-lg-12">
                        <div id="emp_type" style="height: 400px"></div>
                    </div>

                    <div class="col-sm-12 col-lg-12">
                        <div id="emp" style="height: 400px"></div>
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

    // 基于准备好的dom，初始化echarts实例
    var buildingChart = echarts.init(document.getElementById('building'));

    $(document).ready(function () {
        $.ajax({
            url: '/statistics/count/building',
            async: true,
            cache: false,
            dataType:"json",
            success: function (data) {

                buildingChart.setOption({
                    xAxis: {
                        data: data.categories,

                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '楼栋数量',
                        data: data.data
                    }]
                });
                // 设置加载等待隐藏
                buildingChart.hideLoading();
            }
        });

        // 显示标题，图例和空的坐标轴
        buildingChart.setOption({
            color: ['#3398DB'],
            title: {
                text: '按照楼栋统计'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['任务数量']
            },
            xAxis: {
                data: [],
                name: "楼栋",
                axisTick: {
                    alignWithLabel: true
                }
            },
            yAxis: {
                name: "任务数量"
            },
            series: [{
                name: '数量',
                type: 'bar',
                barWidth: '60%',
                data: []
            }]
        });
    });

</script>

<script type="text/javascript">

    // 基于准备好的dom，初始化echarts实例
    var empTypeChart = echarts.init(document.getElementById('emp_type'));

    $(document).ready(function () {
        $.ajax({
            url: '/statistics/count/empType',
            async: true,
            cache: false,
            dataType:"json",
            success: function (data) {

                empTypeChart.setOption({
                    xAxis: {
                        data: data.categories
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '按照维修工种类统计',
                        data: data.data
                    }]
                });
                // 设置加载等待隐藏
                empTypeChart.hideLoading();
            }
        });


        // 显示标题，图例和空的坐标轴
        empTypeChart.setOption({
            color: ['#FF7F24'],
            title: {
                text: '按照维修工种类统计'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['任务数量']
            },
            xAxis: {
                name: "维修工种类",
                data: [],
                axisTick: {
                    alignWithLabel: true
                }

            },
            yAxis: {
                name: "任务数量"
            },
            series: [{
                name: '数量',
                type: 'bar',
                barWidth: '60%',
                data: []
            }]
        });
    });

</script>

<script type="text/javascript">

    // 基于准备好的dom，初始化echarts实例
    var empChart = echarts.init(document.getElementById('emp'));

    $(document).ready(function () {
        $.ajax({
            url: '/statistics/count/emp',
            async: true,
            cache: false,
            dataType:"json",
            success: function (data) {

                empChart.setOption({
                    xAxis: {
                        data: data.categories
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '按照维修工统计',
                        data: data.data
                    }]
                });
                // 设置加载等待隐藏
                empTypeChart.hideLoading();
            }
        });

        // 显示标题，图例和空的坐标轴
        empChart.setOption({
            color: ['#6A5ACD'],
            title: {
                text: '按照维修工统计'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['任务数量']
            },
            xAxis: {
                name: "维修工",
                data: [],
                axisTick: {
                    alignWithLabel: true
                }
            },
            yAxis: {
                name: "任务数量"
            },
            series: [{
                name: '数量',
                type: 'bar',
                barWidth: '60%',
                data: []
            }]
        });
    });

</script>
</body>
</html>