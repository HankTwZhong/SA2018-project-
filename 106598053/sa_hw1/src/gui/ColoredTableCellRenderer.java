package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColoredTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		cellComponent.setBackground(Color.white);
		if (table.getValueAt(row, column) != null) {
			if (table.getValueAt(row, column).equals("UP")) {
				cellComponent.setBackground(Color.GREEN);
			} else if (table.getValueAt(row, column).equals("DOWN")) {
				cellComponent.setBackground(Color.RED);
			}
		}
		return cellComponent;
	}
}
