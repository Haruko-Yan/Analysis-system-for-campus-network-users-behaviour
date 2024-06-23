package cluster;

import java.util.ArrayList;
import java.util.List;

import online_info.DataByWeek;
import online_info.PreferenceOfTime;

public class ClusterUtils {
	/**
	 * 提取0-23点的数据
	 * @param pot
	 * @return
	 */
	public static double[] extractData(PreferenceOfTime pot) {
		double[] clusterData = new double[24];
		clusterData[0] = pot.getA0();
		clusterData[1] = pot.getA1();
		clusterData[2] = pot.getA2();
		clusterData[3] = pot.getA3();
		clusterData[4] = pot.getA4();
		clusterData[5] = pot.getA5();
		clusterData[6] = pot.getA6();
		clusterData[7] = pot.getA7();
		clusterData[8] = pot.getA8();
		clusterData[9] = pot.getA9();
		clusterData[10] = pot.getA10();
		clusterData[11] = pot.getA11();
		clusterData[12] = pot.getA12();
		clusterData[13] = pot.getA13();
		clusterData[14] = pot.getA14();
		clusterData[15] = pot.getA15();
		clusterData[16] = pot.getA16();
		clusterData[17] = pot.getA17();
		clusterData[18] = pot.getA18();
		clusterData[19] = pot.getA19();
		clusterData[20] = pot.getA20();
		clusterData[21] = pot.getA21();
		clusterData[22] = pot.getA22();
		clusterData[23] = pot.getA23();
		return clusterData;
	}
	/**
	 * 提取一周流量使用情况数据
	 * @param dbw
	 * @return
	 */
	public static double[] extractData(DataByWeek dbw) {
		double[] clusterData = new double[168];
	    clusterData[0] = dbw.getMon0();clusterData[1] = dbw.getMon1();clusterData[2] = dbw.getMon2();
		clusterData[3] = dbw.getMon3();clusterData[4] = dbw.getMon4();clusterData[5] = dbw.getMon5();
		clusterData[6] = dbw.getMon6();clusterData[7] = dbw.getMon7();clusterData[8] = dbw.getMon8();
		clusterData[9] = dbw.getMon9();clusterData[10] = dbw.getMon10();clusterData[11] = dbw.getMon11();
		clusterData[12] = dbw.getMon12();clusterData[13] = dbw.getMon13();clusterData[14] = dbw.getMon14();
		clusterData[15] = dbw.getMon15();clusterData[16] = dbw.getMon16();clusterData[17] = dbw.getMon17();
		clusterData[18] = dbw.getMon18();clusterData[19] = dbw.getMon19();clusterData[20] = dbw.getMon20();
		clusterData[21] = dbw.getMon21();clusterData[22] = dbw.getMon22();clusterData[23] = dbw.getMon23();
		clusterData[24] = dbw.getTue0();clusterData[25] = dbw.getTue1();clusterData[26] = dbw.getTue2();
		clusterData[27] = dbw.getTue3();clusterData[28] = dbw.getTue4();clusterData[29] = dbw.getTue5();
		clusterData[30] = dbw.getTue6();clusterData[31] = dbw.getTue7();clusterData[32] = dbw.getTue8();
		clusterData[33] = dbw.getTue9();clusterData[34] = dbw.getTue10();clusterData[35] = dbw.getTue11();
		clusterData[36] = dbw.getTue12();clusterData[37] = dbw.getTue13();clusterData[38] = dbw.getTue14();
		clusterData[39] = dbw.getTue15();clusterData[40] = dbw.getTue16();clusterData[41] = dbw.getTue17();
		clusterData[42] = dbw.getTue18();clusterData[43] = dbw.getTue19();clusterData[44] = dbw.getTue20();
		clusterData[45] = dbw.getTue21();clusterData[46] = dbw.getTue22();clusterData[47] = dbw.getTue23();
		clusterData[48] = dbw.getWed0();clusterData[49] = dbw.getWed1();clusterData[50] = dbw.getWed2();
		clusterData[51] = dbw.getWed3();clusterData[52] = dbw.getWed4();clusterData[53] = dbw.getWed5();
		clusterData[54] = dbw.getWed6();clusterData[55] = dbw.getWed7();clusterData[56] = dbw.getWed8();
		clusterData[57] = dbw.getWed9();clusterData[58] = dbw.getWed10();clusterData[59] = dbw.getWed11();
		clusterData[60] = dbw.getWed12();clusterData[61] = dbw.getWed13();clusterData[62] = dbw.getWed14();
		clusterData[63] = dbw.getWed15();clusterData[64] = dbw.getWed16();clusterData[65] = dbw.getWed17();
		clusterData[66] = dbw.getWed18();clusterData[67] = dbw.getWed19();clusterData[68] = dbw.getWed20();
		clusterData[69] = dbw.getWed21();clusterData[70] = dbw.getWed22();clusterData[71] = dbw.getWed23();
		clusterData[72] = dbw.getThu0();clusterData[73] = dbw.getThu1();clusterData[74] = dbw.getThu2();
		clusterData[75] = dbw.getThu3();clusterData[76] = dbw.getThu4();clusterData[77] = dbw.getThu5();
		clusterData[78] = dbw.getThu6();clusterData[79] = dbw.getThu7();clusterData[80] = dbw.getThu8();
		clusterData[81] = dbw.getThu9();clusterData[82] = dbw.getThu10();clusterData[83] = dbw.getThu11();
		clusterData[84] = dbw.getThu12();clusterData[85] = dbw.getThu13();clusterData[86] = dbw.getThu14();
		clusterData[87] = dbw.getThu15();clusterData[88] = dbw.getThu16();clusterData[89] = dbw.getThu17();
		clusterData[90] = dbw.getThu18();clusterData[91] = dbw.getThu19();clusterData[92] = dbw.getThu20();
		clusterData[93] = dbw.getThu21();clusterData[94] = dbw.getThu22();clusterData[95] = dbw.getThu23();
		clusterData[96] = dbw.getFri0();clusterData[97] = dbw.getFri1();clusterData[98] = dbw.getFri2();
		clusterData[99] = dbw.getFri3();clusterData[100] = dbw.getFri4();clusterData[101] = dbw.getFri5();
		clusterData[102] = dbw.getFri6();clusterData[103] = dbw.getFri7();clusterData[104] = dbw.getFri8();
		clusterData[105] = dbw.getFri9();clusterData[106] = dbw.getFri10();clusterData[107] = dbw.getFri11();
		clusterData[108] = dbw.getFri12();clusterData[109] = dbw.getFri13();clusterData[110] = dbw.getFri14();
		clusterData[111] = dbw.getFri15();clusterData[112] = dbw.getFri16();clusterData[113] = dbw.getFri17();
		clusterData[114] = dbw.getFri18();clusterData[115] = dbw.getFri19();clusterData[116] = dbw.getFri20();
		clusterData[117] = dbw.getFri21();clusterData[118] = dbw.getFri22();clusterData[119] = dbw.getFri23();
		clusterData[120] = dbw.getSat0();clusterData[121] = dbw.getSat1();clusterData[122] = dbw.getSat2();
		clusterData[123] = dbw.getSat3();clusterData[124] = dbw.getSat4();clusterData[125] = dbw.getSat5();
		clusterData[126] = dbw.getSat6();clusterData[127] = dbw.getSat7();clusterData[128] = dbw.getSat8();
		clusterData[129] = dbw.getSat9();clusterData[130] = dbw.getSat10();clusterData[131] = dbw.getSat11();
		clusterData[132] = dbw.getSat12();clusterData[133] = dbw.getSat13();clusterData[134] = dbw.getSat14();
		clusterData[135] = dbw.getSat15();clusterData[136] = dbw.getSat16();clusterData[137] = dbw.getSat17();
		clusterData[138] = dbw.getSat18();clusterData[139] = dbw.getSat19();clusterData[140] = dbw.getSat20();
		clusterData[141] = dbw.getSat21();clusterData[142] = dbw.getSat22();clusterData[143] = dbw.getSat23();
		clusterData[144] = dbw.getSun0();clusterData[145] = dbw.getSun1();clusterData[146] = dbw.getSun2();
		clusterData[147] = dbw.getSun3();clusterData[148] = dbw.getSun4();clusterData[149] = dbw.getSun5();
		clusterData[150] = dbw.getSun6();clusterData[151] = dbw.getSun7();clusterData[152] = dbw.getSun8();
		clusterData[153] = dbw.getSun9();clusterData[154] = dbw.getSun10();clusterData[155] = dbw.getSun11();
		clusterData[156] = dbw.getSun12();clusterData[157] = dbw.getSun13();clusterData[158] = dbw.getSun14();
		clusterData[159] = dbw.getSun15();clusterData[160] = dbw.getSun16();clusterData[161] = dbw.getSun17();
		clusterData[162] = dbw.getSun18();clusterData[163] = dbw.getSun19();clusterData[164] = dbw.getSun20();
		clusterData[165] = dbw.getSun21();clusterData[166] = dbw.getSun22();clusterData[167] = dbw.getSun23();
		return clusterData;
	}
	/**
	 * 计算两点间的距离
	 * @param clusterData
	 * @param centerData
	 * @param dim
	 * @return
	 */
	public static double dist(double[] clusterData,double[] centerData,int dim) {
		double distance = 0;
		for (int i = 0; i < dim; i++) {
			distance = distance + (clusterData[i]-centerData[i])*(clusterData[i]-centerData[i]);
		}
		return Math.sqrt(distance);
	}
	/**
	 * 计算出最小距离点的所属类别
	 * @return
	 */
	public static int getMinDistIndex(double[] dist) {
		int minIndex = 0;
		for (int i = 0; i < dist.length-1; i++) {
			if (dist[minIndex] > dist[i+1]) {
				minIndex = i + 1;
			}
		}
		return minIndex;
	}
	public static int[] getMinCostIndex(double[][] tempCost) {
		int[] minIndex = {0,0};
        for (int i = 0; i < tempCost.length; i++) {
            for (int j = 0; j < tempCost[i].length; j++) {
                if (tempCost[minIndex[0]][minIndex[1]] > tempCost[i][j]) {
                    minIndex[0] = i;
                    minIndex[1] = j;
                }
            }
        }
		return minIndex;
	}
	/**
	 * 计算出各类的中心点
	 * @param sameCategoryList
	 * @return
	 */
	public static double[] getCenterPointArray(List<PreferenceOfTime> sameCategoryList) {
		double[][] list = new double[sameCategoryList.size()][24];
		double[] center = new double[24];
		double sum;
		for (int i = 0; i < sameCategoryList.size(); i++) {
			list[i] = extractData(sameCategoryList.get(i));
		}
		for (int i = 0; i < center.length; i++) {
			sum = 0;
			for (int j = 0; j < list.length; j++) {
				sum = sum + list[j][i];
			}
			center[i] = sum / center.length;
		}
		return center;
	}
	

}
