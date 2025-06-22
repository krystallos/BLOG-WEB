package com.example.job.HydrologicalJob.web;

import com.alibaba.fastjson.JSONObject;
import com.example.hydrologicalWeather.enity.*;
import com.example.hydrologicalWeather.service.HydrologicalWeatherService;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 水文监控
 * @author Enoki
 */
@RestController
public class HydrologicalJobConteroller {

    private static final Logger log = Logger.getLogger(HydrologicalJobConteroller.class);

    @Resource
    private HydrologicalWeatherService hydrologicalWeatherService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 自动任务每隔120分钟更新一次系统信息
     */
    @Scheduled( fixedDelay = 60 * 1000 * 120 )
    public void getHydrologicalJob() {
        try {
            String envir = redisUtils.getConfig(ConfigDicEnum.systemConfigEnvir.message);
            log.info("当前环境为[ " + envir + " ]");
            if(envir.equals("PROD")){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                ResultHydrologicalWeather resultHydrologicalWeather = urlGetTide("T106", simpleDateFormat.format(new Date()));
                if(resultHydrologicalWeather != null && resultHydrologicalWeather.getCode() == 1){
                    //删除
                    hydrologicalWeatherService.delWeather();
                    hydrologicalWeatherService.delLevel();
                    int tideId = 1;
                    int timeId = 1;
                    for(Tide tide : resultHydrologicalWeather.getData().getList()){
                        tide.setId(tideId);
                        //新增时间轴
                        hydrologicalWeatherService.insertWeather(tide);
                        tideId++;
                    }
                    //新增最佳时间
                    for(GotoSea gotoSea : resultHydrologicalWeather.getData().getBeach_catch_times()){
                        gotoSea.setId(timeId);
                        hydrologicalWeatherService.insertLevelGotoSea(gotoSea);
                        timeId++;
                    }
                    for(FishingTime fishingTime : resultHydrologicalWeather.getData().getFishing_times()){
                        fishingTime.setId(timeId);
                        hydrologicalWeatherService.insertLevelTypeFish(fishingTime);
                        timeId++;
                    }
                    for(int a = 0; a< resultHydrologicalWeather.getData().getPeak().size(); a++){
                        resultHydrologicalWeather.getData().getPeak().get(a).setId(timeId);
                        if(a == 0){
                            resultHydrologicalWeather.getData().getPeak().get(a).setxEnd("00:00");
                        }else{
                            resultHydrologicalWeather.getData().getPeak().get(a).setxEnd(resultHydrologicalWeather.getData().getPeak().get(a-1).getX());
                        }
                        hydrologicalWeatherService.insertLevelTypePeak(resultHydrologicalWeather.getData().getPeak().get(a));
                        timeId++;
                    }
                }
                ResultHydrologicalTime resultHydrologicalTime = urlGetWeather("T106");
                if(resultHydrologicalTime != null && resultHydrologicalTime.getCode() == 1){
                    hydrologicalWeatherService.delWeatherTotal();
                    Integer calId = 1;
                    Map<String, Weather> weatMap = new HashMap<>();
                    for(Weather weather : resultHydrologicalTime.getData().getWeather()){
                        weatMap.put(weather.getDate(), weather);
                    }
                    for(Calendars calendars : resultHydrologicalTime.getData().getCalendars()){
                        Integer finalCalId = calId;
                        CalendarsWeatherTotal calendarsWeatherTotal = new CalendarsWeatherTotal(){{
                            setId(finalCalId);
                            setDate(calendars.getDate());
                            setD(calendars.getD());
                            setMax(calendars.getMax());
                            setMin(calendars.getMin());
                            setDiff(calendars.getDiff());
                            setLunar(calendars.getLunar());
                            setWeek(calendars.getWeek());
                            setTag(calendars.getTag());
                            setText(calendars.getText());
                            setWater(calendars.getWater());
                            setWeather(weatMap.get(calendars.getDate()).getWeather());
                            setLow(weatMap.get(calendars.getDate()).getLow());
                            setHigh(weatMap.get(calendars.getDate()).getHigh());
                            setWind(weatMap.get(calendars.getDate()).getWind());
                            setWindLevel(weatMap.get(calendars.getDate()).getWindLevel());
                            setAirQuality(weatMap.get(calendars.getDate()).getAirQuality());
                        }};
                        hydrologicalWeatherService.insertWeatherTotal(calendarsWeatherTotal);
                        calId++;
                    }
                }
            }
        }catch (Exception e){
            log.info("获取水文错误，错误原因:" + e.getMessage());
        }

    }

    public ResultHydrologicalWeather urlGetTide(String areaCode, String dateTime) throws IOException {
        HttpGet httpGet = new HttpGet("https://www.haiyuyun.cn/api/v2/pc/tide_chart?siteCode=" + areaCode + "&date=" + dateTime + "&client=pc");
        //设置类型 "application/x-www-form-urlencoded" "application/json"
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        log.info("调用URL: " + httpGet.getURI());
        //httpClient实例化
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //执行请求并获取返回
        HttpResponse response = httpClient.execute(httpGet, context);
        log.info("返回状态码：" + response.getStatusLine());
        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String line = null;
        StringBuffer responseSB = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            responseSB.append(line);
        }
        reader.close();
        return JSONObject.parseObject(new String(responseSB), ResultHydrologicalWeather.class);
    }

    public ResultHydrologicalTime urlGetWeather(String areaCode) throws IOException {
        HttpGet httpGet = new HttpGet("https://www.haiyuyun.cn/api/v2/pc/tide_site?siteCode=" + areaCode + "&client=pc");
        //设置类型 "application/x-www-form-urlencoded" "application/json"
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        log.info("调用URL: " + httpGet.getURI());
        //httpClient实例化
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //执行请求并获取返回
        HttpResponse response = httpClient.execute(httpGet, context);
        log.info("返回状态码：" + response.getStatusLine());
        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String line = null;
        StringBuffer responseSB = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            responseSB.append(line);
        }
        reader.close();
        return JSONObject.parseObject(new String(responseSB), ResultHydrologicalTime.class);
    }

}