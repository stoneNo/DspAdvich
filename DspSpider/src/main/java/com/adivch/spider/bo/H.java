package com.adivch.spider.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class H implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private String  name;
	
	private ArrayList<String> content;
	
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getContent() {
		
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}


    
}
