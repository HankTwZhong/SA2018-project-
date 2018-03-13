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
		frame = new JFrame("Monitor"); // �إߵ����ج[
		panel = new JPanel(); // �إߪ����ө�m����
		panel.setLayout(new GridBagLayout()); // �]�w������m�t�m
		constraint = new GridBagConstraints(); // �]�w�������W�h
		constraint.fill = GridBagConstraints.VERTICAL;
		label = new JLabel();
		label.setText("�˴���...");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		panel.add(label);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �]�w���U�����k�W�����������sX�A�N�|���������A�õ������ε{��������
		frame.setSize(500, 500); // �վ�����j�p�A�_�h�u�|��ܵ��������D�C
		frame.setVisible(true); // ��ܵ���
	}

	public void refresh(String[] headings, String[][] data, String time) {
		panel.removeAll(); // �M�ũҦ��b����������
		label.setText("�˴��ɶ�" + time);
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
