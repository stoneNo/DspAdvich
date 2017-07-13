package com.adivch.spider.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adivch.spider.bo.BodyText;
import com.adivch.spider.bo.Code;
import com.adivch.spider.bo.Description;
import com.adivch.spider.bo.DocumentType;
import com.adivch.spider.bo.H;
import com.adivch.spider.bo.HeadText;
import com.adivch.spider.bo.ImageDescription;
import com.adivch.spider.bo.Keyword;
import com.adivch.spider.bo.Lang;
import com.adivch.spider.bo.MobileOptimization;
import com.adivch.spider.bo.SeoAnalysisResultDetail;
import com.adivch.spider.bo.Sitemap;
import com.adivch.spider.bo.SmallIcon;
import com.adivch.spider.bo.TestStudent;
import com.adivch.spider.bo.Title;
import com.adivch.spider.controller.base.BaseController;
import com.adivch.spider.redis.service.RedisService;

import net.kernal.spiderman.kit.Properties;
import net.kernal.spiderman.worker.download.DownloadWorker;
import net.kernal.spiderman.worker.download.Downloader;
import net.kernal.spiderman.worker.download.impl.HttpClientDownloader;
import net.kernal.spiderman.worker.extract.ExtractResult;
import net.kernal.spiderman.worker.extract.ExtractTask;
import net.kernal.spiderman.worker.extract.ExtractWorker;
import net.kernal.spiderman.worker.extract.extractor.Extractor;
import net.kernal.spiderman.worker.extract.extractor.impl.HtmlCleanerExtractor;
import net.kernal.spiderman.worker.extract.schema.Model;
import net.kernal.spiderman.worker.extract.schema.Page;

