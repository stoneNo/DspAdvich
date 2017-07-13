package com.adivch.spider.bo;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class SeoAnalysisResult implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
     int sorce;
     
     String uuid;

	public int getSorce() {
		return sorce;
	}

	public void setSorce(int sorce) {
		this.sorce = sorce;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
     
	 
	 
}
