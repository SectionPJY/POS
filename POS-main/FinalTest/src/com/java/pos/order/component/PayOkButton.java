package com.java.pos.order.component;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PayOkButton extends JButton {

	public PayOkButton(ActionListener actionListener) {
		setText("결제");
		addActionListener(actionListener);
	}

}
