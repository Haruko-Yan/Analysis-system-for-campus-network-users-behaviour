package cluster;

import java.util.ArrayList;
import java.util.List;

import online_info.PreferenceOfTime;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<List<PreferenceOfTime>> afterCluster = new ArrayList<List<PreferenceOfTime>>();
		for (int i = 0; i < 5; i++) {
			afterCluster.add(new ArrayList<PreferenceOfTime>());
		}
		afterCluster.remove(0);
		System.out.println(afterCluster);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		for (Integer integer : list) {
			System.out.println(integer);
		}
	}

}
