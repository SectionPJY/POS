package com.java.pos.order.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.java.pos.db.OrderDetailDTO;
import com.java.pos.db.ProductDTO;

public class MenuButtonEventListener implements ActionListener {
	private JTable targetTable;
	private ProductDTO product;
	private JTextField totalPriceTextField;
	
	public MenuButtonEventListener(ProductDTO product, JTable table, JTextField totalPriceTextField) {
		this.product = product;
		this.targetTable = table;
		this.totalPriceTextField = totalPriceTextField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DecimalFormat decFormat = new DecimalFormat("###,###");

		DefaultTableModel model = (DefaultTableModel) targetTable.getModel();

		// 이미 주문 목록에 있는 상품이라면 개수 1추가
		int idx = getIdxEqualProductIdFromTable();
		if (idx != -1) {
			int count = Integer.parseInt(targetTable.getValueAt(idx, 3).toString().replace("개", ""));
			model.setValueAt((count + 1) + "개", idx, 3);
			
			int totalPrice = getTotalPrice();
			totalPriceTextField.setText(decFormat.format(totalPrice) + "원");
			return;
		}
		model.addRow(new Object[] { product.getId(), product.getName(), decFormat.format(product.getPrice()) + "원", 1 + "개" });
		
		int totalPrice = getTotalPrice();
		totalPriceTextField.setText(decFormat.format(totalPrice) + "원");
	}

	private int getIdxEqualProductIdFromTable() {
		DefaultTableModel model = (DefaultTableModel) targetTable.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			int currentProductId = (int) model.getValueAt(i, 0);
			if (currentProductId == product.getId()) {
				return i;
			}
		}
		return -1;
	}

	private int getTotalPrice() {
		int totalPrice = 0;
		for (int i = 0; i < targetTable.getRowCount(); i++) {
			int price = Integer.parseInt(targetTable.getValueAt(i, 2).toString().replaceAll(",", "").replace("원", ""));
			int quantity = Integer.parseInt(targetTable.getValueAt(i, 3).toString().replace("개", ""));
			totalPrice += price * quantity;
		}
		return totalPrice;
	}
	
}
