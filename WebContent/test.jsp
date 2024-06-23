<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<script src="js/jquery-3.3.0.min.js"></script>
 	<!-- 引入 ECharts 文件 -->
    <script src="echarts.common.min.js"></script>
<title>BarChart</title>
</head>
<body>
	 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1200px;height:800px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
		var a = ${json};
		var b = a[1];
		var serie = [];
        for(var i = 0; i < b.length; i++){
        	var item={
        			color:'black',
        			symbol:'none',
        			type:'line',
        			data:[b[i].mon0,b[i].mon1,b[i].mon2,b[i].mon3,b[i].mon4,b[i].mon5,b[i].mon6,b[i].mon7,b[i].mon8,b[i].mon9,b[i].mon10,
        				b[i].mon11,b[i].mon12,b[i].mon13,b[i].mon14,b[i].mon15,b[i].mon16,b[i].mon17,b[i].mon18,b[i].mon19,b[i].mon20,
        				b[i].mon21,b[i].mon22,b[i].mon23,b[i].tue0,b[i].tue1,b[i].tue2,b[i].tue3,b[i].tue4,b[i].tue5,b[i].tue6,b[i].tue7,
        				b[i].tue8,b[i].tue9,b[i].tue10,b[i].tue11,b[i].tue12,b[i].tue13,b[i].tue14,b[i].tue15,b[i].tue16,b[i].tue17,b[i].tue18,
        				b[i].tue19,b[i].tue20,b[i].tue21,b[i].tue22,b[i].tue23,b[i].wed0,b[i].wed1,b[i].wed2,b[i].wed3,b[i].wed4,b[i].wed5,
        				b[i].wed6,b[i].wed7,b[i].wed8,b[i].wed9,b[i].wed10,b[i].wed11,b[i].wed12,b[i].wed13,b[i].wed14,b[i].wed15,b[i].wed16,
        				b[i].wed17,b[i].wed18,b[i].wed19,b[i].wed20,b[i].wed21,b[i].wed22,b[i].wed23,b[i].thu0,b[i].thu1,b[i].thu2,b[i].thu3,
        				b[i].thu4,b[i].thu5,b[i].thu6,b[i].thu7,b[i].thu8,b[i].thu9,b[i].thu10,b[i].thu11,b[i].thu12,b[i].thu13,b[i].thu14,
        				b[i].thu15,b[i].thu16,b[i].thu17,b[i].thu18,b[i].thu19,b[i].thu20,b[i].thu21,b[i].thu22,b[i].thu23,b[i].fri0,b[i].fri1,
        				b[i].fri2,b[i].fri3,b[i].fri4,b[i].fri5,b[i].fri6,b[i].fri7,b[i].fri8,b[i].fri9,b[i].fri10,b[i].fri11,b[i].fri12,
        				b[i].fri13,b[i].fri14,b[i].fri15,b[i].fri16,b[i].fri17,b[i].fri18,b[i].fri19,b[i].fri20,b[i].fri21,b[i].fri22,b[i].fri23,
        				b[i].sat0,b[i].sat1,b[i].sat2,b[i].sat3,b[i].sat4,b[i].sat5,b[i].sat6,b[i].sat7,b[i].sat8,b[i].sat9,b[i].sat10,
        				b[i].sat11,b[i].sat12,b[i].sat13,b[i].sat14,b[i].sat15,b[i].sat16,b[i].sat17,b[i].sat18,b[i].sat19,b[i].sat20,
        				b[i].sat21,b[i].sat22,b[i].sat23,b[i].sun0,b[i].sun1,b[i].sun2,b[i].sun3,b[i].sun4,b[i].sun5,b[i].sun6,b[i].sun7,
        				b[i].sun8,b[i].sun9,b[i].sun10,b[i].sun11,b[i].sun12,b[i].sun13,b[i].sun14,b[i].sun15,b[i].sun16,b[i].sun17,b[i].sun18,
        				b[i].sun19,b[i].sun20,b[i].sun21,b[i].sun22,b[i].sun23]
        	};
        	serie.push(item);
        }
        var myChart = echarts.init(document.getElementById('main'));
        var option = {
        		 dataZoom: 
        		        {
        		            type: 'slider',
        		            show: true,
        		            xAxisIndex: [0],
        		            start: 1,
        		            end: 35
        		        },
        	    xAxis: {
        	        type: 'category',
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
        	        type: 'value'
        	    },
        	    series:serie

        	};


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>

</body>
</html>