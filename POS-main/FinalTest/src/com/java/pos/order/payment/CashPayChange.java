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

import com.java.pos.db.OrderDAO;

public class CashPayChange extends JFrame {
	JLabel lTotal, lPay, lChange;
	JLabel totalL, payL, changeL;
	JButton btnOk, btnCancel;
	DecimalFormat decFormat = new DecimalFormat("###,###");

	public CashPayChange(long totalPrice, String pay, String[] orderMenu, int[] orderPrice, int[] quantity,
			String uId) {
		initGrid();

		initTotalLabel(totalPrice);

		initPayLabel(pay);

		initChangeLabel(totalPrice, pay);

		initButton();

		setTitle("현금결제");
		setSize(400, 200);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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

	public void initTotalLabel(long totalPrice) {
		JPanel jp = new JPanel();
		lTotal = new JLabel("합계");
		totalL = new JLabel((decFormat.format(totalPrice) + "원"));
		jp.add(lTotal);
		jp.add(totalL);
		add(jp);
	}

	public void initPayLabel(String pay) {
		JPanel jp = new JPanel();
		lPay = new JLabel("받은돈");
		payL = new JLabel(pay);
		jp.add(lPay);
		jp.add(payL);
		add(jp);
	}

	public void initChangeLabel(long totalPrice, String pay) {
		JPanel jp = new JPanel();
		lChange = new JLabel("거스름돈");
		String stringPay = (pay.toString().replace(",", "").replace("원", ""));
		long change = (Long.parseLong(stringPay)) - totalPrice;
		changeL = new JLabel((decFormat.format(change) + "원"));
		jp.add(lChange);
		jp.add(changeL);
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