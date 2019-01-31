package com.inslab.stringer;

/**
 * Host 정보를 저장하는 모델 클래스
 */
public class Host {

	private String ip;
	private int port;
	
	public Host(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
