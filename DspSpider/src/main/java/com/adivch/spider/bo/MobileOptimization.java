package com.adivch.spider.bo;

import java.io.Serializable;

public class MobileOptimization implements Serializable{

	private static final long serialVersionUID = 1L;
	private String  name;
	private String rulDescription="  ";
	
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(name!=null){			
			rulDescription = "包含Meta Viewport标签，利于移动端优化";
		}			
		else{
			rulDescription ="很遗憾！没有包含Meta Viewport标签。";
		}
	}
	public String getRulDescription() {
		return rulDescription;
	}
	public void setRulDescription(String rulDescription) {
		this.rulDescription = rulDescription;
	}
	
}
