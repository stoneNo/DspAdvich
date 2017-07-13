package com.adivch.spider.bo;

import java.io.Serializable;

public class Description implements Serializable{
	
	    /**
		 * 70至160个
		 */
		private static final long serialVersionUID = 1L;
		private String  name;
	    private String rulDescription="  ";
	    private int length;
	    
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
			
			if(name!=null){
				setLength(name.length());
			   
				if(name.length()< 70){
					rulDescription = "Description标签对应标题下长度应该设置为10至70个字母长度之间，您目前标题太短了";
				}
				else if(name.length()>160){
					rulDescription = "Description标签对应标题下长度应该设置为10至70个字母长度之间,您目前标题过长";
				}
				else{
					rulDescription ="很好！您的Meta描述长度介于70至160个字母长度之间";
				}
			}
		}
		public String getRulDescription() {
			return rulDescription;
		}
		public void setRulDescription(String rulDescription) {
			this.rulDescription = rulDescription;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
	  
	  


}
