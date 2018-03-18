package Control;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.table.DefaultTableModel;

public class CheckHost extends java.util.TimerTask {
	private ArrayList<Host> checkHostlist;
	private DefaultTableModel tableModel;//使用DefaultTableModel注入表格資料 - Java視窗程式JTable元件應用
	private int _checkFrequency;
	private String _hostFile = "src/Control/hostlist.txt";
	
	public CheckHost(ArrayList<Host> _list, DefaultTableModel _model) {
		checkHostlist = _list;
		tableModel = _model;
		_checkFrequency = 3000;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		tableModel.setRowCount(0);
		for(int j = 0; j < checkHostlist.size(); j++) {
			try {
				checkHostlist.get(j).check();
				tableModel.addRow(new Object[] {checkHostlist.get(j).getName(), checkHostlist.get(j).getIp(), checkHostlist.get(j).getTime(), checkHostlist.get(j).getStatus()});
//				if( checkHostlist.get(j).check() ) {
//					tableModel.addRow(new Object[] {checkHostlist.get(j).getName(), checkHostlist.get(j).getIp(), checkHostlist.get(j).getTime(), "UP"});
//				}else {
//					tableModel.addRow(new Object[] {checkHostlist.get(j).getName(), checkHostlist.get(j).getIp(), checkHostlist.get(j).getTime(), "DOWN"});
//				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void setNewHost(String s) throws IOException {
		Host n1;
		
		FileReader fr = new FileReader(_hostFile);//讀黨
		BufferedReader br = new BufferedReader(fr);
		String rline;
		ArrayList<String> rlineArray = new ArrayList<String>();
		while(br.ready()) {
			rline = br.readLine();
			rlineArray.add(rline);//放到list中
		}
		
		FileWriter fw = new FileWriter(_hostFile);//寫黨
		for(int i = 0; i < rlineArray.size(); i++) {
			fw.write(rlineArray.get(i));//先寫入之前的檔案
			fw.write("\n");
		}
//		if(s == "") {
//			fw.write("localhost");//如果讀到空白就是localhost 否則寫入新增的host
//		}else {
			fw.write(s);
//		}
		fw.flush();
		fw.close();
		
		n1 = new Host(s); //增加host到視窗化程式
		checkHostlist.add(n1);
		tableModel.setRowCount(0);
		run();
	}
	
	public void deleteHost(String s) throws IOException {
		Host n1;
		
		FileReader fr = new FileReader(_hostFile);//讀黨
		BufferedReader br = new BufferedReader(fr);
		String rline;
		ArrayList<String> rlineArray = new ArrayList<String>();
		while(br.ready()) {
			rline = br.readLine().trim();
			if(!rline.equals(s)) {
				System.out.println("rline = "+rline+", s = "+s);
				rlineArray.add(rline);//放到list中
			}
		}
		
		FileWriter fw = new FileWriter(_hostFile);//寫黨
		for(int i = 0; i < rlineArray.size(); i++) {
			fw.write(rlineArray.get(i));//先寫入之前的檔案
			if( i + 1 < rlineArray.size()) {
				fw.write("\n");
			}
		}
		fw.flush();
		fw.close();
		
		n1 = new Host(s); //增加host到視窗化程式
		for(int i = 0; i < checkHostlist.size(); i++) {
			System.out.println(checkHostlist.get(i).getFileNmae()+" , "+ s);
			if(checkHostlist.get(i).getName() == n1.getName()) {
				System.out.println("equal1");
				checkHostlist.remove(i);
				i = 0;
			}else if(checkHostlist.get(i).getIp() == n1.getIp()) {
				if(checkHostlist.get(i).getFileNmae().equals(s)) {
					System.out.println("equal2");
					checkHostlist.remove(i);
					i = 0;
				}
				
			}else if(checkHostlist.get(i).getFileNmae().equals(s)) {
				System.out.println("equal3");
				checkHostlist.remove(i);
				i = 0;
			}
		}
		tableModel.setRowCount(0);
		run();
	}
	//如果新增 刪除 才需要重新READ 否則不用 目前是只有第一次要READ
	
	public ArrayList<Host> readFilteHost(ArrayList<Host> _list) {
		Host n1 = null;
		
		 try {
			FileReader fr = new FileReader(_hostFile);
			BufferedReader br = new BufferedReader(fr);
			ArrayList filehostlist = new ArrayList();
			String rline;
			try {
				while(br.ready()) {
					 rline = br.readLine();
					 String [] rlineArray = rline.trim().split("\\s+");
					 System.out.println(rlineArray[0]);
					 if(rlineArray[0] != null) {
						n1 = new Host(rlineArray[0]);
						checkHostlist.add(n1);
					 }
				} 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		
		return _list;
	}
	
	public void setCheckfrequency(int f) {
		_checkFrequency = f;
	}
	
	public int getCheckfrequency() {
		return _checkFrequency;
	}
	
}
