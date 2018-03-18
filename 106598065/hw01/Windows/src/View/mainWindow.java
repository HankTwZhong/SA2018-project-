package View;

import java.awt.EventQueue;
import java.awt.MenuItem;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Control.CheckHost;
import Control.Host;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;

public class mainWindow {

	private JFrame frame;
	private JTable table;
	private JTextField txtDomainNameOr;
	private JScrollPane scrollPane;
	private Timer timer;
	private Timer systemtimer;
	private ArrayList<Host> checkHostlist;
	private DefaultTableModel tablemodel;
	private CheckHost m;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
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
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		checkHostlist = new ArrayList();
		tablemodel = new DefaultTableModel();
		m = new CheckHost(checkHostlist, tablemodel);
		timer = new Timer();
		systemtimer = new Timer();
		frame = new JFrame();
		frame.setBounds(100, 100, 617, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtDomainNameOr = new JTextField();
		txtDomainNameOr.setBounds(54, 43, 114, 21);
		frame.getContentPane().add(txtDomainNameOr);
		txtDomainNameOr.setColumns(10);
		
		JButton btnNewButton = new JButton("\u65B0\u589E");
		btnNewButton.addActionListener(new ActionListener() { //add new host
			public void actionPerformed(ActionEvent e) {
				String _host = txtDomainNameOr.getText();
				try {
					m.setNewHost(_host);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtDomainNameOr.setText("");
			}
		});
		btnNewButton.setBounds(178, 42, 87, 23);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 94, 510, 264);
		frame.getContentPane().add(scrollPane);	


		JTable t = new JTable();
		tablemodel.addColumn("Host Name");
		tablemodel.addColumn("IP");
		tablemodel.addColumn("Last Check");
		tablemodel.addColumn("Status");
		t.setModel(tablemodel);
		scrollPane.setViewportView(t);
		
		JButton btnNewButton_1 = new JButton("\u522A\u9664");
		btnNewButton_1.addActionListener(new ActionListener() { //add new host
			public void actionPerformed(ActionEvent e) {
				String _host = txtDomainNameOr.getText();
				try {
					m.deleteHost(_host);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtDomainNameOr.setText("");
			}
		});
		btnNewButton_1.setBounds(275, 42, 87, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			@Override
				public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				m.setCheckfrequency((int)spinner.getValue()*1000);
				System.out.println((int)spinner.getValue()*1000);
				timer.cancel();
				timer = new Timer();
				timer.schedule(new CheckHost(checkHostlist,tablemodel),0, m.getCheckfrequency());
				}
			
	        }
	    );
		
		spinner.setModel(new SpinnerNumberModel(new Integer(3), new Integer(0), null, new Integer(1)));
		spinner.setToolTipText("\u983B\u7387(sec.)");
		spinner.setBounds(510, 43, 29, 22);
		frame.getContentPane().add(spinner);
			
		JLabel lblNewLabel = new JLabel(setTime());
		lblNewLabel.setBounds(20, 10, 114, 21);
		lblNewLabel.setText(setTime());
		
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblF = new JLabel("period:");
		lblF.setBounds(454, 46, 46, 15);
		frame.getContentPane().add(lblF);
		
		JLabel lblDomainName = new JLabel("Host");
		lblDomainName.setBounds(20, 46, 87, 15);
		frame.getContentPane().add(lblDomainName);
		
		readFile(checkHostlist, m); //建立表格
		System.out.println(m.getCheckfrequency());
	    timer.schedule(new CheckHost(checkHostlist,tablemodel),100, m.getCheckfrequency());
	    //3秒後執行mainCheck
	}
	
	private void readFile(ArrayList<Host> checkHostlist, CheckHost m) {
		System.out.println("read file !!!!!!!!!");
		m.readFilteHost(checkHostlist);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private String setTime() {
//		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String _Sdate = sdFormat.format(date);
		return _Sdate;
	}
}


