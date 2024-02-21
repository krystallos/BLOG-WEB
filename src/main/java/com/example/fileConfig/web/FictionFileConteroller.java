package com.example.fileConfig.web;

import com.example.fileConfig.enity.fiction.FictionBook;
import com.example.fileConfig.enity.fiction.FictionFile;
import com.example.fileConfig.service.FictionFileService;
import com.example.fileConfig.service.FictionUtilService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageInfo;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 小说文件控制层
 * @author Enoki
 */
@RestController
public class FictionFileConteroller {

    private static final Logger log = Logger.getLogger(FictionFileConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private FictionFileService fictionFileService;
    @Resource
    private FictionUtilService fictionUtilService;

    /**
     * 小说查询
     */
    @Log(title = "小说列表查询", type = LogEnum.SELECT)
    @PostMapping("api/fictionFileList.act")
    public ResultBody fictionFileList(@RequestBody FictionFile fictionFile){
        try{
            fictionFile.pubImplPage(fictionFile.getNowTab(),fictionFile.getHasTab());
            if(fictionFile.getChineseName() != null) {
                fictionFile.setChineseNameFt(ChineseHelper.convertToTraditionalChinese(fictionFile.getChineseName()));
            }
            List<FictionFile> listSize = fictionFileService.selectFictionFileTab(fictionFile);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 小说详细信息
     * @param fictionFile
     */
    @Log(title = "小说详细信息", type = LogEnum.DETIAL)
    @PostMapping("api/fictionFileTabInfo.act")
    public ResultBody fictionFileTabInfo(@RequestBody FictionFile fictionFile){
        List<DicVo> array = new ArrayList<>();
        try {
            fictionFile = fictionFileService.get(fictionFile);
            fictionFile.setIllustrationUrl("/fictionImg/" + fictionFile.getIllustrationUrl());
            fictionFile.setFileType("2");

            if (fictionFile.getTagId() == null || fictionFile.getTagName() == null || (
                    fictionFile.getTagId().split(",").length != fictionFile.getTagName().split(",").length)) {
                fictionFile.setTagVo(new ArrayList<>());
            } else {
                for (int a = 0; a < fictionFile.getTagId().split(",").length; a++) {
                    DicVo vo = DicVo.builder().dicName(fictionFile.getTagName().split(",")[a])
                            .dicValue(fictionFile.getTagId().split(",")[a]).build();
                    array.add(vo);
                }
            }
            fictionFile.setTagVo(array);
            fictionFile.setAllTag(fictionUtilService.selectFileUtilDicNoTab());
            return new ResultBody(ApiResultEnum.SUCCESS, fictionFile);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 小说更新
     * @param fictionFile
     */
    @Log(title = "更新小说信息", type = LogEnum.EDIT)
    @PostMapping("api/updateFictionFile.act")
    public ResultBody updateFictionFile(@RequestBody FictionFile fictionFile){
        try {
            if(fictionFile.getChineseName() != null) {
                fictionFile.setChineseNameFt(ChineseHelper.convertToTraditionalChinese(fictionFile.getChineseName()));
            }
            int a = fictionFileService.selectFictionCount(fictionFile.getChineseName(),fictionFile.getChineseNameFt(),fictionFile.getIds());
            if(a != 0){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "该小说已存在");
            }else {
                fictionFile.getNowDate(null);
                fictionFileService.updateFictionFile(fictionFile);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "更新完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 小说删除
     * @param fictionFile
     */
    @Log(title = "删除小说", type = LogEnum.DELETE)
    @PostMapping("api/delFictionFile.act")
    public ResultBody delFictionFile(@RequestBody FictionFile fictionFile){
        try {
            fictionFile.setDelFlag("1");
            fictionFileService.delFictionFile(fictionFile);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.SUCCESS, e.getMessage());
        }
    }

    /**
     * 小说新增
     * @param session
     * @param fictionFile
     */
    @Log(title = "新增小说信息", type = LogEnum.INSERT)
    @PostMapping("api/insertFictionFile.act")
    public ResultBody insertFictionUtil(HttpSession session,@RequestBody FictionFile fictionFile){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            if(fictionFile.getChineseName() != null) {
                fictionFile.setChineseNameFt(ChineseHelper.convertToTraditionalChinese(fictionFile.getChineseName()));
            }
            int a = fictionFileService.selectFictionCount(fictionFile.getChineseName(),fictionFile.getChineseNameFt(),null);
            if(a != 0){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "该小说已存在");
            }else {
                fictionFile.getNowDate(null);
                fictionFile.setCreateId(person.getIds());
                fictionFileService.insertFictionFile(fictionFile);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "新增完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.SUCCESS, e.getMessage());
        }
    }

    /**
     * 书籍查询
     */
    @Log(title = "小说书籍列表查询", type = LogEnum.SELECT)
    @PostMapping("api/fictionFileMainList.act")
    public ResultBody fictionFileMainList(@RequestBody FictionBook fictionBook){
        try{
            fictionBook.pubImplPage(fictionBook.getNowTab(),fictionBook.getHasTab());
            List<FictionBook> listSize = fictionFileService.selectFictionMainTab(fictionBook);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 书籍删除
     * @param fictionBook
     */
    @Log(title = "小说书籍删除", type = LogEnum.DELETE)
    @PostMapping("api/delFictionMain.act")
    public ResultBody delFictionMain(@RequestBody FictionBook fictionBook){
        try {
            fictionBook.setDelFlag("1");
            fictionFileService.delFictionMain(fictionBook);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 书籍封面查看
     * @param fictionFile
     */
    @Log(title = "小说书籍封面查看", type = LogEnum.DETIAL)
    @PostMapping("api/lookBookMainCover.act")
    public ResultBody lookBookMainCover(@RequestBody FictionFile fictionFile){
        try {
            String url = fictionFileService.lookBookMainCover(fictionFile);
            fictionFile.setIllustrationUrl("/fictionImg/" + url);
            return new ResultBody(ApiResultEnum.SUCCESS, fictionFile);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
