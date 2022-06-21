package com.java.pos.db;

public class OrderDetailDTO {
	private int od_id;
	private int o_id;
	private int p_id;
	private int quatity;
	private int price;

	public OrderDetailDTO(int p_id, int quatity, int price) {
		this.p_id = p_id;
		this.quatity = quatity;
		this.price = price;
	}
	
	public OrderDetailDTO(int od_id, int o_id, int p_id, int quatity, int price) {
		this(p_id, quatity, price);
		this.od_id = od_id;
		this.o_id = o_id;
	}

	public int getPrice() {
		return price;
	}
	
	public int getO_id() {
		return o_id;
	}

	public void setO_id(int o_id) {
		this.o_id = o_id;
	}

	public int getOd_id() {
		return od_id;
	}

	public void setOd_id(int od_id) {
		this.od_id = od_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getQuatity() {
		return quatity;
	}

	public void setQuatity(int quatity) {
		this.quatity = quatity;
	}
}