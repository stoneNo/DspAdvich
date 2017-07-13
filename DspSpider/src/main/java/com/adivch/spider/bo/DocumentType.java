package com.adivch.spider.bo;

import java.io.Serializable;

public class DocumentType implements Serializable{

	private static final long serialVersionUID = 1L;
	private String  name;
    private String rulDescription="  ";
    
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		 String doc="<!DOCTYPE html>";
		if(name.equals(doc)){			
			rulDescription = "很好！您的网站文档类型采用HTML 5。";
		}			
		else{
			rulDescription ="很遗憾！您的网站文档类型为xml。";
		}
	}
	public String getRulDescription() {
		return rulDescription;
	}
	public void setRulDescription(String rulDescription) {
		this.rulDescription = rulDescription;
	}

    
    
	
}
