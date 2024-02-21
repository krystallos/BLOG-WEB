package com.example.api.imgSelect;

import com.alibaba.fastjson.JSON;
import com.example.codeFile.enity.CodeFileEnity;
import com.example.codeFile.service.CodeFileService;
import com.example.fileConfig.enity.FilePixiv;
import com.example.fileConfig.enity.sauce.SauceData;
import com.example.fileConfig.enity.sauce.SauceNaoMsg;
import com.example.fileConfig.service.fileSelectService;
import com.example.util.*;
import com.example.util.annotion.Log;
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
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图像识别页
 * @author Enoki
 */
@RestController
public class SelectSetuConteroller {

    private final Logger logger = Logger.getLogger(SelectSetuConteroller.class);
    @Resource
    private CodeFileService codeFileService;
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

    /*查询saucenao图片*/
    @Log(title = "查询图片Saucenao（公开）", type = LogEnum.SELECT)
    @PostMapping("open/selectSaucenaoImgs.act")
    public ResultBody selectSaucenaoImgs(@RequestBody CodeFileEnity codeFileEnity){
        try {
            long start = System.currentTimeMillis();
            String url = "";
            if(null != codeFileEnity.getCodeType() && "1".equals(codeFileEnity.getCodeType())){
                url += assessUrlBlos + "blosBoot/tempFile/" + codeFileEnity.getPath();
                codeFileEnity = codeFileService.getCodeFile(codeFileEnity);
                if(codeFileEnity==null){
                    return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到文件资源，请重新上传");
                }
            }else{
                url = codeFileEnity.getPath();
            }
            logger.info(url);
            String dbs = "dbs[]=" + ApiResultImgEnum.PIXIV.getCode() + "&dbs[]=" + ApiResultImgEnum.DANBOORU.getCode() + "&dbs[]=" + ApiResultImgEnum.YANDERE.getCode();
            //API请求
            String api = saucenaoHttps + "?url=" + url + "&db=999&api_key=" + saucenaoKey + "&output_type=2&numres=1&" + dbs;
            logger.info(api);
            String saucenao = urlHttpClient(api);
            return new ResultBody(ApiResultEnum.SUCCESS, returnSaucenaoMessage(saucenao,start));
        }catch (Exception e){
            logger.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*查询图片*/
    @Log(title = "查询图片Ascii（公开）", type = LogEnum.SELECT)
    @PostMapping("open/selectImgs.act")
    public ResultBody selectImgs(String baseId){
        try {
            long start = System.currentTimeMillis();
            //清理前缀
            baseId = baseId.substring(baseId.indexOf(",", 1) + 1, baseId.length());
            //base64转换md5后获取本机路径
            CodeFileEnity codeFileEnity = new CodeFileEnity();
            codeFileEnity.setCodeMd5(Md5Config.getMd5(baseId));
            codeFileEnity = codeFileService.getCodeFile(codeFileEnity);
            if(codeFileEnity==null){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到上传文件，请重新上传");
            }

            //API请求
            String api = asciiHttps + "/search/url/http://" + assessUrlBlos + "tempFile/" + codeFileEnity.getPath();
            String colorUrl = sendGetUrl(api);
            return new ResultBody(ApiResultEnum.SUCCESS, returnAsciiMessage(sendGetImg(colorUrl),start));
        }catch (Exception e){
            logger.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 向指定URL发送GET方法重定向至正确color路径
     * @param url   发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGetUrl(String url) throws Exception {
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
        if(sauceNaoMsg == null || sauceNaoMsg.getResults() == null){
            return new FilePixiv();
        }
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
        filePixiv.setOrderTime(String.format("本次图片处理用时：%d ms", System.currentTimeMillis() - startTime));
        return filePixiv;
    }

    public String urlHttpClient(String url) throws IOException {
        String httpText = "";
        BufferedReader in = null;
        InputStreamReader io = null;
        try {
            URL realUrl = new URL(url);
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 7890);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); //http 代理
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection(proxy);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            io = new InputStreamReader(connection.getInputStream(), "UTF-8");
            in = new BufferedReader(io);
            String line;
            while ((line = in.readLine()) != null) {
                httpText += line;
            }
            io.close();
            in.close();
        } catch (Exception e) {
            if(io != null){
                io.close();
            }
            if(in != null){
                in.close();
            }
            return null;
        }
        return httpText;
    }


}
