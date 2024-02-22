package com.example.api.rstOpenPost.web;

import com.example.person.enity.Person;
import com.example.postApi.enity.RstChildPostApi;
import com.example.postApi.enity.RstPostApiVo;
import com.example.postApi.enity.RstPostProject;
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
public class RstOpenPostUseConteroller {

    private static final Logger log = Logger.getLogger(RstOpenPostUseConteroller.class);

    @Resource
    private RstPostUseService rstPostUseService;

    /**
     * API文档列表
     * @param rstPostProject
     */
    @Log(title = "接口文档列表（公开）", type = LogEnum.SELECT)
    @PostMapping("open/rstPostList.act")
    public ResultBody rstPostList(@RequestBody RstPostProject rstPostProject){
        try{
            int total =0;
            List<RstPostProject> list = new ArrayList<>();
            if(rstPostProject.getIds() != null && !rstPostProject.getIds().equals("")){
                rstPostProject.pubImplPage(rstPostProject.getNowTab(),rstPostProject.getHasTab());
                total = rstPostUseService.rstOpenPostListCount(rstPostProject);
                list = total == 0 ? new ArrayList<>() : rstPostUseService.rstOpenPostList(rstPostProject);
                for(RstPostProject vo : list){
                    vo.setChildren(rstPostUseService.rstPostChildList(vo.getIds()));
                    vo.setHasCountFile(vo.getChildren().size());
                }
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
    @Log(title = "API文档详情（公开）", type = LogEnum.DETIAL)
    @PostMapping("open/getRstPost.act")
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
     * API文档详情列表
     * @param rstPostApiVo
     */
    @Log(title = "文档详情接口列表（公开）", type = LogEnum.SELECT)
    @PostMapping("open/getRstPostList.act")
    public ResultBody getRstPostList(@RequestBody RstPostApiVo rstPostApiVo){
        try{
            rstPostApiVo.pubImplPage(rstPostApiVo.getNowTab(), rstPostApiVo.getHasTab());
            List<RstPostApiVo> list = rstPostUseService.getRstPostList(rstPostApiVo);
            return new ResultBody(ApiResultEnum.SUCCESS, list, (int) new PageInfo<>(list).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取API详情
     * @param rstPostApiVo
     */
    @Log(title = "获取API接口详情（公开）", type = LogEnum.DETIAL)
    @PostMapping("open/getRstApiDetial.act")
    public ResultBody getRstApiDetial(@RequestBody RstPostApiVo rstPostApiVo){
        try {
            return new ResultBody(ApiResultEnum.SUCCESS, rstPostUseService.getRstApiDetial(rstPostApiVo));
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
