package com.java.pos.order;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;
import com.java.pos.db.PayType;

public class OrderListPanel extends JFrame {
	OrderDAO oDAO = new OrderDAO();
	String[] colName = { "주문ID", "주문날짜", "주문자", "주문가격", "주문 타입" };
	Object[] row = new Object[5];
	JButton btnSearch, btnClose;

	public OrderListPanel() {
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

		List<OrderDTO> orders = oDAO.findAll();
		for (int i = 0; i < orders.size(); i++) {
			OrderDTO dto = orders.get(i);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일HH시mm분ss초");
			String strDate = dto.getO_date().format(formatter);
			row[0] = dto.getO_id();
			row[1] = strDate;
			row[2] = dto.getM_id();
			row[3] = dto.getO_price();
			row[4] = dto.getPayType().equals(PayType.CACHE) ? "현금" : "카드";

			tModel.addRow(row);
		}

		JPanel jp = new JPanel();
		btnSearch = new JButton("상세");
		btnClose = new JButton("닫기");
		jp.add(btnSearch);
		jp.add(btnClose);

		southPanel.add(jp);

		setTitle("주문조회");
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String odId = table.getModel().getValueAt(table.getSelectedRow() ,0).toString();
				new OrderDetail(odId);
			}
		});

		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}