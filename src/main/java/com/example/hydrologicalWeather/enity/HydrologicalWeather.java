package com.example.hydrologicalWeather.enity;

import java.util.List;

public class HydrologicalWeather {

    private List<GotoSea> beach_catch_times;
    private List<FishingTime> fishing_times;
    private List<Tide> list;
    private List<Peak> peak;

    public List<Peak> getPeak() {
        return peak;
    }

    public void setPeak(List<Peak> peak) {
        this.peak = peak;
    }

    public List<GotoSea> getBeach_catch_times() {
        return beach_catch_times;
    }

    public void setBeach_catch_times(List<GotoSea> beach_catch_times) {
        this.beach_catch_times = beach_catch_times;
    }

    public List<FishingTime> getFishing_times() {
        return fishing_times;
    }

    public void setFishing_times(List<FishingTime> fishing_times) {
        this.fishing_times = fishing_times;
    }

    public List<Tide> getList() {
        return list;
    }

    public void setList(List<Tide> list) {
        this.list = list;
    }
}
