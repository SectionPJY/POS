package com.java.pos.order.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTable;

import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;

public class PayCancelEventListener implements ActionListener {
	private JTable table;
	private OrderDTO orders;
	private JFrame frame;

	public PayCancelEventListener(JTable table, OrderDTO orders, JFrame frame) {
		this.table = table;
		this.orders = orders;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.dispose();
	}
}
