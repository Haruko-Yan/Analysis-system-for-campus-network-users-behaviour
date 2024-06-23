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
//     //<c:forEach items="${list}" var ="referenceOfTime">
// 		<c:if test="true">
// 			var a = 1;
// 		</c:if>
// 	//</c:forEach>
        // 基于准备好的dom，初始化echarts实例
		var a = ${json};
		var b = a[0];
		var serie = [];
        for(var i = 0; i < b.length; i++){
        	var item={
        			color:'black',
        			symbol:'none',
        			type:'line',
        			data:[b[i].a0,b[i].a1,b[i].a2,b[i].a3,b[i].a4,b[i].a5,b[i].a6,b[i].a7,b[i].a8,b[i].a9,b[i].a10,
        				b[i].a11,b[i].a12,b[i].a13,b[i].a14,b[i].a15,b[i].a16,b[i].a17,b[i].a18,b[i].a19,b[i].a20,
        				b[i].a21,b[i].a22,b[i].a23]
        	};
        	serie.push(item);
        }
        var myChart = echarts.init(document.getElementById('main'));
//         var serie=[];
//         var item={
// 				type:'line',
// 				data:[a.age,2,3,4,5]
// 		};
//         var item1={
//  				type:'line',
//  				data:[5,4,3,2,1]
//  		};
//         serie.push(item);
//         serie.push(item1);
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
        	        data: ['0点', '1点', '2点', '3点', '4点','5点','6点','7点','8点','9点','10点','11点','12点','13点','14点',
        	        	'15点','16点','17点','18点','19点','20点','21点','22点','23点']
        	    },
        	    yAxis: {
        	        type: 'value'
        	    },
        	    series:serie
//         	    series: function(){
//         	    	var serie = [];
//         	    	var a = ${d[0][0]}
//         	    	for(var i = 0, i < ${d}.length, i++){
//         	    		var item={
//         	    				type:'line',
//         	    				data:[${d}[i][0],${d}[i][1],${d}[i][2],${d}[i][3],${d}[i][4]]
//         	    		};
//         	    		serie.push(item);
//         	    	}
        	    		
// //         	    		var item1={
// //         	    				type:'line',
// //         	    				data:[${d[1][0]},${d[1][1]},${d[1][2]},${d[1][3]},${d[1][4]}]
// //         	    		};
        	    		
//         	    		//serie.push(item1);
//         	    	return serie;
//         	    }()
        	};


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>

</body>
</html>