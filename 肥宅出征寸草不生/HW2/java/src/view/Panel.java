package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Panel {
	JFrame frame;
	JPanel panel;
	JLabel label;
	JTable table;

	public Panel() {
		frame = new JFrame("Monitor"); // 建立視窗框架
		panel = new JPanel(); // 建立版面來放置元件
		label = new JLabel();
		label.setText("檢測中...");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		panel.add(label);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 設定按下視窗右上角的關閉按鈕X，就會關閉視窗，並結束應用程式的執行
		frame.setSize(1000, 1000); // 調整視窗大小，否則只會顯示視窗的標題列
		frame.setVisible(true); // 顯示視窗
	}

	public void refresh(String[] headings, String[][] data) {
		panel.removeAll(); // 清空所有在版面的元件
		table = new JTable(data, headings);
		table.setRowHeight(20);
		table.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		table.setFillsViewportHeight(true);
		JScrollPane jScrollPane = new JScrollPane(table);
		panel.add(jScrollPane, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
		panel.repaint();
		frame.revalidate();
	}
}
