package com.example.hydrologicalWeather.web;

import com.example.fileConfig.enity.ehentai.GetEhentaiVo;
import com.example.hydrologicalWeather.enity.CalendarsWeatherTotal;
import com.example.hydrologicalWeather.enity.GetHydrological;
import com.example.hydrologicalWeather.enity.GotoSea;
import com.example.hydrologicalWeather.enity.Tide;
import com.example.hydrologicalWeather.service.HydrologicalWeatherService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 水文天气
 * @author Enoki
 */
@RestController
public class HydrologicalWeatherConteroller {

    private static final Logger log = Logger.getLogger(HydrologicalWeatherConteroller.class);

    @Resource
    private HydrologicalWeatherService hydrologicalWeatherService;

    /**
     * 获取某区域的当天潮汐表
     */
    @Log(title = "获取某区域的当天潮汐表", type = LogEnum.SELECT)
    @PostMapping("api/getHydrologicalList.act")
    public ResultBody getHydrologicalList(@RequestBody GetHydrological vo){
        try{
            List<Tide> tideList = hydrologicalWeatherService.getHydrologicalList(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, tideList);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取某区域最近七天水文天气
     */
    @Log(title = "获取某区域最近七天水文天气", type = LogEnum.SELECT)
    @PostMapping("api/getWeatherList.act")
    public ResultBody getWeatherList(@RequestBody GetHydrological vo){
        try{
            List<CalendarsWeatherTotal> calendarsWeatherTotals = hydrologicalWeatherService.getWeatherList(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, calendarsWeatherTotals);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取某区域的当天最佳水文
     */
    @Log(title = "获取某区域的当天最佳水文", type = LogEnum.SELECT)
    @PostMapping("api/getHydrologicalTime.act")
    public ResultBody getHydrologicalTime(@RequestBody GetHydrological vo){
        try{
            List<GotoSea> tideList = hydrologicalWeatherService.getHydrologicalTime(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, tideList);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
