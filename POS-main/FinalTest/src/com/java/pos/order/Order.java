package com.java.pos.order;

import javax.swing.JFrame;

import com.java.pos.db.AccountDTO;

public class Order extends JFrame {

	public Order(AccountDTO mDTO) {
		setTitle("주문");
		setLocationRelativeTo(null); // 화면 중앙
		setContentPane(new OrderPanel(mDTO, this));
		setResizable(false);
		setSize(950, 600);
		setVisible(true);
	}
}