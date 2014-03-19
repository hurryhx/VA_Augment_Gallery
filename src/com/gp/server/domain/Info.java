package com.gp.server.domain;


public class Info {
	private String intro;
	private Integer id;
	public Info(String a,Integer b)
	{
		intro=a;
		id=b;
	}
	public String getintro()
	{
		return intro;
	}
	public void setintro(String a)
	{
		intro=a;
	}
	public Integer getid()
	{
		return id;
	}
	public void setid(Integer a)
	{
		id=a;
	}
}
