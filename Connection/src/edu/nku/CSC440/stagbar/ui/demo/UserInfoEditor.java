//package edu.nku.CSC440.stagbar.ui.demo;
//
//import javax.swing.*;
//import javax.swing.plaf.basic.BasicComboBoxEditor;
//
//public class UserInfoEditor extends BasicComboBoxEditor {
//	public UserInfoEditor(ComboBoxEditor origEditor) {
//		super();
//		editor.setBorder(((JComponent)origEditor.getEditorComponent()).getBorder());
//	}
//
//	@Override
//	public void setItem(Object anObject) {
//		if(anObject instanceof UserInfo) {
//			super.setItem(((UserInfo)anObject).getFirstName());
//		}
//		else { super.setItem(anObject); }
//	}
//
//	@Override
//	public Object getItem() {
//		Object superRes = super.getItem();
//		if(superRes instanceof String) {
//			UserInfo result = UserRepository.getInstance().getUserInfo((String)superRes);
//			return result;
//		}
//		return superRes;
//	}
//}
//
