package com.example.hydrologicalWeather.service;

import com.example.hydrologicalWeather.enity.*;
import com.example.hydrologicalWeather.mapper.HydrologicalWeatherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HydrologicalWeatherService {

    @Resource
    HydrologicalWeatherMapper hydrologicalWeatherMapper;

    public List<GotoSea> getHydrologicalTime(GetHydrological vo){
        return hydrologicalWeatherMapper.getHydrologicalTime(vo);
    }

    public List<CalendarsWeatherTotal> getWeatherList(GetHydrological vo){
        return hydrologicalWeatherMapper.getWeatherList(vo);
    }

    public List<Tide> getHydrologicalList(GetHydrological vo){
        return hydrologicalWeatherMapper.getHydrologicalList(vo);
    }

    public int insertWeatherTotal(CalendarsWeatherTotal calendarsWeatherTotal){
        return hydrologicalWeatherMapper.insertWeatherTotal(calendarsWeatherTotal);
    }

    public int insertWeather(Tide tide){
        return hydrologicalWeatherMapper.insertWeather(tide);
    }

    public int insertLevelTypeFish(FishingTime fishingTime){
        return hydrologicalWeatherMapper.insertLevelTypeFish(fishingTime);
    }

    public int insertLevelTypePeak(Peak peak){
        return hydrologicalWeatherMapper.insertLevelTypePeak(peak);
    }

    public int insertLevelGotoSea(GotoSea gotoSea){
        return hydrologicalWeatherMapper.insertLevelGotoSea(gotoSea);
    }

    public int delWeather(){
        return hydrologicalWeatherMapper.delWeather();
    }
    public int delLevel(){
        return hydrologicalWeatherMapper.delLevel();
    }
    public int delWeatherTotal(){
        return hydrologicalWeatherMapper.delWeatherTotal();
    }

}
