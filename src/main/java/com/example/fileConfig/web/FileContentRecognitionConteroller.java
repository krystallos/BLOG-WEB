package com.example.fileConfig.web;

import com.alibaba.fastjson.JSON;
import com.example.fileConfig.enity.FileConfig;
import com.example.fileConfig.enity.FileUnit;
import com.example.fileConfig.enity.authorEnity.PixivAuthRegion;
import com.example.fileConfig.enity.authorEnity.PixivAuthor;
import com.example.fileConfig.enity.fiction.FictionTag;
import com.example.fileConfig.enity.pixivEnity.PixivCrawler;
import com.example.fileConfig.enity.pixivEnity.PixivHasUrl;
import com.example.fileConfig.enity.pixivEnity.PixivTags;
import com.example.fileConfig.service.FileConfigService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 图片内容处理层
 * @author Enoki
 */
@RestController
public class FileContentRecognitionConteroller {

    private static final Logger log = Logger.getLogger(FileContentRecognitionConteroller.class);

    @Resource
    private FileConfigService fileConfigService;

    /**
     * 图片标签线程
     */
    @Log(title = "更新图片标签", type = LogEnum.EDIT)
    @PostMapping("api/fileTagThread.act")
    public ResultBody fileTagThread(@RequestBody FileConfig voReturn){
        try{
            log.info("==============开始爬取图片标签/作者信息================");
            List<PixivHasUrl> itemPath = fileConfigService.selectGroupAuthPid(voReturn);
            log.info("开始轮询" + itemPath.size() + "位作者的图片");
            onCallback(itemPath);
            return new ResultBody(ApiResultEnum.SUCCESS, "标签处理已放至后台，需要更新" + itemPath.size() + "位作者");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 开启线程
     * @param itemPath
     */
    private void onCallback(List<PixivHasUrl> itemPath){
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            try {
                this.insertPixivTag(itemPath);
            } catch (IOException e) {
                log.error(e);
            }
        });
        executor.shutdown();
    }

    /**
     * 插入作者
     */
    private String insertPixivAuthor(String hasUrl, String notUrl, String itemPath, String isHas) throws IOException{
        String itemAuthG = fileConfigService.selectGroupAuthUid(hasUrl, notUrl, isHas, itemPath);
        Pattern pattern = Pattern.compile("[0-9]*");
        if(pattern.matcher(itemAuthG).matches()){
            String itext = "";
            itext = uidHttpClient(itext, itemAuthG);
            if(itext == null || itext.equals("")){
                log.info("无法找到作者" + itemAuthG);
                return "";
            }
            PixivAuthor body = JSON.parseObject(itext, PixivAuthor.class);
            if(body == null || body.getError()){
                return "";
            }
            if(body.getBody().getRegion() == null || body.getBody().getRegion().getName() == null){
                PixivAuthRegion pr = new PixivAuthRegion();
                pr.setName(pr.getRegionName());
                body.getBody().setRegion(pr);
            }
            String authSysIds = fileConfigService.selectHasAuthor(body.getBody().getUserId());
            if(authSysIds == null){
                fileConfigService.insertAuthor(body);
            }else{
                body.setIds(authSysIds);
                fileConfigService.updateAuthor(body);
            }
            return body.getIds();
        }else{
            log.info("作者格式异常，异常格式为" + itemAuthG);
            return "";
        }
    }

    /**
     * 插入标签
     * @param itemPath
     * @throws IOException
     */
    private void insertPixivTag(List<PixivHasUrl> itemPath) throws IOException{
        int imageSize = 0;
        int iamegErrSize = 0;
        int iamegNoDataSize = 0;
        for(PixivHasUrl item : itemPath){
            String authorIds = "";
            if(item.getIsHas().equals("1")){
                authorIds = insertPixivAuthor("G12/PNG/", "壁纸" ,item.getPath(), item.getIsHas());
            }else{
                authorIds = insertPixivAuthor("R18/PNG/", "gif" ,item.getPath(), item.getIsHas());
            }
            List<FileUnit> imageName = fileConfigService.selectGroupLissImagePid(item.getPath());
            imageSize += imageName.size();
            for(FileUnit imageItem : imageName){
                FileConfig fileConfig = new FileConfig();
                String[] listName = imageItem.getFileName().split("_");
                if(listName.length < 2){
                    log.info("图片格式异常，跳过" + imageItem.getFileName() + "文件");
                    iamegErrSize ++;
                    continue;
                }
                String pid = null;
                if(listName.length == 2){
                    pid = listName[0];
                }else{
                    pid = listName[listName.length-2];
                }

                String itext = "";
                itext = urlHttpClient(itext, pid);
                if(itext == null || itext.equals("")){
                    log.info("作者未开放" + imageItem.getFileName() + "的访问权限");
                    iamegNoDataSize ++;
                    continue;
                }
                PixivCrawler body = JSON.parseObject(itext, PixivCrawler.class);
                if(body == null || body.getError()){
                    continue;
                }
                List<PixivTags> tags = body.getBody().getTags().getTags() == null ? new ArrayList<>() : body.getBody().getTags().getTags();
                List<String> let = tags.stream().map(v->  v.getTranslation() != null ? v.getTranslation().getEn() : v.getTag() ).collect(Collectors.toList());
                fileConfig.setTagNameList(let);
                fileConfig.setIds(imageItem.getIds());
                fileConfigService.insertTag(fileConfig);
                fileConfigService.updateSync(imageItem.getIds(), authorIds);
            }
        }
        log.info("轮询结束");
    }

    public String urlHttpClient(String httpText,String pid) throws IOException {
        BufferedReader in = null;
        InputStreamReader io = null;
        try {
            URL realUrl = new URL("https://www.pixiv.net/ajax/illust/" + pid + "?lang=zh");
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
            io = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
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

    public String uidHttpClient(String httpText,String uid) throws IOException {
        BufferedReader in = null;
        InputStreamReader io = null;
        try {
            URL realUrl = new URL("https://www.pixiv.net/ajax/user/"+ uid +"?full=1&lang=zh");
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
            io = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
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
