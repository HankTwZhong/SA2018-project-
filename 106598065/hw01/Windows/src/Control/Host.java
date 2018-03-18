package Control;
import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Host {
	
	private String _fileName;
	private String _hostName;
	private String _ip;
	private boolean _status;
	private InetAddress _address; //取得host name or ip

	
	public Host(String url) throws IOException{
		try {
			_fileName = url;
			_address = InetAddress.getByName(url);
			_hostName = _address.getHostName();
			_ip = _address.getHostAddress();
			_status = _address.isReachable(5000);
			
			if(url.equals(_address.getHostAddress())) {
				_hostName = _ip;
			}
			
		} catch (UnknownHostException e) {//如果讀到錯誤的IP或者hostname則一樣會加入表格
			e.printStackTrace();
			_fileName = url;
			_hostName = url;
			_address = null;
			_ip = null;
			_status = false;
		}
	}
	
	public boolean check() throws IOException {
		try {
			_address = InetAddress.getByName(_fileName);
//			_hostName = _address.getHostName();
//			_ip = _address.getHostAddress();
			_status = _address.isReachable(5000);
			
//			if(_fileName.equals(_address.getHostAddress())) {
//				_hostName = _ip;
//			}
			
		} catch (UnknownHostException e) {//如果讀到錯誤的IP或者hostname則一樣會加入表格
			//e.printStackTrace();
//			_hostName = _fileName;
//			_address = null;
//			_ip = null;
			_status = false;

		} catch (IOException e) {
			_status = false;
		}
		return _status;
	}
	
	public String getName() {
		return _hostName;
	}
	
	public String getFileNmae() {
		return _fileName;
	}
	
	public String getIp() {
		return _ip;
	}
	
	public String getStatus() {
		String status;
		if(_status) {
			status = "UP";
		}else {
			status = "DOWN";
		}
			
		return status;
	}
	
	public String getTime() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String _Sdate = sdFormat.format(date);
		return _Sdate;
	}
}
