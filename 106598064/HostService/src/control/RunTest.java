package control;
import java.awt.Color;
import java.awt.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.table.DefaultTableModel;
public class RunTest extends java.util.TimerTask{
	private ArrayList<Host> list;
	private DefaultTableModel model;
	public RunTest(ArrayList<Host> _list,DefaultTableModel _model) 
	{
		list=_list;
		model=_model;
	}
	public void run() {  
	      // TODO Auto-generated method stub  
		model.setRowCount(0);
		for(int i=0;i<list.size();i++) 
		{
			if(list.get(i).check()) {
				model.addRow(new Object[] {list.get(i).getName(),list.get(i).getIP(),"OK",list.get(i).getTime()});
			}else {
				model.addRow(new Object[] {list.get(i).getName(),list.get(i).getIP(),"Down",list.get(i).getTime()});
			}
				
		}  
	 }
	public void setHost(String s) {
		Host a;
		try {
			a = new Host(s);
			list.add(a);
			model.setRowCount(0);
			run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void deleteHost(String s) {

		ArrayList<Host> temp=new ArrayList();		
		for(int i=0;i<list.size();i++) {
			if(!(list.get(i).getName().equals(s)||list.get(i).getIP().equals(s))) {
				temp.add(list.get(i));			
			}
		}
		
		
		list.clear();
		
		try {
			FileWriter fw = new FileWriter("test.txt");
			for(int i=0;i<temp.size();i++) {
				if(i==temp.size()-1) {
					fw.write(temp.get(i).getName());
				}else {
					fw.write(temp.get(i).getName()+"\r\n");
				}
				list.add(temp.get(i));
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.setRowCount(0);
		run();
		
	}
}
