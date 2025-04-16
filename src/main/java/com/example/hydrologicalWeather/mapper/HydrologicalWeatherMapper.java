package com.example.hydrologicalWeather.mapper;

import com.example.fileConfig.enity.ehentai.GetEhentaiVo;
import com.example.hydrologicalWeather.enity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HydrologicalWeatherMapper {

	List<GotoSea> getHydrologicalTime(GetHydrological vo);
	List<CalendarsWeatherTotal> getWeatherList(GetHydrological vo);
	List<Tide> getHydrologicalList(GetHydrological vo);

	int insertWeatherTotal(@Param("calendarsWeatherTotal") CalendarsWeatherTotal calendarsWeatherTotal);
	int insertWeather(@Param("tide") Tide tide);
	int insertLevelTypeFish(@Param("fishingTime") FishingTime fishingTime);
	int insertLevelGotoSea(@Param("gotoSea") GotoSea gotoSea);
	int insertLevelTypePeak(@Param("peak") Peak peak);

	int delWeather();
	int delLevel();
	int delWeatherTotal();

}
