package com.example.api.imgSelect.app;

import com.alibaba.fastjson.JSON;
import com.example.codeFile.service.CodeFileService;
import com.example.fileConfig.enity.FileSelect;
import com.example.fileConfig.enity.FilePixiv;
import com.example.fileConfig.enity.sauce.SauceData;
import com.example.fileConfig.enity.sauce.SauceNaoMsg;
import com.example.fileConfig.service.fileSelectService;
import com.example.util.*;
import com.example.util.dic.ApiResultImgEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图像识别页
 * @author Enoki
 */
@RestController
public class SelectSetuAppConteroller {

    private final Logger logger = LoggerFactory.getLogger(SelectSetuAppConteroller.class);
    @Resource
    private requestUTF requestUTF;
    @Resource
    private fileSelectService fileSelectService;
    @Value("${assessUrlBlos}")
    private String assessUrlBlos;
    @Value("${asciiHttps}")
    private String asciiHttps;
    @Value("${saucenaoHttps}")
    private String saucenaoHttps;
    @Value("${saucenaoKey}")
    private String saucenaoKey;
    @Value("${tempFile}")
    private String tempFile;

    /*查询saucenao图片*/
    @PostMapping("api/selectSaucenaoImgsApp.act")
    public ResultBody selectSaucenaoImgs(HttpServletRequest request, HttpServletResponse response, @RequestBody FileSelect fileSelect){
        try {
            requestUTF.uTFonE(request,response);
            long start = System.currentTimeMillis();
            if(fileSelect.getFileUrl() == null){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "查询不到图片url");
            }
            File fileIo = new File(tempFile + fileSelect.getFileUrl());
            if(!fileIo.exists() && !fileIo.isDirectory()){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "查询不到图片保存信息");
            }

            String dbs = "dbs[]=" + ApiResultImgEnum.PIXIV.getCode() + "&dbs[]=" +
                            ApiResultImgEnum.DANBOORU.getCode() + "&dbs[]=" + ApiResultImgEnum.YANDERE.getCode();

            //API请求
            String api = saucenaoHttps + "?url=" + fileSelect.getUrl() + "tempFile/" + fileSelect.getFileUrl()
                    + "&db=999&api_key=" + saucenaoKey + "&output_type=2&numres=1&" + dbs;
            String saucenao = sendGetImg(api);

