package com.example.api.fictionApi;

import com.example.fileConfig.enity.fiction.FictionBook;
import com.example.fileConfig.enity.fiction.FictionFile;
import com.example.fileConfig.enity.fiction.FictionForDetilVo;
import com.example.fileConfig.enity.fiction.FictionSelect;
import com.example.fileConfig.service.FictionFileService;
import com.example.fileConfig.service.FictionUtilService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageInfo;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 小说对外查看页
 * @author Enoki
 */
@RestController
public class FictionMainConteroller {

    private static final Logger log = Logger.getLogger(FictionMainConteroller.class);

    @Resource
    private FictionFileService fictionFileService;
    @Resource
    private FictionUtilService fictionUtilService;

    /**
     * 小说查询
     */
    @Log(title = "小说瀑布流查询（公开）", type = LogEnum.SELECT)
    @PostMapping("open/fictionFileNoPage.act")
    public ResultBody fictionFileNoPage(@RequestBody FictionSelect fictionSelect){
        try{
            FictionFile fictionFile = new FictionFile();
            if(fictionSelect.getChineseName() != null) {
                fictionFile.setChineseName(fictionSelect.getChineseName());
                fictionFile.setChineseNameFt(ChineseHelper.convertToTraditionalChinese(fictionSelect.getChineseName()));
            }
            fictionFile.setIsEnd(fictionSelect.getIsEnd());
            fictionFile.pubImplPage(fictionSelect.getNowTab(), fictionSelect.getHasTab());
            List<FictionFile> listSize = fictionFileService.selectFictionFileTab(fictionFile);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize , (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /*查询很多很多的小说TAG字段*/
    @Log(title = "获取小说标签（公开）", type = LogEnum.SELECT)
    @PostMapping("open/fictionTagAllPage.act")
    public ResultBody fictionPage(){
        try{
            List<DicVo> item = fictionUtilService.selectFileUtilDicNoTab();
            return new ResultBody(ApiResultEnum.SUCCESS, item);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*小说详细信息*/
    @Log(title = "获取小说详情（公开）", type = LogEnum.SELECT)
    @PostMapping("open/fictionDetial.act")
    public ResultBody fictionDetial(@RequestBody FictionBook fictionBook){
        try{
            if(fictionBook.getIds() == null || fictionBook.getIds().equals("")){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到书籍标签，链接错误！");
            }
            FictionFile fictionFile = new FictionFile();

            fictionFile.setIds(fictionBook.getIds());
            fictionFile = fictionFileService.get(fictionFile);
            if(fictionFile != null){
                fictionFile.setIllustrationUrl("/fictionImg/" + fictionFile.getIllustrationUrl());
                if(fictionFile.getTagId() == null || fictionFile.getTagName() == null ||(
                        fictionFile.getTagId().split(",").length != fictionFile.getTagName().split(",").length)){
                    fictionFile.setTagVo(new ArrayList<>());
                }else {
                    List<DicVo> array = new ArrayList<>();
                    for (int a=0;a< fictionFile.getTagId().split(",").length;a++) {
                        DicVo vo = DicVo.builder().dicName(fictionFile.getTagName().split(",")[a])
                                .dicValue(fictionFile.getTagId().split(",")[a]).build();
                        array.add(vo);
                        fictionFile.setTagVo(array);
                    }
                }
                fictionBook.setFictionId(fictionFile.getIds());
                List<FictionBook> list = fictionFileService.selectFictionMainTab(fictionBook);
                FictionForDetilVo fictionForDetilVo = new FictionForDetilVo();
                fictionForDetilVo.setHasDetil(fictionFile);
                fictionForDetilVo.setBook(list);
                return new ResultBody(ApiResultEnum.SUCCESS, fictionForDetilVo);
            }else{
                return new ResultBody(ApiResultEnum.DATA_ERROR, "找不到小说书籍记录，请联系管理员");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*查询小说URL*/
    @Log(title = "获取小说书籍URL（公开）", type = LogEnum.SELECT)
    @PostMapping("open/fictionUrlForEpub.act")
    public ResultBody fictionUrlForEpub(@RequestBody FictionBook fictionBook){
        try {
            fictionBook = fictionFileService.getFictionBook(fictionBook);
            return new ResultBody(ApiResultEnum.SUCCESS, fictionBook);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
