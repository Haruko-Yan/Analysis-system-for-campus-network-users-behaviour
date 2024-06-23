package cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import jdbc.JDBCUtils;
import online_info.DataByWeek;
import online_info.PreferenceOfTime;

public class ClusterService {
	JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSourceToOnlineInfo());
	/**
	 * 根据K-means聚类上网时间偏好
	 * @return
	 */
	public List<List<PreferenceOfTime>> clusterPreferenceOfTime() {
		int size = 3;
		int times = 0;
		double[] clusterData = new double[24];//数据集的单个数据(无num,category值)
		double[] centerData = new double[24];//中心点的单个数据(无num,category值)
		double[] distance = new double[size];//用作取距离最小值的数组
		List<List<PreferenceOfTime>> afterCluster = new ArrayList<List<PreferenceOfTime>>();//一轮聚类后的数据集
		//先向afterCluster添加数个空List
		for (int i = 0; i < size; i++) {
			afterCluster.add(new ArrayList<PreferenceOfTime>());
		}
		List<PreferenceOfTime> sameCategoryList = new ArrayList<PreferenceOfTime>();//存储相同类别的数据
		double[][] centerArray = new double[size][24];//当前所有的中心点
		double[][] preCenterArray = new double[size][24];//上一轮所有的中心点
		//第一轮聚类时的中心点集，size为K值
		List<PreferenceOfTime> centerPoint = new ArrayList<PreferenceOfTime>(size);
		String sql = "select * from preferenceOfTime";
		List<PreferenceOfTime> list = jt.query(sql, new BeanPropertyRowMapper<PreferenceOfTime>(PreferenceOfTime.class));
		//打乱数据集，取前5(size)个数据作为中心点
		Collections.shuffle(list);
		for (int i = 0; i < size; i++) {
			centerPoint.add(list.get(i));
		}
		//提取出聚类数据
		for (PreferenceOfTime preferenceOfTime : list) {
			clusterData = ClusterUtils.extractData(preferenceOfTime);
			for (int i = 0; i < centerPoint.size(); i++) {
				centerData = ClusterUtils.extractData(centerPoint.get(i));
				//计算出距离后放入数组中待比大小
				distance[i] = ClusterUtils.dist(clusterData, centerData, clusterData.length);
			}
			//比大小，将最小距离的索引作为一轮聚类后数据集的索引(相当于将一条数据分了一次类，然后保存在拥有相同类别数据的List中)
			afterCluster.get(ClusterUtils.getMinDistIndex(distance)).add(preferenceOfTime);
		}
		//得到各个类的中心点
		for (int i = 0; i < afterCluster.size(); i++) {
			sameCategoryList =  afterCluster.get(i);
			//得到一个类数据的中心点
			centerArray[i] = ClusterUtils.getCenterPointArray(sameCategoryList);
		}
		//--------------------------第一次与第二次聚类分割线----------------------------------
		//判断中心点是否是上一次的中心点，若不是，则继续聚类
		while(!Arrays.deepEquals(preCenterArray, centerArray) && times < 11) {
			times ++;
			preCenterArray = centerArray;
			for (PreferenceOfTime preferenceOfTime : list) {
				clusterData = ClusterUtils.extractData(preferenceOfTime);
				for (int i = 0; i < centerArray.length; i++) {
					distance[i] = ClusterUtils.dist(clusterData, centerArray[i], clusterData.length);
				}
				afterCluster.get(ClusterUtils.getMinDistIndex(distance)).add(preferenceOfTime);
			}
			//得到各个类的中心点
			for (int i = 0; i < afterCluster.size(); i++) {
				sameCategoryList =  afterCluster.get(i);
				//得到一个类数据的中心点
				centerArray[i] = ClusterUtils.getCenterPointArray(sameCategoryList);
			}
		}
		
		return afterCluster;
	}
	
	/**
	 * 根据K-mediods聚类一周流量使用情况
	 * @return
	 */
	public List<List<DataByWeek>> clusterDataByWeek(int size){
		double cost = 0;//当前损失值
		double preCost = 0;//之前损失值
		double[] singleData = new double[168];//数据集中的单个数据
		double[] singlecenterData = new double[168];
		double[] distance = new double[size];
		int[] minIndex;//计算出的最小损失值的索引 
		List<List<DataByWeek>> afterCluster = new ArrayList<List<DataByWeek>>();//K-mediods算法聚类后的最终List
		//先向afterCluster添加数个空List
		for (int i = 0; i < size; i++) {
			afterCluster.add(new ArrayList<DataByWeek>());
		}
		String sql = "select * from databyweek";
		List<DataByWeek> list = jt.query(sql, new BeanPropertyRowMapper<DataByWeek>(DataByWeek.class));
		List<DataByWeek> notCenterPoint = jt.query(sql, new BeanPropertyRowMapper<DataByWeek>(DataByWeek.class));//非中心点List
		Collections.shuffle(notCenterPoint);
		List<DataByWeek> centerPoint = new ArrayList<DataByWeek>();//中心点List
		for (int i = 0; i < size; i++) {
			centerPoint.add(notCenterPoint.get(0));
			notCenterPoint.remove(0);
		}
		double[][] tempCost = new double[size][notCenterPoint.size()];//非样本点替换中心点后的损失值
		for (DataByWeek dataByWeek : list) {
			singleData = ClusterUtils.extractData(dataByWeek);
			for (int i = 0; i < centerPoint.size(); i++) {
				singlecenterData = ClusterUtils.extractData(centerPoint.get(i));
				//计算出距离后待比大小
				distance[i] = ClusterUtils.dist(singleData, singlecenterData, singleData.length);
			}
			cost += distance[ClusterUtils.getMinDistIndex(distance)]*distance[ClusterUtils.getMinDistIndex(distance)];
			preCost = cost;
		}
		//------------------------以上为初始化---------------------------
		int k = 0;
		DataByWeek container;//临时容器
		while(true) {
			for (int i = 0; i < centerPoint.size(); i++) {
				k = 0;
				container = centerPoint.get(i);
				for (DataByWeek dataByWeek : notCenterPoint) {
					//替换一个中心点
					centerPoint.set(i, dataByWeek);
					//根据暂时的中心点计算损失值，损失值为所有点到其中心点的距离之和
					for (DataByWeek dataByWeek1 : list) {
						singleData = ClusterUtils.extractData(dataByWeek1);
						for (int j = 0; j < centerPoint.size(); j++) {
							singlecenterData = ClusterUtils.extractData(centerPoint.get(j));
							//计算出距离后待比大小
							distance[j] = ClusterUtils.dist(singleData, singlecenterData, singleData.length);
						}
						//得到一个点到其中心点的误差平方，接着循环叠加其他点的误差平方得到替换后的损失值
						tempCost[i][k] += distance[ClusterUtils.getMinDistIndex(distance)]*distance[ClusterUtils.getMinDistIndex(distance)];
					}
					k++;
				}
				centerPoint.set(i, container);
			}
			//得到损失值最小值索引
			minIndex = ClusterUtils.getMinCostIndex(tempCost);
			//判断最小损失值是否小于先前损失值
			if (tempCost[minIndex[0]][minIndex[1]] < preCost) {
				//取代中心点，将之前的中心点放入非中心点集
				DataByWeek tempDataByWeek = notCenterPoint.get(minIndex[1]);
				notCenterPoint.set(minIndex[1], centerPoint.get(minIndex[0]));
				centerPoint.set(minIndex[0], tempDataByWeek);
			}
			else {
				for (DataByWeek dataByWeek : list) {
					singleData = ClusterUtils.extractData(dataByWeek);
					for (int i = 0; i < centerPoint.size(); i++) {
						singlecenterData = ClusterUtils.extractData(centerPoint.get(i));
						//计算出距离后待比大小
						distance[i] = ClusterUtils.dist(singleData, singlecenterData, singleData.length);
					}
					//比大小，将最小距离的索引作为一轮聚类后数据集的索引(相当于将一条数据分了一次类，然后保存在拥有相同类别数据的List中)
					afterCluster.get(ClusterUtils.getMinDistIndex(distance)).add(dataByWeek);
				}
				break;
			}
		}
		return afterCluster;
	}

}
