package com.example.fileConfig.web;

import com.example.fileConfig.enity.FileUtil;
import com.example.fileConfig.service.FileUtilService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *文件设置页
 * @author Enoki
 */
@RestController
public class FileUtilConteroller {

    private static final Logger log = Logger.getLogger(FileUtilConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private FileUtilService fileUtilService;


    /**
     * 详情
     * @param fileUtil
     */
    @Log(title = "获取文件配置路径详情", type = LogEnum.DETIAL)
    @PostMapping("api/fileUtilInfo.act")
    public ResultBody fileUtilInfo(@RequestBody FileUtil fileUtil){
        fileUtil = fileUtilService.get(fileUtil);
        if(fileUtil == null){
            return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到记录");
        }
        return new ResultBody(ApiResultEnum.SUCCESS, fileUtil);
    }

    /**
     * 新增
     * @param fileUtil
     */
    @Log(title = "新增文件配置路径", type = LogEnum.INSERT)
    @PostMapping("api/fileUtilInsert.act")
    public ResultBody fileUtilInsert(HttpSession session, @RequestBody FileUtil fileUtil){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            fileUtil.setCreateId(person.getIds());
            fileUtil.setPsnId(person.getIds());
            fileUtil.getNowDate(null);
            fileUtilService.insertFileUtil(fileUtil);
            return new ResultBody(ApiResultEnum.SUCCESS, "新增成功");
        } catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 文件配置路径查询
     * @param session
     */
    @Log(title = "获取文件配置路径列表", type = LogEnum.SELECT)
    @PostMapping("api/fileUtilList.act")
    public ResultBody fileUtilList(HttpSession session, @RequestBody FileUtil fileUtil){
        try{
            Person list = (Person)redisUtils.get(session.getId());
            fileUtil.setPsnId(list.getIds());
            fileUtil.pubImplPage(fileUtil.getNowTab(),fileUtil.getHasTab());
            List<FileUtil> listSize = fileUtilService.selectFileUtilTab(fileUtil);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 删除
     * @param fileUtil
     */
    @Log(title = "删除文件配置路径", type = LogEnum.DELETE)
    @PostMapping("api/delFileUtil.act")
    public ResultBody delFileUtil(@RequestBody FileUtil fileUtil){
        try {
            fileUtil.setDelFlag("1");
            fileUtil.getNowDate(null);
            fileUtilService.updateLog(fileUtil);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.SUCCESS, e.getMessage());
        }
    }

    /**
     * 更新
     * @param fileUtil
     */
    @Log(title = "更新文件配置路径", type = LogEnum.EDIT)
    @PostMapping("api/fileUtilUpdate.act")
    public ResultBody fileUtilUpdate(@RequestBody FileUtil fileUtil) {
        try {
            fileUtil.setUpdateDate(fileUtil.getNowDate(null));
            fileUtilService.updateLog(fileUtil);
            return new ResultBody(ApiResultEnum.SUCCESS, "更新完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
