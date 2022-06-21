package com.java.pos.order.component;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PayCancelButton extends JButton {

	public PayCancelButton(ActionListener actionListenr) {
		setText("취소");
		addActionListener(actionListenr);
	}
}
