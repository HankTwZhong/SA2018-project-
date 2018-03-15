package view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Panel {
	JFrame frame;
	JPanel panel;
	GridBagConstraints constraint;
	JLabel label;
	JTable table;

	public Panel() {
		frame = new JFrame("Monitor"); // 建立視窗框架
		panel = new JPanel(); // 建立版面來放置元件
		panel.setLayout(new GridBagLayout()); // 設定版面位置配置
		constraint = new GridBagConstraints(); // 設定版面的規則
		constraint.fill = GridBagConstraints.VERTICAL;
		label = new JLabel();
		label.setText("檢測中...");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		panel.add(label);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 設定按下視窗右上角的關閉按鈕X，就會關閉視窗，並結束應用程式的執行
		frame.setSize(500, 500); // 調整視窗大小，否則只會顯示視窗的標題列
		frame.setVisible(true); // 顯示視窗
	}

	public void refresh(String[] headings, String[][] data, String time) {
		panel.removeAll(); // 清空所有在版面的元件
		label.setText("檢測時間" + time);
		table = new JTable(data, headings);
		table.setRowHeight(20);
		table.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		addComponent(label, 0, 0);
		addComponent(new JScrollPane(table), 0, 1);
		panel.repaint();
		frame.revalidate();
		frame.repaint();
	}

	private void addComponent(Component component, int x, int y) {
		constraint.gridx = x;
		constraint.gridy = y;
		panel.add(component, constraint);
	}
}
