package com.example.postApi.web.app;

import com.example.postApi.enity.PostApiEnity;
import com.example.postApi.service.ApiPostListService;
import com.example.util.*;
import com.example.util.config.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 对外接口控制层
 * @author Enoki
 */
@RestController
public class ApiPostListAppConteroller {

    private final Logger logger = LoggerFactory.getLogger(ApiPostListAppConteroller.class);

    @Resource
    private ApiPostListService apiPostListService;
    @Resource
    private requestUTF requestUTF;

    /**
     * 对外接口列表
     * @param request
     * @param response
     * @param postApiEnity
     */
    @PostMapping("api/postApiTabApp.act")
    public ResultBody postApiTabApp(HttpServletRequest request, HttpServletResponse response, @RequestBody PostApiEnity postApiEnity){
        try{
            requestUTF.uTFonE(request,response);
            postApiEnity.pubImplPage(postApiEnity.getNowTab(),postApiEnity.getHasTab());
            int total = apiPostListService.countApiPostListPage(postApiEnity);
            List<PostApiEnity> listTab = total == 0? new ArrayList<>(): apiPostListService.apiPostListPage(postApiEnity);
            return new ResultBody(ApiResultEnum.SUCCESS,listTab,total);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR,null);
    }

}
