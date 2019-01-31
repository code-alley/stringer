package com.inslab.mybatis;


/*
 Key : content  	Value : 191.238.83.94
Key : id  	Value : 10
Key : name  	Value : jdk.codealley.com
Key : domain_id  	Value : 1
Key : prio  	Value : 0
Key : ttl  	Value : 86400
Key : type  	Value : A
 */

/**
 * DNS record 정보를 저장할 모델 클래스
 */
public class Record {

	private int id;
	private int domain_id;
	private String name;
	private String type;
	private String content;
	private int ttl;
	private int prio; 
	private int change_date;
	
	public int getId(){
		return this.id;
	}
	public void setId(int _id){
		this.id = _id;
	}
	public int getDomain_id() {
		return domain_id;
	}
	public void setDomain_id(int domain_id) {
		this.domain_id = domain_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public int getPrio() {
		return prio;
	}
	public void setPrio(int prio) {
		this.prio = prio;
	}
	public int getChange_date() {
		return change_date;
	}
	public void setChange_date(int change_date) {
		this.change_date = change_date;
	}
}
