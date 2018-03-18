package control;
import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Host {
	private String HostName;
	private String IP;
	private boolean Status;
	private InetAddress address;
	public Host(String url) throws IOException 
	{
		
		try {
			address = InetAddress.getByName(url);
			HostName=address.getHostName();
			IP=address.getHostAddress();
			Status=address.isReachable(1000);
			if(!Status) {
				HostName=url;
				IP=" ";
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HostName=url;
			Status=false;
			IP=" ";
		} 
	}
	
	public String getName() 
	{
		return HostName;
	}
	
	public String getIP() 
	{
		return IP;
	}
	
	public boolean getStatus() 
	{
		return Status;
	}
	
	public String getTime()
	{
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		return strDate;
	}
	
	public boolean check() 
	{
		
		Status=false;
		try {
			address = InetAddress.getByName(HostName);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(address!=null) {
			try {
				if(address.isReachable(100)) {				
					HostName=address.getHostName();
					IP=address.getHostAddress();
					Status=true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
		return Status;
	}
	
	
}
