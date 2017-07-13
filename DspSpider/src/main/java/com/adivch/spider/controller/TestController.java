package com.adivch.spider.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
public class TestController extends BaseController{

	@Autowired
	RedisService redisService;

	
    @RequestMapping("/sayHello")
//    @ResponseBody
    String home(Map<String, Object> model) {
    	
    	 model.put("time", "20170723");  
         model.put("message", "这是测试的内容。。。");  
         model.put("toUserName", "张三");  
         model.put("fromUserName", "老许");  
         return "/index2"; 
    }
    
    @RequestMapping("/get")
    @ResponseBody
    String getKey() {
        return redisService.get("tianlei2");
    }
    
    @RequestMapping("/set")
    @ResponseBody
    void setKey() {
    	redisService.set("tianlei2", "kkkkkkkkkkkkk",30l);
    }
    
    /**
     * 普通参数传入
     * @param param1
     * @param request
     * @param response
     */
    @RequestMapping("/getJsonResult")
    @ResponseBody
    public void getJsonResult(@RequestParam("p1")String param1,HttpServletRequest request,HttpServletResponse response) 
    {
    	TestStudent testS = new TestStudent();
    	
    	testS.setAddress("南京桥北");
    	testS.setAge("12");
    	testS.setName(param1);
    	
        try {
			writeJSON(response, testS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    /**
     * Json 对象  参数传入
     * @param param1
     * @param request
     * @param response
     */
    @RequestMapping(value ="/getFixJsonResult",method = {RequestMethod.POST})
    @ResponseBody
    public void getFixJsonResult(@RequestBody(required=false) TestStudent testStudent ,HttpServletRequest request,HttpServletResponse response) 
    {
    	
        try {
			writeJSON(response, testStudent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    
    @RequestMapping(value ="/getWebTitle")
    @ResponseBody
    public void getWebTitle(@RequestParam("webUrl")String webUrl,HttpServletRequest request,HttpServletResponse response){
    	// 先下载第一页
                 SeoAnalysisResultDetail seoAnalysisResult = new SeoAnalysisResultDetail();

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
	   					seoAnalysisResult.setSitemap("很好！您的网站包含sitemap.html和sitemap.xml两个文件");
	   				}
	   				else if(resultCode==1){
	   					seoAnalysisResult.setSitemap("很好！您的网站包含sitemap.xml文件");
	   				}
	   				else if(resultCode==2){
	   					seoAnalysisResult.setSitemap("很好！您的网站包含sitemap.html文件");
	   				}
	   				else{
	   					seoAnalysisResult.setSitemap("很遗憾，您的网站没有sitemap.html和sitemap.xml格式的网站地图");
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
    				seoAnalysisResult.setTextProportion(df.format((float)textLenght/htmlLenght));
    				
    				
    				 char buf[]=new char[20]; 
    				 bodyStr.getChars(0,15,buf,0); 
    				 String typeName = new String(buf);
    				 DocumentType documentType = new DocumentType();
    				 documentType.setName(typeName.trim());
    				 seoAnalysisResult.setDocumentType(documentType);
    				 
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
    				seoAnalysisResult.setImg(image);
    				
    				
    				
    				
    				SmallIcon smallIcon = new SmallIcon();
    				smallIcon.setName(properties.getString("linkRel"));
    				seoAnalysisResult.setSmallIcon(smallIcon);
    				
    				MobileOptimization mobileOptimization = new MobileOptimization();
    				mobileOptimization.setName(properties.getString("viewport"));
    				seoAnalysisResult.setMobileOptimization(mobileOptimization);
    				
    				
    				Title title = new  Title();
    				title.setName(properties.getString("title"));  
    				seoAnalysisResult.setTitle(title);
    				
    				
    				Description description = new Description();
    				description.setName(properties.getString("description"));
    				seoAnalysisResult.setDescription(description);
    				
    				Keyword keyword = new Keyword();
    				keyword.setName(properties.getString("keywords"));
    				seoAnalysisResult.setKeyword(keyword);
    				
    				
    				Lang lang = new Lang();
    				lang.setName(properties.getString("lang"));
    				seoAnalysisResult.setLang(lang);			
    				
    				
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
    				
    				seoAnalysisResult.setHtabel(linckedHashMap);
    				
    				
    				Code code =new Code();
    				
    				String charsetSecond = properties.getString("charsetSencond");
    				String charset = properties.getString("charset");
    				
    				if(!StringUtils.isEmpty(charsetSecond)){
    					 code.setName(charsetSecond);
					}
    				if(!StringUtils.isEmpty(charset)){
   					  code.setName(charset);
					}
    				
    				
    				
    				seoAnalysisResult.setCode(code);
    				
    				
    				
    				 try {
    						writeJSON(response,seoAnalysisResult);
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