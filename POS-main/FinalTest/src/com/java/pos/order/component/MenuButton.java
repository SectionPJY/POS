package com.java.pos.order.component;

import java.awt.Dimension;

import javax.swing.JButton;

import com.java.pos.db.ProductDTO;
import java.awt.event.ActionListener;

public class MenuButton extends JButton {

	public MenuButton(ProductDTO productDTO, ActionListener event) {
		super(productDTO.getName());
		setPreferredSize(new Dimension(100, 50));
		addActionListener(event);
	}

}
