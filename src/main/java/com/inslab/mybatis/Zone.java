package com.inslab.mybatis;

/**
 * Zone 정보를 저장할 모델 클래스
 */
public class Zone {

	private int id;
	private String name;
	private String master;
	private int last_check;
	private String type;
	private int notified_serial;
	private String account;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public int getLast_check() {
		return last_check;
	}
	public void setLast_check(int last_check) {
		this.last_check = last_check;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNotified_serial() {
		return notified_serial;
	}
	public void setNotified_serial(int notified_serial) {
		this.notified_serial = notified_serial;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}
