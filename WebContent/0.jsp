<%@page import="login.Teacher"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>1</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.3.0.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<style>
	.bluebar{
		background-color: #438EB9;
    	height: 80px;
	}
	#title{
		float:left;
		margin-left:30px;
		margin-top:26px;
		font-size:24px;
	}
	#welcome{
		float:right;
		margin-right:30px;
		margin-top:26px;
		font-size:20px;
	}
	.table-scoll{
		height: 400px; 
		overflow:scroll;
	}
	.mainBlock{
		width:100%;
		position:relative;
	}
	#guidedbar{
		width:200px;
		background-color:#F2F2F2;
		position:absolute;
	}
	.content{
		position:absolute;
		width:89%;
		left:11%;
	}
	td,th{
		text-align:center;
	}
	#title1{
		margin-top:40px;
		display:block;
		margin-left:40%;
		font-size:24px;
	}
	.check{
		margin-top:30px;
	}
	#condition{
		float:left;
		margin-left:170px;
	}
 	#checkall{ 
 		margin-right:190px;
		float:right;
 	} 
	.table{
		margin-top:1%;
		width:80%;
		margin-left:10%
	}
	#scoll{
		margin-top:64px;
		height:350px;
		overflow:scroll;
		overflow-x:hidden;
	}
	#pagination{
		margin-left:10%;
	}
</style>
<body>
<div class="bluebar">
<div id="title">校园网用户上网行为分析系统</div>
<div id="welcome">
<%Teacher teacher = (Teacher)request.getAttribute("resultTeacher");
  String welcome = "";
  if(teacher!=null){
	  welcome = teacher.getUsername()+"欢迎您！";
	  }
%>
<%= welcome %>
</div>
</div>
<div class="mainBlock">
<ul id="guidedbar" class="nav nav-pills nav-stacked">
  <li role="presentation"><a href="main.jsp">文件上传</a></li>
  <li role="presentation" class="active"><a href="1.jsp">信息查询</a></li>
  <li role="presentation"><a href="2.jsp">用户上网情况</a></li>
</ul>

<div class="content">
	<span id="title1">校园网用户上网信息</span>
	<div class="check">
	<div id="condition">
		<form class="form-inline" action="${pageContext.request.contextPath}/FindUserByPageServlet" method="post">
  			<div class="form-group">
    			<label for="num">学号</label>
    			<input type="text" class="form-control" id="num" name="num" placeholder="例如:10000001">
  			</div>
  			<div class="form-group">
    			<label for="startTime">起始日期</label>
    			<input type="text" class="form-control" id="startTime" name="start_time" placeholder="例如:2015-12-01">
  			</div>
  			<div class="form-group">
    			<label for="endTime">结束日期</label>
    			<input type="text" class="form-control" id="endTime" name="end_time" placeholder="例如:2016-01-01">
  			</div>
  			<button type="submit" class="btn btn-default">查询</button>
		</form>
	</div>
	<div id="checkall">
	<a class="btn btn-info" href="${pageContext.request.contextPath}/FindUserByPageServlet" role="button">查询所有信息</a>
	</div>
	</div>
	
	<div id="scoll">
	<table class="table table-hover table-bordered table-condensed">
		<tr>
    		<th>用户账号</th>
    		<th>登录时间</th>
    		<th>注销时间</th>
    		<th>使用时长(分钟)</th>
    		<th>使用流量(MB)</th>
    		<th>IP地址</th>
    		<th>国际上行(MB)</th>
    		<th>国际下行(MB)</th>
    		<th>国内上行(MB)</th>
    		<th>国内下行(MB)</th>
  		</tr>
	</table>
	</div>
	
</div>
</div>
</body>
</html>