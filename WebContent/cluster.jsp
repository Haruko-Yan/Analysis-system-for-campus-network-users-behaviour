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
  <div id="collapseOne" class="panel-collapse collapse">
            <div class="panel-body">
            <ul style="background-color:#FFFFFF" class="nav nav-pills nav-stacked">
                <li role="presentation"><a href="preferenceOfTime.jsp">上网时间偏好</a></li>
  				<li role="presentation"><a href="durationAndData.jsp">上网时长及流量</a></li>
            </ul>
            </div>
   </div>
   <li role="presentation" class="active"><a href="cluster.jsp">聚类分析</a></li>
   <li role="presentation"><a href="analysisOfRelation.jsp">相关性分析</a></li>
</ul>

<div class="content">
	<div id="condition">
		<form class="form-inline" action="${pageContext.request.contextPath}/ClusterByDataOfWeek" method="post">
  			<div class="form-group">
    			<label for="num">聚类K值</label>
    			<input type="text" class="form-control" id="valueK" name="valueK" placeholder="例如:1">
  			</div>
  			<button type="submit" class="btn btn-default">查询</button>
		</form>
	</div>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<c:forEach begin="1" end="${valueK}" var="i" step="1">
		<div name="clusterChart" style="width: 1200px;height:250px;"></div>
	</c:forEach>

        <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
		var a = ${json};
		for(var i = 0; i < a.length; i++){
			var b = a[i];
			var serie = [];
	        for(var j = 0; j < b.length; j++){
	        	var item={
	        			color:'black',
	        			symbol:'none',
	        			type:'line',
	        			data:[b[j].mon0,b[j].mon1,b[j].mon2,b[j].mon3,b[j].mon4,b[j].mon5,b[j].mon6,b[j].mon7,b[j].mon8,b[j].mon9,b[j].mon10,
	        				b[j].mon11,b[j].mon12,b[j].mon13,b[j].mon14,b[j].mon15,b[j].mon16,b[j].mon17,b[j].mon18,b[j].mon19,b[j].mon20,
	        				b[j].mon21,b[j].mon22,b[j].mon23,b[j].tue0,b[j].tue1,b[j].tue2,b[j].tue3,b[j].tue4,b[j].tue5,b[j].tue6,b[j].tue7,
	        				b[j].tue8,b[j].tue9,b[j].tue10,b[j].tue11,b[j].tue12,b[j].tue13,b[j].tue14,b[j].tue15,b[j].tue16,b[j].tue17,b[j].tue18,
	        				b[j].tue19,b[j].tue20,b[j].tue21,b[j].tue22,b[j].tue23,b[j].wed0,b[j].wed1,b[j].wed2,b[j].wed3,b[j].wed4,b[j].wed5,
	        				b[j].wed6,b[j].wed7,b[j].wed8,b[j].wed9,b[j].wed10,b[j].wed11,b[j].wed12,b[j].wed13,b[j].wed14,b[j].wed15,b[j].wed16,
	        				b[j].wed17,b[j].wed18,b[j].wed19,b[j].wed20,b[j].wed21,b[j].wed22,b[j].wed23,b[j].thu0,b[j].thu1,b[j].thu2,b[j].thu3,
	        				b[j].thu4,b[j].thu5,b[j].thu6,b[j].thu7,b[j].thu8,b[j].thu9,b[j].thu10,b[j].thu11,b[j].thu12,b[j].thu13,b[j].thu14,
	        				b[j].thu15,b[j].thu16,b[j].thu17,b[j].thu18,b[j].thu19,b[j].thu20,b[j].thu21,b[j].thu22,b[j].thu23,b[j].fri0,b[j].fri1,
	        				b[j].fri2,b[j].fri3,b[j].fri4,b[j].fri5,b[j].fri6,b[j].fri7,b[j].fri8,b[j].fri9,b[j].fri10,b[j].fri11,b[j].fri12,
	        				b[j].fri13,b[j].fri14,b[j].fri15,b[j].fri16,b[j].fri17,b[j].fri18,b[j].fri19,b[j].fri20,b[j].fri21,b[j].fri22,b[j].fri23,
	        				b[j].sat0,b[j].sat1,b[j].sat2,b[j].sat3,b[j].sat4,b[j].sat5,b[j].sat6,b[j].sat7,b[j].sat8,b[j].sat9,b[j].sat10,
	        				b[j].sat11,b[j].sat12,b[j].sat13,b[j].sat14,b[j].sat15,b[j].sat16,b[j].sat17,b[j].sat18,b[j].sat19,b[j].sat20,
	        				b[j].sat21,b[j].sat22,b[j].sat23,b[j].sun0,b[j].sun1,b[j].sun2,b[j].sun3,b[j].sun4,b[j].sun5,b[j].sun6,b[j].sun7,
	        				b[j].sun8,b[j].sun9,b[j].sun10,b[j].sun11,b[j].sun12,b[j].sun13,b[j].sun14,b[j].sun15,b[j].sun16,b[j].sun17,b[j].sun18,
	        				b[j].sun19,b[j].sun20,b[j].sun21,b[j].sun22,b[j].sun23]
	        	};
	        	serie.push(item);
	        }
	        var myChart = echarts.init(document.getElementsByName('clusterChart')[i]);
	        var option = {
	        		 dataZoom: 
	        		        {
	        		            type: 'slider',
	        		            show: true,
	        		            xAxisIndex: [0],
	        		            start: 0,
	        		            end: 100
	        		        },
	        	    xAxis: {
	        	        type: 'category',
	        	        name: '时段',
	        	        data: ['Mon0','Mon1','Mon2','Mon3','Mon4','Mon5','Mon6','Mon7','Mon8','Mon9','Mon10',
	        	        	'Mon11','Mon12','Mon13','Mon14','Mon15','Mon16','Mon17','Mon18','Mon19','Mon20',
	        	        	'Mon21','Mon22','Mon23','Tue0','Tue1','Tue2','Tue3','Tue4','Tue5','Tue6','Tue7','Tue8','Tue9','Tue10',
	        	        	'Tue11','Tue12','Tue13','Tue14','Tue15','Tue16','Tue17','Tue18','Tue19','Tue20',
	        	        	'Tue21','Tue22','Tue23','Wed0','Wed1','Wed2','Wed3','Wed4','Wed5','Wed6','Wed7','Wed8','Wed9','Wed10',
	        	        	'Wed11','Wed12','Wed13','Wed14','Wed15','Wed16','Wed17','Wed18','Wed19','Wed20',
	        	        	'Wed21','Wed22','Wed23','Thu0','Thu1','Thu2','Thu3','Thu4','Thu5','Thu6','Thu7','Thu8','Thu9','Thu10',
	        	        	'Thu11','Thu12','Thu13','Thu14','Thu15','Thu16','Thu17','Thu18','Thu19','Thu20',
	        	        	'Thu21','Thu22','Thu23','Fri0','Fri1','Fri2','Fri3','Fri4','Fri5','Fri6','Fri7','Fri8','Fri9','Fri10',
	        	        	'Fri11','Fri12','Fri13','Fri14','Fri15','Fri16','Fri17','Fri18','Fri19','Fri20',
	        	        	'Fri21','Fri22','Fri23','Sat0','Sat1','Sat2','Sat3','Sat4','Sat5','Sat6','Sat7','Sat8','Sat9','Sat10',
	        	        	'Sat11','Sat12','Sat13','Sat14','Sat15','Sat16','Sat17','Sat18','Sat19','Sat20',
	        	        	'Sat21','Sat22','Sat23','Sun0','Sun1','Sun2','Sun3','Sun4','Sun5','Sun6','Sun7','Sun8','Sun9','Sun10',
	        	        	'Sun11','Sun12','Sun13','Sun14','Sun15','Sun16','Sun17','Sun18','Sun19','Sun20',
	        	        	'Sun21','Sun22','Sun23']
	        	    },
	        	    yAxis: {
	        	        type: 'value',
	        	        name: '流量',
	        	        max: '7500',
	        	        axisLabel: {
	    	                formatter: '{value} MB'
	    	            }
	        	    },
	        	    series:serie

	        	};


	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
		}
		
    </script>
</div>
</div>

</body>
</html>