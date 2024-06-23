package online_info;

import java.util.Date;

public class OnlineInfo {
	private String num;
	private Date start_time;
	private Date end_time;
	private String duration;
	private String data_usage;
	private String ip_address;
	private String international_upstream;
	private String international_downstream;
	private String domestic_upstream;
	private String domestic_downstream;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getData_usage() {
		return data_usage;
	}
	public void setData_usage(String data_usage) {
		this.data_usage = data_usage;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getInternational_upstream() {
		return international_upstream;
	}
	public void setInternational_upstream(String international_upstream) {
		this.international_upstream = international_upstream;
	}
	public String getInternational_downstream() {
		return international_downstream;
	}
	public void setInternational_downstream(String international_downstream) {
		this.international_downstream = international_downstream;
	}
	public String getDomestic_upstream() {
		return domestic_upstream;
	}
	public void setDomestic_upstream(String domestic_upstream) {
		this.domestic_upstream = domestic_upstream;
	}
	public String getDomestic_downstream() {
		return domestic_downstream;
	}
	public void setDomestic_downstream(String domestic_downstream) {
		this.domestic_downstream = domestic_downstream;
	}
	

	@Override
	public String toString() {
		return "OnlineInfo [num=" + num + ", start_time=" + start_time + ", end_time=" + end_time + ", duration="
				+ duration + ", data_usage=" + data_usage + ", ip_address=" + ip_address + ", international_upstream=" + international_upstream + ", international_downstream="
				+ international_downstream + ", domestic_upstream=" + domestic_upstream + ", domestic_downstream="
				+ domestic_downstream + "]";
	}

}
