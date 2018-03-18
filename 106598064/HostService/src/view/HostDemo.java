package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import control.Host;
import control.RunTest;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HostDemo  {

	private JFrame frame;
	private JTable table;	
	private JTextField textField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HostDemo window = new HostDemo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HostDemo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DefaultTableModel model = new DefaultTableModel();
		ArrayList<Host> list=new ArrayList();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 435);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 47, 385, 154);
		frame.getContentPane().add(scrollPane);	
		
		table = new JTable();	
		model.addColumn("HostName");
		model.addColumn("IP");
		model.addColumn("Status");
		model.addColumn("Last Check");
		table.setModel(model);
		table.getColumn("Last Check").setPreferredWidth(120);
		scrollPane.setViewportView(table);
		//蓋便表格顏色
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			             public Component getTableCellRendererComponent(JTable table, Object value,
			                     boolean isSelected, boolean hasFocus, int row, int column) {
			                 Component cell = super.getTableCellRendererComponent  
			                         (table, value, isSelected, hasFocus, row, column);
			                 if(value.equals("OK") ) {
			                	 cell.setBackground(Color.GREEN);
			                	 cell.setForeground(Color.black);
			                 }else {
			                	 cell.setBackground(Color.RED);
			                	 cell.setForeground(Color.white);
			                 }
			                     
			 
			                 return cell;
			             }
		};
		table.getColumn("Status").setCellRenderer(tcr);
		
		textField = new JTextField();
		textField.setBounds(22, 16, 139, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String s=textField.getText();
				if(!s.trim().isEmpty()) {
					RunTest r=new RunTest(list,model);
					r.setHost(s);
					textField.setText("");
					try {
						File f=new File("test.txt");
						FileWriter fw = new FileWriter("test.txt",true);
						if(f.length()==0) {
							fw.write(s);						
						}else {
							fw.write("\r\n"+s);
						}
						fw.flush();
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(213, 14, 87, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s=textField.getText();
				if(!s.trim().isEmpty()) {
					RunTest r=new RunTest(list,model);
					r.deleteHost(s);
					textField.setText("");
				}
				
			}
		});
		btnDelete.setBounds(320, 14, 87, 23);
		frame.getContentPane().add(btnDelete);
		
		
		try {
			FileReader fr = new FileReader("test.txt");
			BufferedReader br = new BufferedReader(fr);
	        String line;
			try {
				while((line = br.readLine())!=null)
				{				
						Host a=new Host(line);
						list.add(a);
						//System.out.print(line);			
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
//		Host a = null;
//		Host b = null;
//		Host c = null;
//		try {
//			a = new Host("www.google.com.tw");
//			list.add(a);
//			b=new Host("www.ntut.edu.tw");
//			list.add(b);
//			c=new Host("123");
//			list.add(c);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//RunTest();
		Timer timer = new Timer();  
	    timer.schedule(new RunTest(list,model),0, 5000);
		
		
	}
}




