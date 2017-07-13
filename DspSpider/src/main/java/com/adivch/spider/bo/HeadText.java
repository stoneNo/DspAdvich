package com.adivch.spider.bo;

import java.io.Serializable;

public class HeadText implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String  name;
	private int  lenght;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		String nameTtr = name.replace(" ", "");
		lenght = nameTtr.length();
	}
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

}
