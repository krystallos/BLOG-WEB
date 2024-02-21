package com.example.fileConfig.web.app;

import com.example.fileConfig.enity.fiction.*;
import com.example.fileConfig.service.FictionFileService;
import com.example.fileConfig.service.FictionUtilService;
import com.example.nachrichten.web.app.NachrichtenAppConteroller;
import com.example.util.*;
import com.example.util.dic.DicVo;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 小说控制层
 * @author Enoki
 */
@RestController
public class FictionConfigAppConteroller {

    private static final Logger log = Logger.getLogger(FictionConfigAppConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private FictionUtilService fictionUtilService;
    @Resource
    private FictionFileService fictionFileService;
    //能提供给APP端的TAG长度
    private static final int tagSize = 20;
    //首页查询多少标签的小说
    private static final int fictionSize = 1;
    //首页显示这个标签内有多少小说
    private static final int fictionFileSize = 5;
    //首页显示最近更新的小说数量
    private static final int fictionMainSize = 15;

    /**
     * APP标签配置路径查询
     * @param response
     * @param request
     */
    @PostMapping("api/fictionUtilListApp.act")
    public ResultBody fictionUtilListApp(HttpServletResponse response, HttpServletRequest request, FictionTag fictionTag){
        try{
            requestUTF.uTFonE(request,response);
            List<FictionTag> listSize = fictionUtilService.selectFileUtilNoTab(fictionTag);
            List<FictionTag> newListSize = new ArrayList<>();
            for(int a=0;a<tagSize;a++){
                if(listSize.size() == a){
                    break;
                }
                newListSize.add(listSize.get(a));
            }
            return new ResultBody(ApiResultEnum.SUCCESS, newListSize);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
    }

    /**
     * APP首页随机小说查询
     * @param response
     * @param request
     */
    @PostMapping("api/fictionListToMainApp.act")
    public ResultBody fictionListToMainApp(HttpServletResponse response, HttpServletRequest request){
        try{
            requestUTF.uTFonE(request,response);
            FictionForMainVo vo = new FictionForMainVo();
            List<FictionTag> tagSize = fictionUtilService.selectFileUtilToNum(fictionSize + 1);
            Map<Integer , List<FictionFile>> map = new HashMap<>();
            for(int a=0;a<tagSize.size();a++){
                List<FictionFile> newListSize = fictionFileService.selectRecommend(fictionFileSize, tagSize.get(a).getIds());
                map.put(a,newListSize);
            }
            vo.setTagName(tagSize);
            vo.setTagBookFiction(map);
            return new ResultBody(ApiResultEnum.SUCCESS, vo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
    }

    /**
     * APP首页最新更新小说
     * @param response
     * @param request
     */
    @PostMapping("api/fictionDateToMainApp.act")
    public ResultBody fictionDateToMainApp(HttpServletResponse response, HttpServletRequest request){
        try{
            requestUTF.uTFonE(request,response);
            List<FictionFile> newListSize = fictionFileService.selectRecommendForDate(fictionMainSize + 1);
            return new ResultBody(ApiResultEnum.SUCCESS, newListSize);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
    }

    /**
     * APP分页的根据小说标签查询数据
     * @param response
     * @param request
     * @param fictionFile
     */
    @PostMapping("api/fictionFileAppPage.act")
    public ResultBody fictionFileAppPage(HttpServletResponse response,HttpServletRequest request,@RequestBody FictionFile fictionFile){
        try{
            requestUTF.uTFonE(request,response);
            fictionFile.pubImplPage(fictionFile.getNowTab(),fictionFile.getHasTab());
            if(fictionFile.getChineseName() != null) {
                fictionFile.setChineseNameFt(ChineseHelper.convertToTraditionalChinese(fictionFile.getChineseName()));
            }
            int total = fictionFileService.selectAllCountFiction(fictionFile);
            List<FictionFile> listSize = total == 0? new ArrayList<>() : fictionFileService.selectFictionFileTab(fictionFile);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, total);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
    }

    /*小说详细信息*/
    @PostMapping("api/fictionDetialApp.act")
    public ResultBody fictionDetialApp(HttpServletRequest request, HttpServletResponse response,@RequestBody FictionForDetilVo vo){
        try{
            requestUTF.uTFonE(request,response);
            if(vo.getDetialId() == null || vo.getDetialId().equals("")){
                return new ResultBody(ApiResultEnum.FAIL, "找不到数据");
            }
            FictionFile fictionFile = new FictionFile();
            FictionBook fictionBook = new FictionBook();
            FictionForDetilVo forDetilVo = new FictionForDetilVo();
            fictionFile.setIds(vo.getDetialId());
            fictionFile = fictionFileService.get(fictionFile);
            if(fictionFile != null){
                if(fictionFile.getTagId() == null || fictionFile.getTagName() == null ||(
                        fictionFile.getTagId().split(",").length != fictionFile.getTagName().split(",").length)){
                    fictionFile.setTagVo(new ArrayList<>());
                }else {
                    List<DicVo> array = new ArrayList<>();
                    for (int a=0;a< fictionFile.getTagId().split(",").length;a++) {
                        DicVo dic = DicVo.builder().dicName(fictionFile.getTagName().split(",")[a])
                                .dicValue(fictionFile.getTagId().split(",")[a]).build();
                        array.add(dic);
                        fictionFile.setTagVo(array);
                    }
                }
                fictionBook.setFictionId(fictionFile.getIds());
                List<FictionBook> list = fictionFileService.selectFictionMainTab(fictionBook);
                forDetilVo.setHasDetil(fictionFile);
                forDetilVo.setBook(list);
            }else{
                return new ResultBody(ApiResultEnum.FAIL, "找不到数据");
            }

            return new ResultBody(ApiResultEnum.SUCCESS, forDetilVo);
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, "服务连接超时");
    }

}
