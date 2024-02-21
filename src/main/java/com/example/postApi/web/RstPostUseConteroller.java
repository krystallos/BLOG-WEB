package com.example.postApi.web;

import com.example.person.enity.Person;
import com.example.postApi.enity.PostApiEnity;
import com.example.postApi.enity.RstChildPostApi;
import com.example.postApi.enity.RstPostApiVo;
import com.example.postApi.enity.RstPostProject;
import com.example.postApi.service.ApiPostListService;
import com.example.postApi.service.RstPostUseService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.rsaKey;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 接口文档
 * @author Enoki
 */
@RestController
public class RstPostUseConteroller {

    private static final Logger log = Logger.getLogger(RstPostUseConteroller.class);

    @Resource
    private RstPostUseService rstPostUseService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * API文档列表
     * @param rstPostProject
     */
    @Log(title = "API文档列表查询", type = LogEnum.SELECT)
    @PostMapping("api/rstPostList.act")
    public ResultBody rstPostList(@RequestBody RstPostProject rstPostProject){
        try{
            rstPostProject.pubImplPage(rstPostProject.getNowTab(),rstPostProject.getHasTab());
            int total = rstPostUseService.rstPostListCount(rstPostProject);
            List<RstPostProject> list = total == 0 ? new ArrayList<>() : rstPostUseService.rstPostList(rstPostProject);
            for(RstPostProject vo : list){
                vo.setChildren(rstPostUseService.rstPostChildList(vo.getIds()));
                vo.setHasCountFile(vo.getChildren().size());
            }
            return new ResultBody(ApiResultEnum.SUCCESS, list, total);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * API文档详情
     * @param rstPostApiVo
     */
    @Log(title = "API文档详情", type = LogEnum.DETIAL)
    @PostMapping("api/getRstPost.act")
    public ResultBody getRstPost(@RequestBody RstPostApiVo rstPostApiVo){
        try{
            RstChildPostApi vo = rstPostUseService.getRstPost(rstPostApiVo);
            if(vo != null) {
                vo.setRstPostApi(rstPostUseService.getRstPostList(rstPostApiVo));
            }
            return new ResultBody(ApiResultEnum.SUCCESS, vo);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * API文档修改
     * @param rstPostProject
     */
    @Log(title = "修改API文档", type = LogEnum.EDIT)
    @PostMapping("api/editRstPost.act")
    public ResultBody editRstPost(@RequestBody RstPostProject rstPostProject){
        try{
            rstPostUseService.editRstPost(rstPostProject);
            return new ResultBody(ApiResultEnum.SUCCESS, "修改完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * API文档下拉框
     */
    @Log(title = "API文档查询父级下拉", type = LogEnum.SELECT)
    @PostMapping("api/getRstPostChiList.act")
    public ResultBody getRstPostChiList(){
        try{
            return new ResultBody(ApiResultEnum.SUCCESS, rstPostUseService.getRstPostChiList());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * API文档保存
     */
    @Log(title = "保存API文档记录", type = LogEnum.INSERT)
    @PostMapping("api/saveRstPost.act")
    public ResultBody saveRstPost(@RequestBody RstPostProject rstPostProject, HttpSession session){
        try{
            if(rstPostProject.getProjectName() == null || "".equals(rstPostProject.getProjectName())){
                return new ResultBody(ApiResultEnum.FAIL, "项目名称为空");
            }
            if(rstPostUseService.rstPostListDouble(rstPostProject) > 0){
                return new ResultBody(ApiResultEnum.FAIL, "项目名称" + rstPostProject.getProjectName() + "重复");
            }
            if(rstPostProject.getProjectPid() == null || "".equals(rstPostProject.getProjectPid())){
                rstPostProject.setProjectPid("0");
                rstPostProject.setIsFile("0");
            }else{
                rstPostProject.setIsFile("1");
            }
                Person person = (Person) redisUtils.get(session.getId());
            rstPostProject.getNowDate(null);
            rstPostProject.setCreateId(person.getIds());
            rstPostProject.setIds(rsaKey.uuid(""));
            rstPostUseService.insertRstPostApi(rstPostProject);
            return new ResultBody(ApiResultEnum.SUCCESS, "保存成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * API文档详情列表
     * @param rstPostApiVo
     */
    @Log(title = "获取API文档接口列表", type = LogEnum.SELECT)
    @PostMapping("api/getRstPostList.act")
    public ResultBody getRstPostList(@RequestBody RstPostApiVo rstPostApiVo){
        try{
            rstPostApiVo.pubImplPage(rstPostApiVo.getNowTab(),rstPostApiVo.getHasTab());
            List<RstPostApiVo> list = rstPostUseService.getRstPostList(rstPostApiVo);
            return new ResultBody(ApiResultEnum.SUCCESS, list, (int) new PageInfo<>(list).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除API接口
     * @param rstPostApiVo
     */
    @Log(title = "删除API文档", type = LogEnum.DELETE)
    @PostMapping("api/rstDelete.act")
    public ResultBody rstDelete(@RequestBody RstPostApiVo rstPostApiVo){
        try {
            rstPostUseService.rstDelete(rstPostApiVo.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除接口记录
     * @param rstPostProject
     */
    @Log(title = "删除API接口", type = LogEnum.DELETE)
    @PostMapping("api/rstPostDelete.act")
    public ResultBody rstPostDelete(@RequestBody RstPostProject rstPostProject){
        try {
            rstPostUseService.rstPostDelete(rstPostProject.getIds());
            rstPostUseService.rstPostApiDelete(rstPostProject.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取API详情
     * @param rstPostApiVo
     */
    @Log(title = "获取API详情", type = LogEnum.DETIAL)
    @PostMapping("api/getRstApiDetial.act")
    public ResultBody getRstApiDetial(@RequestBody RstPostApiVo rstPostApiVo){
        try {
            return new ResultBody(ApiResultEnum.SUCCESS, rstPostUseService.getRstApiDetial(rstPostApiVo));
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 保存API详情
     * @param rstPostApiVo
     */
    @Log(title = "保存API接口详情", type = LogEnum.INSERT)
    @PostMapping("api/saveRstApiDetial.act")
    public ResultBody saveRstApiDetial(@RequestBody RstPostApiVo rstPostApiVo, HttpSession session){
        try {
            if(rstPostApiVo == null){
                return new ResultBody(ApiResultEnum.FAIL, "数据异常，请检查后重新保存");
            }
            if(rstPostApiVo.getPostName() == null || "".equals(rstPostApiVo.getPostName())){
                return new ResultBody(ApiResultEnum.FAIL, "请填写接口名称");
            }
            if(rstPostApiVo.getPostType() == null || "".equals(rstPostApiVo.getPostType())){
                return new ResultBody(ApiResultEnum.FAIL, "请填写接口类型");
            }
            if(rstPostApiVo.getDataType() == null || "".equals(rstPostApiVo.getDataType())){
                return new ResultBody(ApiResultEnum.FAIL, "请填写数据格式");
            }
            if(rstPostApiVo.getPostUrl() == null || "".equals(rstPostApiVo.getPostUrl())){
                return new ResultBody(ApiResultEnum.FAIL, "请填写接口地址");
            }
            if(rstPostApiVo.getIds() == null) {
                Person person = (Person) redisUtils.get(session.getId());
                rstPostApiVo.getNowDate(null);
                rstPostApiVo.setCreateId(person.getIds());
                rstPostApiVo.setIds(rsaKey.uuid(""));
                rstPostUseService.insertRstApiDetial(rstPostApiVo);
            }else{
                rstPostApiVo.getNowDate(null);
                rstPostUseService.saveRstApiDetial(rstPostApiVo);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "保存成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
