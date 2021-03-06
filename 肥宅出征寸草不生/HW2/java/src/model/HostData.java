package model;

public class HostData {
	
	private String date;
	private String hostName;
	private String ipAddress;
	private String active;
	
	public HostData( String date, String hostName, String ipAddress, String active ) {
		this.date = date;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.active = active;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public String getActive() {
		return active;
	}

}
