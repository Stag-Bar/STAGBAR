package edu.nku.CSC440.stagbar.ui.demo;

import javax.swing.*;
import java.awt.*;

public class UserInfoRenderer extends DefaultListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel result = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		UserInfo userInfo = (UserInfo)value;
		result.setText(userInfo.getFirstName());
		return result;
	}
}
