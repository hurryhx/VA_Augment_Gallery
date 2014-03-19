package com.gp.server.domain;

public class Asso {
	private String picid;
	private String name;
	private String author;
	public Asso(String a, String b, String c){
		setPicid(a); setName(b); setAuthor(c);
	}
	public String getPicid() {
		return picid;
	}
	public void setPicid(String picid) {
		this.picid = picid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
