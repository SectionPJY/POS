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

import com.java.pos.db.OrderDAO;

public class CardPay extends JFrame {
	JLabel lb, totalPriceLabel;
	JTextField cardField;
	JButton btnOk, btnCancel;

	public CardPay(long totalPrice, String[] orderMenu, int[] orderPrice, int[] quantity, String uId) {
		for (int i = 0; i < orderMenu.length; i++) {
			System.out.println("CardPay : " + orderMenu[i]);
		}

		// 프레임 생성
		initGrid();

		// Label
		initLabel();

		// totalPrice Label
		initTotalLabel(totalPrice);

		// TextField
		initTextField();

		// Button
		initButton();

		setTitle("카드결제");
		setSize(400, 200);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Button Action
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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

	public void initLabel() {
		JPanel jp1 = new JPanel();
		lb = new JLabel("카드를 입력해주세요");
		jp1.add(lb);
		add(jp1);
	}

	public void initTotalLabel(long totalPrice) {
		JPanel jp2 = new JPanel();
		DecimalFormat decFormat = new DecimalFormat("###,###");
		String tPrice = (decFormat.format(totalPrice) + "원");
		totalPriceLabel = new JLabel(tPrice);
		jp2.add(totalPriceLabel);
		add(jp2);
	}

	public void initTextField() {
		JPanel jp3 = new JPanel();
		cardField = new JTextField(16);
		cardField.setHorizontalAlignment(JTextField.CENTER);
		jp3.add(cardField);
		add(jp3);
	}

	public void initButton() {
		JPanel jp4 = new JPanel();
		btnOk = new JButton("결제");
		btnCancel = new JButton("취소");
		jp4.add(btnOk);
		jp4.add(btnCancel);
		add(jp4);
	}
}