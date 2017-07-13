package com.adivch.spider.bo;

import java.io.Serializable;

public class ImageDescription implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int srcTotal;
	
	public int altTotal;
	
    private String srcRulDescription="  ";
    
    private String altRulDescription="  ";



	public int getSrcTotal() {
		return srcTotal;
	}

	public void setSrcTotal(int srcTotal) {
		this.srcTotal = srcTotal;						
			srcRulDescription = "我们在该网页找到"+srcTotal+"张图片";	
	}

	public int getAltTotal() {
		return altTotal;
	}

	public void setAltTotal(int altTotal) {
		this.altTotal = altTotal;
		int k=srcTotal-altTotal;
		
		if(altTotal<=srcTotal){					
			altRulDescription ="很遗憾，"+k +"张图片的alt属性未设置。建议添加图片的Alt属性，以助于搜索引擎对于图片内容的解读。";
		}else if(srcTotal==altTotal && srcTotal>0){
			altRulDescription="很好，所有图片均设有Alt属性。";
		}else{
			altRulDescription="";
			}	
		
	}

	public String getSrcRulDescription() {
		return srcRulDescription;
	}

	public void setSrcRulDescription(String srcRulDescription) {
		this.srcRulDescription = srcRulDescription;
	}

	public String getAltRulDescription() {
		return altRulDescription;
	}

	public void setAltRulDescription(String altRulDescription) {
		this.altRulDescription = altRulDescription;
	}

	
	
	
}
