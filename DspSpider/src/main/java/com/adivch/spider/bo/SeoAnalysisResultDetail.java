package com.adivch.spider.bo;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class SeoAnalysisResultDetail implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Title title;
	public Description description;
	public Keyword keyword;
	public Lang lang;
	public Code code;
	public MobileOptimization mobileOptimization;
	public SmallIcon smallIcon;
	public ImageDescription img;
	private DocumentType documentType;
	private LinkedHashMap<String,H> htabel;
	private String TextProportion;
	private String sitemap;


	public LinkedHashMap<String, H> getHtabel() {
		return htabel;
	}

	public void setHtabel(LinkedHashMap<String, H> htabel) {
		this.htabel = htabel;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}


	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public MobileOptimization getMobileOptimization() {
		return mobileOptimization;
	}

	public void setMobileOptimization(MobileOptimization mobileOptimization) {
		this.mobileOptimization = mobileOptimization;
	}

	public SmallIcon getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(SmallIcon smallIcon) {
		this.smallIcon = smallIcon;
	}

	public ImageDescription getImg() {
		return img;
	}

	public void setImg(ImageDescription img) {
		this.img = img;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getTextProportion() {
		return TextProportion;
	}

	public void setTextProportion(String textProportion) {
		TextProportion = textProportion;
	}

	public String getSitemap() {
		return sitemap;
	}

	public void setSitemap(String sitemap) {
		this.sitemap = sitemap;
	}



	 
	 
}
