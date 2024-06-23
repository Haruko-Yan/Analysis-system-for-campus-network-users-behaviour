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
    <title>上网时间偏好</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.3.0.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>
    <!-- 引入 ECharts 文件 -->
    <script src="echarts.common.min.js"></script>
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
	#condition{
		margin-top:70px;
		margin-left:170px;
	}
	#barChart{
		width: 1200px;
		height:600px;
		margin-top:30px;
		margin-left:40px;
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
  <li role="presentation"><a href="upload.jsp">文件上传</a></li>
  <li role="presentation"><a href="query.jsp">信息查询</a></li>
  <li role="presentation"><a data-toggle="collapse" href="#collapseOne">用户上网情况</a></li>
  <div id="collapseOne" class="panel-collapse collapse in">
            <div class="panel-body">
            <ul style="background-color:#FFFFFF" class="nav nav-pills nav-stacked">
                <li role="presentation"><a href="preferenceOfTime.jsp">上网时间偏好</a></li>
  				<li role="presentation" class="active"><a href="durationAndData.jsp">上网时长及流量</a></li>
            </ul>
            </div>
   </div>
   <li role="presentation"><a href="cluster.jsp">聚类分析</a></li>
   <li role="presentation"><a href="analysisOfRelation.jsp">相关性分析</a></li>
</ul>

<div class="content">
	<div id="condition">
		<form class="form-inline" action="${pageContext.request.contextPath}/durationAndDataServlet" method="post">
  			<div class="form-group">
    			<label for="num">学号</label>
    			<input type="text" class="form-control" id="num" name="num" placeholder="例如:10000001">
  			</div>
  			<button type="submit" class="btn btn-default">查询</button>
		</form>
	</div>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="barChart" style="width: 1200px;height:600px;"></div>
    <script type="text/javascript">

    var myChart = echarts.init(document.getElementById('barChart'));
    option = {
    	    tooltip: {
    	        trigger: 'axis',
    	        axisPointer: {
    	            type: 'cross',
    	            crossStyle: {
    	                color: '#999'
    	            }
    	        }
    	    },
    	    toolbox: {
    	        feature: {
    	            dataView: {show: true, readOnly: false},
    	            magicType: {show: true, type: ['line', 'bar']},
    	            restore: {show: true},
    	            saveAsImage: {show: true}
    	        }
    	    },
    	    legend: {
    	        data:['平均每日上网时长','平均每日使用流量']
    	    },
    	    xAxis: [
    	        {
    	            type: 'category',
    	            data: ['个人','总体'],
    	            axisPointer: {
    	                type: 'shadow'
    	            }
    	        }
    	    ],
    	    yAxis: [
    	        {
    	            type: 'value',
    	            name: '时长',
    	            min: 0,
    	            max: 1000,
    	            interval: 200,
    	            axisLabel: {
    	                formatter: '{value} min'
    	            }
    	        },
    	        {
    	            type: 'value',
    	            name: '流量',
    	            min: 0,
    	            max: 2000,
    	            interval: 400,
    	            axisLabel: {
    	                formatter: '{value} MB'
    	            }
    	        }
    	    ],
    	    series: [
    	        {
    	            name:'平均每日上网时长',
    	            type:'bar',
    	            data:[${averageDuration[0]}, ${averageDuration[1]}]
    	        },
    	        
    	        {
    	            name:'平均每日使用流量',
    	            type:'bar',
    	            yAxisIndex: 1,
    	            data:[${averageDataUsage[0]}, ${averageDataUsage[1]}]
    	        }
    	    ]
    	};
 		// 使用刚指定的配置项和数据显示图表。
    		myChart.setOption(option);
    </script>
</div>
</div>

</body>
</html>