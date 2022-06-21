package com.java.pos.order;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;
import com.java.pos.db.OrderDetailDTO;
import com.java.pos.db.ProductDAO;

public class OrderDetail extends JFrame {
	OrderDAO oDAO = new OrderDAO();
	ArrayList<OrderDetailDTO> orderDetails = new ArrayList<OrderDetailDTO>();
	String[] colName = { "주문물건", "개수", "가격" };
	Object[] row = new Object[3];
	JButton btnClose;

	public OrderDetail(String orderId) {
		DefaultTableModel tModel = new DefaultTableModel(colName, 0) {
			// 테이블값 수정 불가
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		JTable table = new JTable(tModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 1, 3, 3));
		add(southPanel, BorderLayout.SOUTH);

		orderDetails = oDAO.orderDetailList(orderId);
		for (int i = 0; i < orderDetails.size(); i++) {
			DecimalFormat decFormat = new DecimalFormat("###,###");
			ProductDAO pDAO = new ProductDAO();
			OrderDetailDTO dto = orderDetails.get(i);
			String pName = pDAO.getProductDetail(dto.getP_id());
			row[0] = pName;
			row[1] = dto.getQuatity();
			row[2] = String.valueOf(dto.getPrice() * dto.getQuatity()) + "원";

			tModel.addRow(row);
		}

		JPanel jp = new JPanel();
		btnClose = new JButton("닫기");
		jp.add(btnClose);

		southPanel.add(jp);

		setTitle("주문상세정보");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
	}
}
