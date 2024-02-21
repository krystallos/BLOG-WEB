package com.example.postApi.web;

import com.example.postApi.enity.PostApiEnity;
import com.example.postApi.service.ApiPostListService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 对外接口控制层
 * @author Enoki
 */
@RestController
public class ApiPostListConteroller {

    private static final Logger log = Logger.getLogger(ApiPostListConteroller.class);

    @Resource
    private ApiPostListService apiPostListService;

    /**
     * 对外接口列表
     * @param postApiEnity
     */
    @Log(title = "对外公开接口列表", type = LogEnum.SELECT)
    @PostMapping("api/postApiTab.act")
    public ResultBody postApiTab(@RequestBody PostApiEnity postApiEnity){
        try{
            List<PostApiEnity> listTab = new ArrayList<>();
            postApiEnity.pubImplPage(postApiEnity.getNowTab(),postApiEnity.getHasTab());
            listTab = apiPostListService.apiPostListPage(postApiEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 提交接口记录
     * @param postApiEnity
     */
    @Log(title = "提交对外接口", type = LogEnum.INSERT)
    @PostMapping("api/postApiInsert.act")
    public ResultBody postApiInsert(@RequestBody PostApiEnity postApiEnity){
        try {
            apiPostListService.insertApiPost(postApiEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, "提交成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除接口记录
     * @param postApiEnity
     */
    @Log(title = "删除对外接口", type = LogEnum.DELETE)
    @PostMapping("api/postApiDelete.act")
    public ResultBody postApiDelete(@RequestBody PostApiEnity postApiEnity){
        try {
            apiPostListService.deleteApiPost(postApiEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
