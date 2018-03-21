package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import model.HostData;
import view.Panel;

public class DataOperator {

	public static void sendDataToPanel() {

		String[] headings = new String[] { "Host Name", "IP Address", "Status", "Date" };
		// String[] urls = { "http://www.ntut.edu.tw/bin/home.php",
		// "https://www.google.com.tw/", "https://facebook.com",
		// "https://youtudsdsdsdsbe.com.tw" };

		Timer timer = new Timer();

		Panel panel = new Panel();
		timer.schedule(new TimerTask() {
			String[][] data;

			public void run() {
				try {
					ArrayList<HostData> arrayListData = parseJson();
					data = new String[arrayListData.size()][headings.length];
					for (int i = 0; i < arrayListData.size(); i++) {
						data[i][0] = arrayListData.get(i).getHostName();
						data[i][1] = arrayListData.get(i).getIpAddress();
						data[i][2] = arrayListData.get(i).getActive();
						data[i][3] = arrayListData.get(i).getDate();
					}
				} catch (Exception e) {

				}

				panel.refresh(headings, data);
			}
		}, 1000, 5000);
	}

	public static void main(String[] args) throws Exception {
		sendDataToPanel();
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

	public static String sendGetRequest() throws IOException {
		String url = "http://localhost:3000/getHostsData";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	private static ArrayList<HostData> parseJson() throws Exception {
		JSONObject json;
		ArrayList<HostData> myData = new ArrayList<>();
		try {

			json = new JSONObject(sendGetRequest());
			System.out.println(json.get("data"));

			final JSONArray geodata = json.getJSONArray("data");
			final int n = geodata.length();
			System.out.println("length = " + n);
			for (int i = 0; i < n; ++i) {
				final JSONObject person = geodata.getJSONObject(i);
				System.out.println(person.getString("date"));
				System.out.println(person.getString("hostName"));
				System.out.println(person.getString("ipAddress"));
				System.out.println(person.getString("active"));

				myData.add(new HostData(person.getString("date"), person.getString("hostName"),
						person.getString("ipAddress"), person.getString("active")));
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return myData;
	}

}
