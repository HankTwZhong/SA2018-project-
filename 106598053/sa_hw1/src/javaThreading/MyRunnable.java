package javaThreading;


import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import gui.ColoredTableCellRenderer;
import gui.checkHostGUI;

public class MyRunnable implements Runnable  {

	private final String hostName;
	private final int row;
	
	
	public MyRunnable(String hostName, int row) {
		this.hostName = hostName;
		this.row = row;
	}
	
	
	public void setData(String ipData, boolean status, String currTime){		
	  checkHostGUI.data[row][1] = ipData;
	  checkHostGUI.data[row][3] = currTime;
	  if( status )checkHostGUI.data[row][2] = "UP";
	  else checkHostGUI.data[row][2] = "DOWN";
	  
	  checkHostGUI.table.repaint();
	  checkHostGUI.table.getColumnModel().getColumn(2).setCellRenderer(new ColoredTableCellRenderer());
	}
	
	String getTime(){
	  return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	@Override
	public void run() {
		String ip = "";
		boolean status = false;
		String timeStamp ;
		try {
			InetAddress inet = InetAddress.getByName(hostName);
			System.out.println("Sending Ping Request to " + hostName );			
			
			if (inet.isReachable(5000)) {		
				
				ip = inet.getHostAddress();
				status = true;
				timeStamp = getTime();
				
				System.out.println(hostName + " is reachable." + inet.getHostAddress());
				
			} else {				
				System.out.println(hostName + " NOT reachable.");
				timeStamp = getTime();
			}
		} catch (Exception e) {
			timeStamp = getTime();
			System.out.println("Exception:" + e.getMessage());
		}
		
		setData(ip, status, timeStamp);
		
	} // connect
	
}
