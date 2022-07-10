# 项目名称

Springboot+freemarker的宿舍寝室维修上报管理系统源码+论文（包安装配置）

# 系统介绍
《基于Springboot+freemarker+Mysql实现的宿舍|寝室维修上报管理系统》该项目含有源码、论文等资料、配套开发软件、软件安装教程、项目发布教程等

使用技术：

前端使用技术：freemarker,HTML5,CSS3、JavaScript等

后台使用技术：Springboot、Spring、SpringMvc、SpringData等

数据库：Mysql数据库

项目功能介绍：

主要实现了学生自由上报宿舍损坏维修情况，系统自动分配维修师傅及管理员手动分配维修师傅的基本功能，

实现的功能有：

管理员：校园管理（楼栋管理、宿舍管理）、师生管理（学生管理、辅导员管理）、维修管理（维修工管理、维修进度管理）、

阅览室管理（座位生成等）、学生信用积分管理、座位预约统计管理等。

学生：登录、修改个人信息、上报宿舍维修信息、查看维修进度、完成维修进度等。

维修工：登录、查看分配给自己的维修任务、标记自己的维修进度等。

特色功能：系统自动分配维修工后会自动给维修工发邮件提醒，管理员也可以自由调配维修工

开发文档包含了需求分析、系统架构、流程图、E-R图、用例图、实体图、数据库设计等所有的论文要求点

系统功能完整，使用目前主流框架技术，适合作为毕业设计、课程设计、数据库大作业。

# 环境需要

1.运行环境：最好是java jdk 1.8，我们在这个平台上运行的。其他版本理论上也可以。\
2.IDE环境：IDEA，Eclipse,Myeclipse都可以。推荐IDEA;\
3.tomcat环境：Tomcat 7.x,8.x,9.x版本均可\
4.硬件环境：windows 7/8/10 1G内存以上；或者 Mac OS； \
5.数据库：MySql 5.7版本；\
6.是否Maven项目：否；

# 技术栈

1. 后端：Spring+SpringMVC+Mybatis\
2. 前端：JSP+CSS+JavaScript+jQuery

# 使用说明

1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并导入项目的sql文件；\
2. 使用IDEA/Eclipse/MyEclipse导入项目，Eclipse/MyEclipse导入时，若为maven项目请选择maven;\
若为maven项目，导入成功后请执行maven clean;maven install命令，然后运行；\
3. 将项目中springmvc-servlet.xml配置文件中的数据库配置改为自己的配置;\
4. 运行项目，在浏览器中输入http://localhost:8080/ 登录

# 高清视频演示
https://www.bilibili.com/video/BV1gN4y1u7oY/

​