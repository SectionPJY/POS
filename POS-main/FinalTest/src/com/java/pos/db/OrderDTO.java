package com.java.pos.db;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
	private int o_id;
	private String m_id;
	private long o_price;
	private LocalDateTime o_date;
	private List<OrderDetailDTO> orderDetails;
	private PayType payType;
	
	public OrderDTO(PayType payType, String m_id, long price, List<OrderDetailDTO> orderDetails) {
		this.m_id = m_id;
		this.o_price = price;
		this.orderDetails = orderDetails;
		this.o_date = LocalDateTime.now();
		this.payType = payType;
	}

	public OrderDTO(int o_id, PayType payType, String m_id, long price, List<OrderDetailDTO> orderDetails) {
		this(payType, m_id, price, orderDetails);
		this.o_id = o_id;
	}
	
	public PayType getPayType() {
		return payType;
	}
	
	public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}
	
	public int getO_id() {
		return o_id;
	}

	public void setO_id(int o_id) {
		this.o_id = o_id;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public long getO_price() {
		return o_price;
	}

	public void setO_price(long o_price) {
		this.o_price = o_price;
	}

	public LocalDateTime getO_date() {
		return o_date;
	}

	public void setO_date(LocalDateTime o_date) {
		this.o_date = o_date;
	}
}