package com.adivch.spider;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import net.kernal.spiderman.worker.download.DownloadWorker;
import net.kernal.spiderman.worker.download.Downloader;
import net.kernal.spiderman.worker.download.impl.HttpClientDownloader;

public class TestXpath {

	public static void main(String[] args) {
		
		        // 先下载第一页
				final DownloadWorker downloadWorker = new DownloadWorker(new HttpClientDownloader());
				final String url = "http://adweb.advich.com/adweb/case/already-recommended/";
				final Downloader.Request request = new Downloader.Request(url);
				final Downloader.Response response = downloadWorker.download(request);
			   
				 List<Object> values = new ArrayList<Object>();
				
				
				String html =response.getBodyStr();
			    HtmlCleaner htmlCleaner = new HtmlCleaner();
		        htmlCleaner.getProperties().setTreatDeprecatedTagsAsContent(true);
		        TagNode doc = htmlCleaner.clean(html);
				TagNode mNode = (TagNode) doc;
		        Object[] nodeArray = null;
		        String axPath ="//title";
		        String xpath = axPath.replace("/text()", "");
	        	try {
					nodeArray = mNode.evaluateXPath(xpath);
				} catch (XPatherException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	            for (int i = 0; i < nodeArray.length; i++) {
	                TagNode tagNode = (TagNode) nodeArray[i];
	                if (axPath.endsWith("/text()")) {
	                    values.add(tagNode.getText().toString());
	                    continue;
	                }

                    Object value;
//	                    value = tagNode.getAttributeByName(attr);
                    value = tagNode;
                    values.add(value);
	            }
	        	System.out.println(values);
		
		
	}
}
