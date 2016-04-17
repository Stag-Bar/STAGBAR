package edu.nku.CSC440.stagbar.ui.report;

import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = {
			"Type", "Alcohol",
			"Bottles - Previous", "Amount - Previous",
			"Bottles - Current", "Amount - Current",
			"Sales", "Deliveries",
			"Bottles - Discrepancy", "Amount - Discrepancy"
	};

	@Override
	public int getColumnCount() {
		return 0;
	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}
