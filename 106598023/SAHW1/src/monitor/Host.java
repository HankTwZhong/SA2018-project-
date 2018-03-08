package monitor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import view.Panel;

public class Host {

	public static void main(String[] args) {

		String[] headings = new String[] { "Host Name", "IP Address", "Status" };
		String[] urls = { "http://www.ntut.edu.tw/bin/home.php", "https://www.google.com.tw/", "https://facebook.com","https://youtudsdsdsdsbe.com.tw"};
		String[][] data = new String[urls.length][headings.length];
		Timer timer = new Timer();
		
		Panel panel = new Panel();
		timer.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < urls.length; i++) {
					try {
						data[i] = checkHost(urls[i]);
					} catch (UnknownHostException e) {
						data[i][0]=urls[i];
						data[i][1]="";
						data[i][2]="Down";
					}
					catch (MalformedURLException e) {
						data[i][0]=urls[i];
						data[i][1]="";
						data[i][2]="Down";
					} catch (IOException e) {
						data[i][0]=urls[i];
						data[i][1]="";
						data[i][2]="Down";
					}
				}

				Calendar calendar = Calendar.getInstance();
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				panel.refresh(headings, data, time);
			}
		}, 1000, 5000);
	}

	private static String[] checkHost(String address) throws MalformedURLException, IOException {
		String[] result = new String[3];
		URL url = new URL(address);
		InetAddress inetAddress = InetAddress.getByName(url.getHost());
		result[0] = inetAddress.getHostName();
		result[1] = inetAddress.getHostAddress();
		if (inetAddress.isReachable(5000)) {
			result[2] = "Up";
		} else {
			result[2] = "Down";
		}
		return result;
	}

}