/**
 * @author StoneTian
 * 测试类  提供参考
 *2017年7月11日
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/seo")
public class SeoAnalysisController extends BaseController{

	@Autowired
	RedisService redisService;
    
	  @RequestMapping(value ="/getAnalysisResult")
	  @ResponseBody
	  public void getAnalysisResult(@RequestParam("uuid")String uuid,HttpServletRequest request,HttpServletResponse response){
		  
		  SeoAnalysisResultDetail resut = redisService.get(uuid);
		  try {
			writeJSON(response, resut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	
	
    @RequestMapping(value ="/analysisweb")
    @ResponseBody
    public void getWebTitle(@RequestParam("webUrl")String webUrl,HttpServletRequest request,HttpServletResponse response){
    	// 先下载第一页
                 SeoAnalysisResultDetail seoAnalysisResultDetail = new SeoAnalysisResultDetail();

    			final DownloadWorker downloadWorker = new DownloadWorker(new HttpClientDownloader());
    			final String url = webUrl;
    			final Downloader.Request downloadRequest = new Downloader.Request(url);
    			final Downloader.Response downloadResponse = downloadWorker.download(downloadRequest);
    			// 从这里开始计算耗时，前面的是第一页下载不纳入到递归分页耗时计算
    			final long start = System.currentTimeMillis();//使用微秒即可
    			// 解析, 使用HtmlClean解析器
    			final Extractor.Builder builder = HtmlCleanerExtractor.builder();
    			final ExtractTask task = new ExtractTask(downloadResponse);
    			// 页面抽取配置
    			final Page page = new Page("单篇文章内容分页", builder) {
    				public void config(UrlMatchRules rules, Models models) {
    					rules.addRegexRule(".*");
    					Model model = models.addModel("")
//    					// 这两个参数非常重要，全靠它们来递归抽取下一页内容了
    					.set("fieldNameForNextPageUrl", "next")
    					.set("fieldNameForNextPageContent", "content");
    					model.addField("title")// 标题
    					.set("xpath", "//title/text()");
    					 model.addField("description")// 描述
    					.set("xpath", "//head/meta[@name='description']") 
    					.set("attr", "content");
    					 model.addField("keywords")// 关键字
     					.set("xpath", "//head/meta[@name='keywords']") 
     					.set("attr", "content");
    					 model.addField("h1")   //H标签
						.set("xpath", "//h1/text()")
    					 .set("isArray", true);
						model.addField("h2")
						.set("xpath", "//h2/text()")
						.set("isArray", true);
						model.addField("h3")
						.set("xpath", "//h3/text()")
						.set("isArray", true);
						model.addField("h4")
						.set("xpath", "//h4/text()")
						.set("isArray", true);
						model.addField("h5")
						.set("xpath", "//h5/text()")
						.set("isArray", true);
						model.addField("h6")
						.set("xpath", "//h6/text()")
						.set("isArray", true);
						model.addField("lang")  //声明语言
						.set("xpath", "[@lang]")
						 .set("attr", "lang");
					    model.addField("charset") //编码格式1
						.set("xpath", "//head/meta[@charset]")
					    .set("attr", "charset");   
					    model.addField("charsetSencond") //编码格式2
						.set("xpath", "//head/meta[@http-equiv]")
					    .set("attr", "content"); 
					    model.addField("images") //图片
						.set("xpath", "//img")
						.set("isAutoExtractAttrs", true)
						.set("isArray", true);
						model.addField("viewport")// viewport 移动端优化
						.set("xpath", "//head/meta[@name='viewport']")
						.set("attr", "content");
						model.addField("linkRel")
						.set("xpath", "//link[@rel='icon']") 
						.set("attr", "href");
						model.addField("textHead")
						.set("xpath", "head/text()");
						model.addField("textBody")
						.set("xpath", "body/text()");
					    
    				}
    			};
    			
    			// 执行解析				
    			final ExtractWorker worker = new ExtractWorker(Arrays.asList(page), downloadWorker);
    			worker.work(task, (p, result) -> {
    				System.out.println("耗时=============================："+(System.currentTimeMillis() - start)+"ms");
    				
    				ExtractResult  extractResult =(ExtractResult)result;
    				
    				Properties  properties = extractResult.getFields();
   				      String bodyStr = result.getResponseBody(); 
   				      
   				      
	   				
	   				Pattern pattern = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
	   				Matcher matcher = pattern.matcher(url);
	   				matcher.find();
	   				String primaryDomainName = matcher.group();
	   				Sitemap sitemap = new Sitemap();
	   				sitemap.setSitemapHtmlUrl(primaryDomainName);
	   				sitemap.setSitemapXmlUrl(primaryDomainName);
	   				String newSitemapHtml = sitemap.getSitemapHtmlUrl();
	   				String newSitemapXml = sitemap.getSitemapXmlUrl();
	   				List<String> list = new ArrayList<String>();
	   				list.add(newSitemapHtml);
	   				list.add(newSitemapXml);
	   				int resultCode =0;
	   				for(String newSitemapurl : list){
		   			 URL urlMap;
		   			    try {  
		   			    	 urlMap = new URL(newSitemapurl);  
		   			         InputStream in = urlMap.openStream(); 
		   			         resultCode += setSitemapFlag(newSitemapurl);
		   			         System.out.println("连接可用"+ urlMap);  
		   			    } catch (Exception e1) { 
		   			         System.out.println("连接打不开!"); 
		   			    } 
	   				}
	   				if(resultCode==3){
	   					seoAnalysisResultDetail.setSitemap("很好！您的网站包含sitemap.html和sitemap.xml两个文件");
	   				}
	   				else if(resultCode==1){
	   					seoAnalysisResultDetail.setSitemap("很好！您的网站包含sitemap.xml文件");
	   				}
	   				else if(resultCode==2){
	   					seoAnalysisResultDetail.setSitemap("很好！您的网站包含sitemap.html文件");
	   				}
	   				else{
	   					seoAnalysisResultDetail.setSitemap("很遗憾，您的网站没有sitemap.html和sitemap.xml格式的网站地图");
	   				}

    				
	   				
	   				
    				HeadText headText = new HeadText();
    				BodyText bodyText = new BodyText();

    				headText.setName(properties.getString("textHead"));
    				bodyText.setName(properties.getString("textBody"));
    				int headTextLenght = headText.getLenght();
    				int bodyTextLenght =bodyText.getLenght();
    				int textLenght  = bodyTextLenght + headTextLenght;
    				int htmlLenght = bodyStr.replace(" ", "").length();
    				DecimalFormat df=new DecimalFormat("0.00");   				
    				seoAnalysisResultDetail.setTextProportion(df.format((float)textLenght/htmlLenght));
    				
    				
    				 char buf[]=new char[20]; 
    				 bodyStr.getChars(0,15,buf,0); 
    				 String typeName = new String(buf);
    				 DocumentType documentType = new DocumentType();
    				 documentType.setName(typeName.trim());
    				 seoAnalysisResultDetail.setDocumentType(documentType);
    				 
    				ImageDescription image = new ImageDescription();
    				ArrayList<Properties>  images = (ArrayList<Properties>) properties.get("images");
    				AtomicInteger totalSrc =new AtomicInteger();
    				AtomicInteger totalAlt =new AtomicInteger();
    				if(images!=null && !images.isEmpty()){
    					
    					images.forEach( proper ->{
    						String src = (String) proper.get("src");
    						if(!StringUtils.isEmpty(src)){
    							totalSrc.incrementAndGet();
    						}
    						String alt = (String) proper.get("alt");
                            if(!StringUtils.isEmpty(alt)){
                            	totalAlt.incrementAndGet();
    						}
    					});
    				}
    				image.setSrcTotal(totalSrc.get());
    				image.setAltTotal(totalAlt.get());
    				seoAnalysisResultDetail.setImg(image);
    				
    				
    				
    				
    				SmallIcon smallIcon = new SmallIcon();
    				smallIcon.setName(properties.getString("linkRel"));
    				seoAnalysisResultDetail.setSmallIcon(smallIcon);
    				
    				MobileOptimization mobileOptimization = new MobileOptimization();
    				mobileOptimization.setName(properties.getString("viewport"));
    				seoAnalysisResultDetail.setMobileOptimization(mobileOptimization);
    				
    				
    				Title title = new  Title();
    				title.setName(properties.getString("title"));  
    				seoAnalysisResultDetail.setTitle(title);
    				
    				
    				Description description = new Description();
    				description.setName(properties.getString("description"));
    				seoAnalysisResultDetail.setDescription(description);
    				
    				Keyword keyword = new Keyword();
    				keyword.setName(properties.getString("keywords"));
    				seoAnalysisResultDetail.setKeyword(keyword);
    				
    				
    				Lang lang = new Lang();
    				lang.setName(properties.getString("lang"));
    				seoAnalysisResultDetail.setLang(lang);			
    				
    				
    				LinkedHashMap<String,H> linckedHashMap =new LinkedHashMap<String,H>();
    				
    				for (int i =1; i < 7; i++) {
						 H h =new H();
    					 h.setName("h"+i);
    					 Object object =  properties.get(h.getName());
    					 if(object!=null){
    						 h.setContent((ArrayList<String>)object);
    					 }
    					 else{
    						 h.setContent(new ArrayList<String>());
    					 }
    					 h.setSize(h.getContent().size());
    					 linckedHashMap.put("h"+i, h);
					}
    				
    				seoAnalysisResultDetail.setHtabel(linckedHashMap);
    				
    				
    				Code code =new Code();
    				
    				String charsetSecond = properties.getString("charsetSencond");
    				String charset = properties.getString("charset");
    				
    				if(!StringUtils.isEmpty(charsetSecond)){
    					 code.setName(charsetSecond);
					}
    				if(!StringUtils.isEmpty(charset)){
   					  code.setName(charset);
					}
    				
    				
    				
    				seoAnalysisResultDetail.setCode(code);
    				
    				Date date =  new Date();
    				String uuid = webUrl.hashCode()+"@"+date.getTime();
    				redisService.set(uuid, seoAnalysisResultDetail, 600l);
    				
    				 try {
    						writeJSON(response,seoAnalysisResultDetail);
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					} 
    				 				 
    				
    			});
       
    }
 
	private int setSitemapFlag(String newSitemapurl) {
		char format[]=new char[20]; 
		newSitemapurl.getChars(newSitemapurl.length()-3,newSitemapurl.length(),format,0);
		String sitemapSuffixName = new String(format);
		if( sitemapSuffixName.trim().equals("xml")){
			return 1;
		}
		else{
			return 2;
		}
	}
}