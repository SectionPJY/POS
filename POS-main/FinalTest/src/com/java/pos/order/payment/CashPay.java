package com.java.pos.order.payment;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CashPay extends JFrame {
	JLabel totalLabel, changeLabel;
	JTextField cashField;
	JButton btnOk, btnCancel;
	DecimalFormat decFormat = new DecimalFormat("###,###");

	public CashPay(long totalPrice, String[] orderMenu, int[] orderPrice, int[] quantity, String uId) {
		initGrid();

		// total
		initTotalPrice(totalPrice);

		// 받은 금액
		initTextField();

		// 버튼
		initButton();

		setTitle("현금결제");
		setSize(400, 200);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Button Action
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Integer.parseInt(cashField.getText()) >= totalPrice) {
					String pay = (decFormat.format(Long.parseLong(cashField.getText())) + "원");
					new CashPayChange(totalPrice, pay, orderMenu, orderPrice, quantity, uId);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "받은 돈을 다시 입력해주세요.", "현금결제", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void initGrid() {
		Container ct = getContentPane();
		ct.setLayout(new GridLayout(4, 1, 1, 1));
	}

	public void initTotalPrice(long totalPrice) {
		JPanel jp = new JPanel();
		String total = (decFormat.format(totalPrice) + "원");
		totalLabel = new JLabel(total);
		jp.add(totalLabel);
		add(jp);
	}

	public void initTextField() {
		JPanel jp = new JPanel();
		cashField = new JTextField(20);
		cashField.setHorizontalAlignment(JTextField.CENTER);
		jp.add(cashField);
		add(jp);
	}

	public void initButton() {
		JPanel jp = new JPanel();
		btnOk = new JButton("결제");
		btnCancel = new JButton("취소");
		jp.add(btnOk);
		jp.add(btnCancel);
		add(jp);
	}
}