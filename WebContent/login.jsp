<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>登录</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.3.0.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>
    <style>
    	.profile{
    background-color: #FFFFFF;
    border-radius: 20px;
    width: 400px;
    height: 500px;
    margin: auto;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    border:1px solid #BFBFBF;
    box-shadow:0px 0px 50px 5px #aaa;
}
	#title{
		margin-left:50px;
		margin-top:50px;
	}
	#image{
		margin-left:120px;
		margin-top:10px;
	}
	.form-horizontal{
		margin-left:50px;
	}
	#error{
		margin-left:130px;
		color:red;
	}
    </style>
  </head>
  <body style="background-color: #56baed;">
  <div class="profile">
  	<div id="title"><h4><b>欢迎登录校园网用户上网行为分析系统</b></h4></div>
  	<img id="image" src="image/timg.jpg" alt="封面图片" height="150" width="150"/>
    <form action="LoginServlet" class="form-horizontal" method="post">
  
  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">账号</label>
    <div class="col-sm-10">
      <input type="text" name="username" class="form-control" id="inputEmail3" style="width:200px" placeholder="账号">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
    <div class="col-sm-10">
      <input type="password" name="password" class="form-control" id="inputPassword3" style="width:200px" placeholder="密码">
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <input type="submit"  class="btn btn-default" value="登录">
    </div>
  </div>
    
</form>
	<div id="error"><%= request.getAttribute("error")==null? "": request.getAttribute("error") %></div>
    </div>
  </body>
</html>