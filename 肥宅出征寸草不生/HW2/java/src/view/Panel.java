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
		frame = new JFrame("Monitor"); // �إߵ����ج[
		panel = new JPanel(); // �إߪ����ө�m����
		label = new JLabel();
		label.setText("�˴���...");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		panel.add(label);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �]�w���U�����k�W�����������sX�A�N�|���������A�õ������ε{��������
		frame.setSize(1000, 1000); // �վ�����j�p�A�_�h�u�|��ܵ��������D�C
		frame.setVisible(true); // ��ܵ���
	}

	public void refresh(String[] headings, String[][] data) {
		panel.removeAll(); // �M�ũҦ��b����������
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
