package com.example.api.codeEdny.web;

import com.example.api.aiWords.enity.AiWordsChild;
import com.example.api.aiWords.enity.AiWordsFather;
import com.example.api.aiWords.enity.AiWordsPage;
import com.example.mineBlos.myDrama.enity.DramaList;
import com.example.mineBlos.myDrama.service.DramaService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.requestUTF;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 番剧管理页
 * @author Enoki
 */
@RestController
public class DramaConteroller {

    private static final Logger log = Logger.getLogger(DramaConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private DramaService dramaService;

    /*获取顶级词汇*/
    @Log(title = "分享的番剧列表（公开）", type = LogEnum.SELECT)
    @PostMapping("open/dramaList.act")
    public ResultBody dramaList(HttpServletRequest request, HttpServletResponse response, @RequestBody DramaList dramaList){
        try {
            requestUTF.uTFonE(request, response);
            dramaList.pubImplPage(dramaList.getNowTab(),dramaList.getHasTab());
            List<DramaList> item = dramaService.dramaList(dramaList);
            return new ResultBody(ApiResultEnum.SUCCESS, item, (int) new PageInfo<>(item).getTotal());
        }catch (IOException e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
