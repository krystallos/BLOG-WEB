package com.example.fileConfig.web;

import com.example.fileConfig.enity.fiction.FictionTag;
import com.example.fileConfig.service.FictionUtilService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 小说标签编辑控制层
 * @author Enoki
 */
@RestController
public class FictionConfigConteroller {

    private static final Logger log = Logger.getLogger(FictionConfigConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private FictionUtilService fictionUtilService;

    /**
     * 标签配置路径查询
     */
    @Log(title = "小说标签配置列表", type = LogEnum.SELECT)
    @PostMapping("api/fictionUtilList.act")
    public ResultBody fictionUtilList(@RequestBody FictionTag fictionTag){
        try{
            fictionTag.pubImplPage(fictionTag.getNowTab(),fictionTag.getHasTab());
            List<FictionTag> listSize = fictionUtilService.selectFictionUtilTab(fictionTag);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 标签配置路径不带分页查询
     */
    @Log(title = "不分页的小说标签列表", type = LogEnum.SELECT)
    @PostMapping("api/selectFileUtilDicNoTab.act")
    public ResultBody selectFileUtilDicNoTab(){
        try{
            List<DicVo> listSize = fictionUtilService.selectFileUtilDicNoTab();
            return new ResultBody(ApiResultEnum.SUCCESS, listSize);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 获取详情
     * @param fictionTag
     */
    @Log(title = "小说标签详情", type = LogEnum.DETIAL)
    @PostMapping("api/fictionUtilTabInfo.act")
    public ResultBody fictionUtilTabInfo(@RequestBody FictionTag fictionTag){
        fictionTag = fictionUtilService.get(fictionTag);
        fictionTag.setTagType("2");
        return new ResultBody(ApiResultEnum.SUCCESS, fictionTag);
    }

    /**
     * 标签更新
     * @param fictionTag
     */
    @Log(title = "更新小说标签", type = LogEnum.EDIT)
    @PostMapping("api/updateFictionUtil.act")
    public ResultBody updateFictionUtil(@RequestBody FictionTag fictionTag){
        try {
            int count = fictionUtilService.selectCountHaving(fictionTag.getTagName(),fictionTag.getIds());
            if(count != 0){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "标签重复");
            }else {
                fictionTag.getNowDate(null);
                fictionUtilService.updateFictionUtil(fictionTag);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "修改成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 标签删除
     * @param fictionTag
     */
    @Log(title = "删除小说标签", type = LogEnum.DELETE)
    @PostMapping("api/delFictionUtil.act")
    public ResultBody delFictionUtil(@RequestBody FictionTag fictionTag){
        try {
            fictionTag.setDelFlag("1");
            fictionUtilService.delFictionUtil(fictionTag);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 标签新增
     * @param session
     * @param fictionTag
     */
    @Log(title = "新增小说标签", type = LogEnum.INSERT)
    @PostMapping("api/insertFictionUtil.act")
    public ResultBody insertFictionUtil(HttpSession session ,@RequestBody FictionTag fictionTag){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            int count = fictionUtilService.selectCountHaving(fictionTag.getTagName(),null);
            if(count != 0){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "标签重复");
            }else {
                fictionTag.getNowDate(null);
                fictionTag.setCreateId(person.getIds());
                fictionUtilService.insertFictionUtil(fictionTag);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "添加成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.SUCCESS, e.getMessage());
        }
    }

}
