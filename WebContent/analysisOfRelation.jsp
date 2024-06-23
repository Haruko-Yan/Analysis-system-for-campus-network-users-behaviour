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
    <title>相关性分析</title>

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
	#button{
		margin-top:50px;
		margin-left:50px;
	}
	#barChart1{
		float:left;
	}
	#coefficient1{
		float:right;
		margin-top:100px;
	}
	#barChart2{
		float:left;
	}
	#coefficient2{
		float:right;
		margin-top:100px;
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
  <div id="collapseOne" class="panel-collapse collapse ">
            <div class="panel-body">
            <ul style="background-color:#FFFFFF" class="nav nav-pills nav-stacked">
                <li role="presentation"><a href="preferenceOfTime.jsp">上网时间偏好</a></li>
  				<li role="presentation"><a href="durationAndData.jsp">上网时长及流量</a></li>
            </ul>
            </div>
   </div>
   <li role="presentation"><a href="cluster.jsp">聚类分析</a></li>
   <li role="presentation" class="active"><a href="analysisOfRelation.jsp">相关性分析</a></li>
</ul>
<div class="content">
<div id = "button">
	<a class="btn btn-info" href="${pageContext.request.contextPath}/AnalysisOfRelationServlet" role="button">关联图表</a>
</div>
<div id="barChart">
	<div>
	<div id="barChart1" style="width: 960px;height:500px;"></div>
	<div id="coefficient1">相关系数：${coefficients[0]}</div>
	</div>
	<div>
	<div id="barChart2" style="width: 960px;height:500px;"></div>
	<div id="coefficient1">相关系数：${coefficients[1]}</div>
	</div>

<script type="text/javascript">
var myChart = echarts.init(document.getElementById('barChart1'));
var dataSet = ${dataSet};
var lines = ${lines};
var twoPoints = ${twoPoints};
var dataAll = [];
var data1 = [];
for(var i = 0; i < dataSet[0].length; i++){
	data = [dataSet[0][i].data,dataSet[0][i].score]
	data1.push(data);
}
dataAll.push(data1);

var markLineOpt = {
    animation: false,
    label: {
        normal: {
            formatter: lines[0],
            textStyle: {
                align: 'right'
            }
        }
    },
    lineStyle: {
        normal: {
            type: 'solid'
        }
    },
    tooltip: {
        formatter: lines[0]
    },
    data: [[{
        coord: [twoPoints[0][0][0], twoPoints[0][0][1]],
        symbol: 'none'
    }, {
        coord: [twoPoints[0][1][0], twoPoints[0][1][1]],
        symbol: 'none'
    }]]
};

option = {
    title: {
        text: '考试成绩与每日使用流量相关图',
        x: 'center',
        y: 0
    },

    tooltip: {
        formatter: 'Group {a}: ({c})'
    },
    xAxis: [
    	{name:'使用流量',gridIndex: 0, min: 0, max: 3000,axisLabel: {formatter: '{value} MB'}}
    ],
    yAxis: [
    	{name:'成绩',gridIndex: 0, min: 0, max: 100,axisLabel: {formatter: '{value} 分'}}
    ],
    series: [
        {
            name: 'I',
            type: 'scatter',
            xAxisIndex: 0,
            yAxisIndex: 0,
            data: dataAll[0],
            markLine: markLineOpt
        },
    ]
        
    
};
myChart.setOption(option);
</script>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('barChart2'));
var dataSet = ${dataSet};
var lines = ${lines};
var twoPoints = ${twoPoints};
var dataAll = [];
var data1 = [];
for(var i = 0; i < dataSet[1].length; i++){
	data = [dataSet[1][i].duration,dataSet[1][i].score]
	data1.push(data);
}
dataAll.push(data1);

var markLineOpt = {
    animation: false,
    label: {
        normal: {
            formatter: lines[1],
            textStyle: {
                align: 'right'
            }
        }
    },
    lineStyle: {
        normal: {
            type: 'solid'
        }
    },
    tooltip: {
        formatter: lines[1]
    },
    data: [[{
        coord: [twoPoints[1][0][0], twoPoints[1][0][1]],
        symbol: 'none'
    }, {
        coord: [twoPoints[1][1][0], twoPoints[1][1][1]],
        symbol: 'none'
    }]]
};

option = {
    title: {
        text: '考试成绩与每日上网时长相关图',
        x: 'center',
        y: 0
    },

    tooltip: {
        formatter: 'Group {a}: ({c})'
    },
    xAxis: [
        {name:'上网时长',gridIndex: 0, min: 0, max: 1200,axisLabel: {formatter: '{value} min'}}
    ],
    yAxis: [
        {name:'成绩',gridIndex: 0, min: 0, max: 100,axisLabel: {formatter: '{value} 分'}}
    ],
    series: [
        {
            name: 'I',
            type: 'scatter',
            xAxisIndex: 0,
            yAxisIndex: 0,
            data: dataAll[0],
            markLine: markLineOpt
        },
    ]
        
    
};
myChart.setOption(option);
</script>
</div>
</div>
</div>
</body>
</html>