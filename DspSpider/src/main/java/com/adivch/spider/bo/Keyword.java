package com.adivch.spider.bo;

import java.io.Serializable;

public class Keyword implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String  name;
    private String rulDescription="  ";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;		
		if(name!=null){			
				rulDescription = "很好！您的页面已设置Meta关键词。";
			}			
			else{
				rulDescription ="很遗憾！您的页面已设置Meta关键词。";
			}
	
	}
	public String getRulDescription() {
		return rulDescription;
	}
	public void setRulDescription(String rulDescription) {
		this.rulDescription = rulDescription;
		
		
	}
    
    
}
