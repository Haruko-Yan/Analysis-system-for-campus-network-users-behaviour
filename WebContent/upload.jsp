<%@page import="login.Teacher"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>文件上传</title>

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
		position:relative;
	}
	#guidedbar{
		width:11%;
		background-color:#F2F2F2;
		position:absolute;
	}
	.content{
		position:absolute;
		margin-left:11%;
	}
	#uploadForm{
		margin-left:100px;
		margin-top:50px;
	}
	#file{
		margin-top:20px;
	}
	#submit{
		margin-top:20px;
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
  <li role="presentation" class="active"><a href="upload.jsp">文件上传</a></li>
  <li role="presentation"><a href="query.jsp">信息查询</a></li>
  <li role="presentation"><a data-toggle="collapse" href="#collapseOne">用户上网情况</a></li>
  <div id="collapseOne" class="panel-collapse collapse ">
            <div class="panel-body">
            <ul style="background-color:#FFFFFF" class="nav nav-pills nav-stacked">
                <li role="presentation"><a href="preferenceOfTime.jsp">上网时间偏好</a></li>
  				<li role="presentation"><a href="durationAndData.jsp">上网时长及流量</a></li>
            </ul>
            </div>
   </div>
   <li role="presentation"><a href="cluster.jsp">聚类分析</a></li>
   <li role="presentation"><a href="analysisOfRelation.jsp">相关性分析</a></li>
</ul>
<div class="content">
<form id="uploadForm" action="/UploadTest/UploadServlet" method="post" enctype="multipart/form-data"> 
	<span style="font-size:20px">Excel上传</span>
	<div id="file">
		<input type="file" name="userUploadFile" style="float:left" onChange="ifIsEmpty()"><br>
		<span style="margin-left:10px;color:red;">${message}</span>
	</div>

	<button id="submit" type="submit" class="btn btn-default" disabled="disabled">上传</button>
</form>
<script type="text/javascript">
// 预览文件过后，判断用户是否选择了上传文件
	function ifIsEmpty(){
		var file = document.getElementById("file")
		if(file.value!=null||file.value!=""){
			document.getElementById("submit").removeAttribute("disabled")
		}
	}
	
</script>
</div>
</div>
</body>
</html>