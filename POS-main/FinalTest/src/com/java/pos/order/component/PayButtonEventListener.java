package com.java.pos.order.component;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.java.pos.account.UserThreadLocal;
import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;
import com.java.pos.db.OrderDetailDTO;
import com.java.pos.db.PayType;

public class PayButtonEventListener implements ActionListener {
	private JTable table;
	private String confirmMessage;
	private PayType payType;
	private JTextField tf;
	
	public PayButtonEventListener(JTable table, String confirmMessage, PayType payType, JTextField tf) {
		this.table = table;
		this.confirmMessage = confirmMessage;
		this.payType = payType;
		this.tf = tf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (table.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "상품을 선택해주세요.", "결제", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int totalPrice = 0;
		List<OrderDetailDTO> detailDTOs = new ArrayList<>();
		for (int i = 0; i < table.getRowCount(); i++) {
			int price = Integer.parseInt(table.getValueAt(i, 2).toString().replaceAll(",", "").replace("원", ""));
			int quantity = Integer.parseInt(table.getValueAt(i, 3).toString().replace("개", ""));
			int productId = Integer.parseInt(table.getValueAt(i, 0).toString());

			totalPrice += price * quantity;
			detailDTOs.add(new OrderDetailDTO(productId, quantity, price));
		}
		
		if(showConfirmDialog(null, confirmMessage, "결제", JOptionPane.YES_NO_OPTION) == NO_OPTION) {
			return;
		}
		OrderDTO orders = new OrderDTO(payType, UserThreadLocal.get(), totalPrice, detailDTOs);
		
		OrderDAO orderDao = new OrderDAO();
		orderDao.save(orders);

		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		while (dm.getRowCount() > 0) {
			dm.removeRow(0);
			tf.setText("");
		}
		showMessageDialog(null, "결제 완료", "결제가 완료되었습니다.", JOptionPane.DEFAULT_OPTION);
	}

}
