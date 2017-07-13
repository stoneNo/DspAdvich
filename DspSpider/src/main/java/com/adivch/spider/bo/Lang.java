package com.adivch.spider.bo;

import java.io.Serializable;

public class Lang implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String  name;
    private String rulDescription="  ";
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(name=="en"){			
	            rulDescription = "很好！您在HTML中声明的语言是en。";
			}			
			else{
				rulDescription ="您在HTML中声明的语言是"+name;
			}	
	}
	public String getRulDescription() {
		return rulDescription;
	}
	public void setRulDescription(String rulDescription) {
		this.rulDescription = rulDescription;
	}
    
    
    
}
