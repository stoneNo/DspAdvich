package com.adivch.spider.bo;

import java.io.Serializable;

public class Code implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String  name;
    private String rulDescription="  ";
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(name!=null){					
		        rulDescription = "很好！您在HTML中声明的字符集名称是"+name;
			}			
			else{
				rulDescription ="很遗憾！您在HTML中没有声明字符集";
				}			
	}
	public String getRulDescription() {
		return rulDescription;
	}
	public void setRulDescription(String rulDescription) {
		this.rulDescription = rulDescription;
	}
    
}
