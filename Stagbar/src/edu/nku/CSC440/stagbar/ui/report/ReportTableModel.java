package edu.nku.CSC440.stagbar.ui.report;

import edu.nku.CSC440.stagbar.dataaccess.data.ReportItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ReportTableModel extends AbstractTableModel {

	private final ArrayList<ReportItem> data;

	public ReportTableModel(ArrayList<ReportItem> data) {this.data = data;}

	@Override
	public Class getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public String getColumnName(int column) {
		return Column.values()[column].toString();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	private Object getValueAt(int rowIndex, Column column) {
		ReportItem row = data.get(rowIndex);
		Object value = null;
		switch(column) {
			case TYPE:
				value = row.getAlcohol().getType().toString();
				break;
			case ALCOHOL:
				value = row.getAlcohol().toString();
				break;
			case AMT_CURRENT:
				value = row.getAmountCurrent();
				break;
			case AMT_DELIVERED:
				value = row.getAmountDelivered();
				break;
			case AMT_DISCREPANCY:
				value = row.getDiscrepency_Amount();
				break;
			case AMT_PREVIOUS:
				value = row.getAmountPrevious();
				break;
			case AMT_SOLD:
				value = row.getAmountSold();
				break;
			case BTL_CURRENT:
				value = row.getBottlesCurrent();
				break;
			case BTL_DELIVERED:
				value = row.getBottlesDelivered();
				break;
			case BTL_DISCREPANCY:
				value = row.getDiscrepency_Bottles();
				break;
			case BTL_PREVIOUS:
				value = row.getBottlesPrevious();
				break;
			case BTL_SOLD:
				value = row.getBottlesSold();
				break;
		}

		return value;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getValueAt(rowIndex, Column.values()[columnIndex]);
	}

	private enum Column {
		TYPE("Type"), ALCOHOL("Alcohol"),
		BTL_PREVIOUS("Bottles - Previous"), AMT_PREVIOUS("Amount - Previous"),
		BTL_CURRENT("Bottles - Current"), AMT_CURRENT("Amount - Current"),
		BTL_SOLD("Bottles - Sold"), AMT_SOLD("Amount - Sold"),
		BTL_DELIVERED("Bottles - Delivered"), AMT_DELIVERED("Amount - Delivered"),
		BTL_DISCREPANCY("Bottles - Discrepancy"), AMT_DISCREPANCY("Amount - Discrepancy");

		private final String columnName;

		Column(String columnName) {
			this.columnName = columnName;
		}

		@Override
		public String toString() {
			return columnName;
		}
	}
}
