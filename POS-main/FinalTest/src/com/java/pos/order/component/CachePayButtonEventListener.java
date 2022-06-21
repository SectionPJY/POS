package com.java.pos.order.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CachePayButtonEventListener implements ActionListener{
	private ActionListener actionListener;
	
	public CachePayButtonEventListener(ActionListener payButtonEventListener) {
		this.actionListener = payButtonEventListener;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// 선행작업이 진행되고
		
		// 실질적인 동작
		actionListener.actionPerformed(arg0);
		
		// 후처리
	}

}
