package com.example.fileConfig.web;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.fileConfig.enity.ehentai.GetEhentaiVo;
import com.example.fileConfig.enity.pixivEnity.PixivHasUrl;
import com.example.fileConfig.service.EhentaiService;
import com.example.fileConfig.service.FileLookService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 本子控制页
 * @author Enoki
 */
@RestController
public class EhentaiConteroller {

    private static final Logger log = Logger.getLogger(EhentaiConteroller.class);

    @Resource
    private EhentaiService ehentaiService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取本子列表
     */
    @Log(title = "获取本子列表", type = LogEnum.SELECT)
    @PostMapping("api/selectEhentaiFile.act")
    public ResultBody selectEhentaiFile(@RequestBody GetEhentaiVo vo){
        try{
            vo.pubImplPage(vo.getNowTab(),vo.getHasTab());
            List<GetEhentaiVo> itemPath = ehentaiService.selectEhentaiFile(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, itemPath, (int) new PageInfo<>(itemPath).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 更新本子作者名称
     */
    @Log(title = "更新本子作者名称", type = LogEnum.EDIT)
    @PostMapping("api/editEhentaiFile.act")
    public ResultBody editEhentaiFile(@RequestBody GetEhentaiVo vo){
        try{
            ehentaiService.updateEhentaiFile(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, "更新完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 自动同步本子
     */
    @Log(title = "自动同步文件夹中的本子", type = LogEnum.EDIT)
    @PostMapping("api/updateEhentaiFile.act")
    public ResultBody updateEhentaiFile(){
        try{
            log.info("==============开始轮询本子信息================");
            List<GetEhentaiVo> itemPath = ehentaiService.selectEhentaiName();
            log.info("从数据库中获取到" + itemPath.size() + "项文件，进行去重");
            onCallback(itemPath);
            return new ResultBody(ApiResultEnum.SUCCESS, "已放入同步队列中，请稍后查看");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 开启线程
     * @param itemPath
     */
    private void onCallback(List<GetEhentaiVo> itemPath){
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            this.selectDistentEhentai(itemPath);
        });
        executor.shutdown();
    }

    private final static String tempEhentaiUrl = "/tempEhentai";

    private void selectDistentEhentai(List<GetEhentaiVo> itemPath){
        //获取redis中本子的文件存储地址
        String path = redisUtils.getConfig(ConfigDicEnum.accessEhentai.message);
        //根据地址访问地址内的下级缓存地址
        File file = new File(path + tempEhentaiUrl);
        //数据库取到的本子名称
        Map<String, String> dbName = itemPath.stream().collect(Collectors.toMap(GetEhentaiVo::getBookName, GetEhentaiVo::getBookName));
        List<String> systemName = new ArrayList<>();
        if(!file.exists()){
            log.info("【" + path + "】目录不存在，停止轮询");
        }
        //获取缓存中本子的全部名称
        File[] fileList = file.listFiles();
        if(fileList != null){
            //将本子的全部名称保存到systemName
            for(File vo : fileList){
                systemName.add(vo.getName());
            }
            int addNum = 0;
            for(String evo : systemName){
                //存入系统和数据库的文件夹名称
                String fileName = "";
                String authName = "未知作者";
                Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                Matcher matcher = pattern.matcher(evo);
                boolean boo = matcher.find();
                if(boo) {
                    authName = matcher.group(1);
                    fileName = evo.replace(matcher.group(1), "").replace("[]", "");
                }else{
                    fileName = evo;
                }
                if(dbName.get(fileName) == null){
                    addNum++;
                    GetEhentaiVo newEhentai = new GetEhentaiVo();
                    newEhentai.setBookName(fileName);
                    newEhentai.setBookAuthor(authName);
                    //获取这个文件夹下的首张图片
                    File imgSize = new File(path + tempEhentaiUrl + "/" + evo);
                    File newName = new File(path + tempEhentaiUrl + "/" + fileName);
                    boolean editName = imgSize.renameTo(newName);
                    if(editName){
                        log.info("文件夹【" + evo + "】已修改为【" + fileName + "】");
                    }else{
                        log.info("文件夹【" + evo + "】修改失败，指向文件夹【" + fileName + "】");
                    }
                    for(File img : newName.listFiles()){
                        if(img.getName().contains("jpg") || img.getName().contains("jpeg") || img.getName().contains("png")){
                            newEhentai.setBookImage("/" + img.getName());
                            break;
                        }
                    }
                    ehentaiService.insertEhentai(newEhentai);
                    fileCut(newName, new File(path + "/" + fileName));
                }
            }
            log.info("本次更新完成" + addNum + "本，累计数量" + (itemPath.size() + addNum));
        }
    }

    public static void fileCut(File fs,File ft) {
        if(!ft.mkdirs()){boolean createFile = ft.mkdirs();}
        File []file=fs.listFiles();
        for(File x:file) {
            if(x.isDirectory()) {
                fileCut(x,ft);
            }else {
                File f=new File(ft,x.getName());
                x.renameTo(f);
            }
        }
        fs.delete();
    }

}
