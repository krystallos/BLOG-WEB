package com.example.hydrologicalWeather.enity;

import java.util.List;

public class HydrologicalTime {

    private List<Calendars> calendars;
    private List<Weather> weather;

    public List<Calendars> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendars> calendars) {
        this.calendars = calendars;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
