package com.example.logAspect.web;

import com.example.fileConfig.enity.FileSelectPixiv;
import com.example.logAspect.enity.LogAspectList;
import com.example.logAspect.service.LogAspectService;
import com.example.token.enity.token;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志系统
 */
@RestController
public class LogAspectConteroller {

    private static final Logger log = Logger.getLogger(LogAspectConteroller.class);

    @Resource
    private LogAspectService logAspectService;

    /**
     * 访问日志查询
     */
    @Log(title = "访问信息日志列表", type = LogEnum.SELECT)
    @PostMapping("api/aspLog.act")
    public ResultBody aspLog(@RequestBody LogAspectList logAspect){
        try{
            logAspect.pubImplPage(logAspect.getNowTab(),logAspect.getHasTab());
            logAspect.setResult(null);
            List<LogAspectList> listSize = logAspectService.findListLog(logAspect);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 图片日志查询
     */
    @Log(title = "访问图片日志列表", type = LogEnum.SELECT)
    @PostMapping("api/aspImg.act")
    public ResultBody aspImg(@RequestBody FileSelectPixiv filePixiv){
        try{
            filePixiv.pubImplPage(filePixiv.getNowTab(),filePixiv.getHasTab());
            List<FileSelectPixiv> listSize = logAspectService.findListImg(filePixiv);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * Token日志查询
     */
    @Log(title = "访问TOKEN日志列表", type = LogEnum.SELECT)
    @PostMapping("api/aspToken.act")
    public ResultBody aspToken(@RequestBody token token){
        try{
            token.pubImplPage(token.getNowTab(),token.getHasTab());
            List<token> listSize = logAspectService.findListToken(token);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

}
