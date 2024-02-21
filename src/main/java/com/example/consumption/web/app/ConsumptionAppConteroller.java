package com.example.consumption.web.app;

import com.example.consumption.enity.ConsumptionVo;
import com.example.consumption.service.ConsumptionService;
import com.example.nachrichten.web.app.NachrichtenAppConteroller;
import com.example.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 消费记录页
 * @author Enoki
 */
@RestController
public class ConsumptionAppConteroller {

    private static final Logger log = Logger.getLogger(ConsumptionAppConteroller.class);

    @Resource
    private ConsumptionService consumptionService;
    @Resource
    private requestUTF requestUTF;

    /**
     * 订单查询
     * @param response
     * @param request
     */
    @PostMapping("api/consumptionAppList.act")
    public ResultBody consumptionAppList(HttpServletResponse response, HttpServletRequest request,@RequestBody ConsumptionVo consumptionVo){
        try{
            requestUTF.uTFonE(request,response);
            consumptionVo.setCreateId(consumptionVo.getCreateId());
            consumptionVo.pubImplPage(consumptionVo.getNowTab(),consumptionVo.getHasTab());
            int count = consumptionService.countSelectConsumptionTab(consumptionVo);
            List<ConsumptionVo> listSize = count == 0? new ArrayList<>() : consumptionService.selectConsumptionNotDeitlTab(consumptionVo);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, count);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

    /**
     * 订单查询
     * @param response
     * @param request
     */
    @PostMapping("api/consumptionGet.act")
    public ResultBody consumptionGet(HttpServletResponse response, HttpServletRequest request,@RequestBody ConsumptionVo consumptionVo){
        try{
            requestUTF.uTFonE(request,response);
            ConsumptionVo vo = consumptionService.getConsumptionDeitl(consumptionVo.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, vo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

}
