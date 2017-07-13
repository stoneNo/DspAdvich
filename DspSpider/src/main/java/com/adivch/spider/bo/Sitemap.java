package com.adivch.spider.bo;

import java.io.Serializable;

public class Sitemap implements Serializable{
	
	private static final long serialVersionUID = 1L; 
	private String  SitemapHtmlUrl;
	private String  SitemapXmlUrl;
	
	
	public String getSitemapHtmlUrl() {
		return SitemapHtmlUrl;
	}
	public void setSitemapHtmlUrl(String sitemapHtmlUrl) {
		SitemapHtmlUrl = "https://www."+sitemapHtmlUrl+"/sitemap.html";
	}
	public String getSitemapXmlUrl() {
		return SitemapXmlUrl;
	}
	public void setSitemapXmlUrl(String sitemapXmlUrl) {
		SitemapXmlUrl = "https://www."+sitemapXmlUrl+"/sitemap.xml";
	}
	
	
	
	
	
}
