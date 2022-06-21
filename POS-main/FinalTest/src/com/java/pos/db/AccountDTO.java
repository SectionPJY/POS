package com.java.pos.db;

import java.time.LocalDateTime;

public class AccountDTO {
	private String id;
	private String pw;
	private String name;
	private String position;
	private LocalDateTime registryDate;
	private LocalDateTime accessDate;

	public AccountDTO(String id, String pw, String name, String position) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.position = position;
	}

	public AccountDTO(String id, String name, LocalDateTime registDate, LocalDateTime lastAccessDate) {
		this.id = id;
		this.name = name;
		this.registryDate = registDate;
		this.accessDate = lastAccessDate;
	}

	public AccountDTO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(LocalDateTime registryDate) {
		this.registryDate = registryDate;
	}

	public LocalDateTime getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(LocalDateTime accessDate) {
		this.accessDate = accessDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
