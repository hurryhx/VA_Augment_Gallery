package com.gp.server.domain;

public class Login {
	private String id;
	private String name;
	private String type;
	public Login(String a, String b, String c){
		setId(a); setName(b); setType(c);
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
