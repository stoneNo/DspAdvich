package com.adivch.spider.bo;

import java.io.Serializable;

public class SmallIcon implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String  name;
    private String rulDescription="  ";
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(name!=null){			
			rulDescription = "很好！您的网站已有一个网站小图标。";
		}			
		else{
			rulDescription ="很遗憾！您的网站没有小图标。";
		}
	}
	public String getRulDescription() {
		return rulDescription;
	}
	public void setRulDescription(String rulDescription) {
		this.rulDescription = rulDescription;
	}
    
    
    
}
