package com.gp.server.domain;

public class Listpic {
	private String picid;
	private String name;
	private String posttime;
	private String good;
	public Listpic (String a, String b, String c, String d){
		setPicid(a); 
		setName(b); 
		setPosttime(c); 
		setGood(d);
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
	public String getPosttime() {
		return posttime;
	}
	public void setPosttime(String posttime) {
		this.posttime = posttime;
	}
	public String getGood() {
		return good;
	}
	public void setGood(String good) {
		this.good = good;
	}
}