            FilePixiv filePixiv = returnSaucenaoMessage(saucenao,start);
            filePixiv = fileSelectService.get(filePixiv);
            if(!filePixiv.getBlosPicUrl().equals("未查询到图库地址")){
                filePixiv.setBlosImgUrl(assessUrlBlos + "img/" + filePixiv.getBlosPicUrl()+"/"+filePixiv.getBlosPic());
            }
            return new ResultBody(ApiResultEnum.SUCCESS,filePixiv);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
        }
    }

    /*查询图片*/
    @PostMapping("selectImgsApp.act")
    public ResultBody selectImgs(HttpServletRequest request, HttpServletResponse response, @RequestBody FileSelect fileSelect){
        try {
            requestUTF.uTFonE(request,response);
            long start = System.currentTimeMillis();
            if(fileSelect.getFileUrl() == null){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "查询不到图片url");
            }
            File fileIo = new File(tempFile + fileSelect.getFileUrl());
            if(!fileIo.exists() && !fileIo.isDirectory()){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "查询不到图片保存信息");
            }

            //API请求
            String api = asciiHttps + "/search/url/" + fileSelect.getUrl() + "tempFile/" + fileSelect.getFileUrl();
            String colorUrl = sendGetUrl(api);
            return new ResultBody(ApiResultEnum.SUCCESS, returnAsciiMessage(sendGetImg(colorUrl),start));
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
        }
    }

    /**
     * 向指定URL发送GET方法重定向至正确color路径
     * @param url   发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGetUrl(String url) throws Exception {
        trustEveryone();
        HttpGet httpGet = new HttpGet(url);

        //设置类型 "application/x-www-form-urlencoded" "application/json"
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        logger.info("调用URL: " + httpGet.getURI());

        //httpClient实例化
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //执行请求并获取返回
        HttpResponse response = httpClient.execute(httpGet,context);
        logger.info("返回状态码：" + response.getStatusLine());
        HttpHost target = context.getTargetHost();
        List<URI> redirectLocations = context.getRedirectLocations();
        String location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations).toASCIIString();
        httpClient.close();
        return location;
    }

    /**
     * 向指定URL发送GET方法获取图片信息参数
     * @param url   发送请求的URL
     * @return
     */
    public String sendGetImg(String url) throws Exception {

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("content-Type", "application/x-www-form-urlencoded");
        logger.info("调用URL: " + httpGet.getURI());

        //httpClient实例化
        HttpClient httpClient = HttpClients.createDefault();
        // 执行请求并获取返回
        HttpResponse response = httpClient.execute(httpGet);
        logger.info("返回状态码：" + response.getStatusLine());
        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String line = null;
        StringBuffer responseSB = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            responseSB.append(line);
        }
        reader.close();

        return new String(responseSB);
    }

    //-------------------------------------------------------------------------

    public FilePixiv returnSaucenaoMessage(String str, Long startTime){
        //类型转换
        SauceNaoMsg sauceNaoMsg = JSON.parseObject(str,SauceNaoMsg.class);
        FilePixiv filePixiv = new FilePixiv();
        if(sauceNaoMsg.getResults().size()>0){
            //getResults取决于回复多少张图片
            List<SauceData> sauceData = sauceNaoMsg.getResults();
            if(sauceData.size()>0){

                enCapImgSelectType(sauceData , filePixiv, startTime);

                FilePixiv item = new FilePixiv();
                if(sauceData.get(0).getData() != null && sauceData.get(0).getData().getMember_id()!=null){
                    item = fileSelectService.findGetPixiv(sauceData.get(0));
                }
                if(item != null) {
                    filePixiv.setBlosPic(item.getBlosPic());
                    filePixiv.setBlosImgUrl(item.getBlosImgUrl());
                    filePixiv.setBlosPicUrl(item.getBlosPicUrl());
                }
            }
        }
        String ids = rsaKey.uuid(null);
        filePixiv.setIds(ids);
        fileSelectService.insert(filePixiv);
        filePixiv.setIds(ids);
        return filePixiv;
    }

    public FilePixiv returnAsciiMessage(String str, Long startTime){
        FilePixiv filePixiv = new FilePixiv();
        String html = str;
        //解析document元素类型
        Document doc = Jsoup.parse(html);
        //解析找到页面的指定元素
        Elements links = doc.getElementsByClass("item-box");
        if(links.size()==1){
            filePixiv.setImgUrl("https://ascii2d.net/" + links.get(0).select("img[src]").attr("src"));
        }else if(links.size()>=2){
            filePixiv.setImgUrl("https://ascii2d.net/" + links.get(1).select("img[src]").attr("src"));
            filePixiv.setAuthorUrl(links.get(1).getElementsByClass("detail-box").select("a").get(1).attr("href"));
            if(links.get(1).getElementsByClass("detail-box").select("a").size()>1){
                filePixiv.setPicUrl(links.get(1).getElementsByClass("detail-box").select("a").get(0).attr("href"));
            }
            if(links.get(1).select("h6").select("a").size()==1){
                filePixiv.setPic(links.get(1).select("h6").select("a").get(0).text());
            }else if(links.get(1).select("h6").select("a").size()>=2){
                filePixiv.setPic(links.get(1).select("h6").select("a").get(0).text());
                filePixiv.setAuthor(links.get(1).select("h6").select("a").get(1).text());
            }
        }
        long end = System.currentTimeMillis();
        filePixiv.setOrderTime(String.format("本次图片处理用时：%d ms", end - startTime));
        filePixiv.setLocalHost(assessUrlBlos);
        filePixiv.setCreateDate(filePixiv.getNowDate(""));
        String ids = rsaKey.uuid(null);
        filePixiv.setIds(ids);
        fileSelectService.insert(filePixiv);
        filePixiv.setIds(ids);
        return filePixiv;
    }

    private FilePixiv enCapImgSelectType(List<SauceData> sauceData, FilePixiv filePixiv, Long startTime){

        filePixiv.setImgUrl(sauceData.get(0).getHeader().getThumbnail());
        filePixiv.setAuthorUrl(sauceData.get(0).getData().getExt_urls().get(0));
        filePixiv.setPicUrl(sauceData.get(0).getData().getExt_urls().stream().collect(Collectors.joining(";<br>")));
        filePixiv.setLikeImg(sauceData.get(0).getHeader().getSimilarity() + "%");
        filePixiv.setCreateDate(filePixiv.getNowDate(""));
        if(sauceData.get(0).getHeader().getIndex_id() == ApiResultImgEnum.PIXIV.getCode()){
            filePixiv.setAuthor(sauceData.get(0).getData().getMember_name());
            filePixiv.setPic(sauceData.get(0).getData().getTitle());
        }else if(sauceData.get(0).getHeader().getIndex_id() == ApiResultImgEnum.DANBOORU.getCode()){
            filePixiv.setAuthor(sauceData.get(0).getData().getDanbooru_id());
            filePixiv.setPic(sauceData.get(0).getData().getMaterial());
        }else if(sauceData.get(0).getHeader().getIndex_id() == ApiResultImgEnum.YANDERE.getCode()){
            filePixiv.setAuthor(sauceData.get(0).getData().getYandere_id());
            filePixiv.setPic(sauceData.get(0).getData().getMaterial());
        }
        filePixiv.setOrderTime(String.format("\n本次图片处理用时：%d ms", System.currentTimeMillis() - startTime));
        return filePixiv;
    }

    public void trustEveryone(){
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLSv1");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
