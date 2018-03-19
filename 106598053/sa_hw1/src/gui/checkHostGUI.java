package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import javaThreading.MyRunnable;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class checkHostGUI extends JFrame {

	public static JPanel contentPane;
	
    public static String[][] data = new String[10][4];
    public static JTable table;
    public int getcurrRow;
    static ScheduledExecutorService exec = Executors.newScheduledThreadPool(4);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					checkHostGUI frame = new checkHostGUI();					
					
					MyRunnable run1 = new MyRunnable("google.com", 0);
					exec.scheduleAtFixedRate(run1, 0, 5, TimeUnit.SECONDS);					
					MyRunnable run2 = new MyRunnable("yahoo.com", 1);		
					exec.scheduleAtFixedRate(run2, 0, 5, TimeUnit.SECONDS);
					
					MyRunnable run3 = new MyRunnable("yahoobbbb.com", 2);		
					exec.scheduleAtFixedRate(run3, 0, 5, TimeUnit.SECONDS);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public checkHostGUI() {
		
		final JFrame frame = new JFrame("Checking hosts");
		 
        String[] columns = {"Host", "IP", "Status", "Check Time"};   
        
        data[0][0] = "google";
        data[1][0] = "yahoo";
        data[2][0] = "yahoobbb";
        getcurrRow = 2;
 
        table = new JTable(data, columns);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial",Font.TRUETYPE_FONT,16));
        table.setRowHeight(50);
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Add host:") );         
        JTextField addhost = new JTextField(20);
        addhost.setText("enter host");
    	panel.add(addhost);
    	Button hostButton = new Button("enter");
    	panel.add( hostButton );

        
        hostButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println( "Click=> " + addhost.getText() );
                data[++getcurrRow][0] = addhost.getText();	
				exec.scheduleAtFixedRate(new MyRunnable(addhost.getText(), getcurrRow), 0, 5, TimeUnit.SECONDS);
                checkHostGUI.table.repaint();
            }
        });
        
        frame.add(panel,BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER); //BorderLayout.CENTER
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setVisible(true);
       
	}

}
