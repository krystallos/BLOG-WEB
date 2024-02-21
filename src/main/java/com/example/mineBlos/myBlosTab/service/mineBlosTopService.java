package com.example.mineBlos.myBlosTab.service;

import com.example.mineBlos.myBlosTab.enity.MineBlosTop;
import com.example.mineBlos.myBlosTab.mapper.mineBlosTopMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class mineBlosTopService {

    @Resource
    private mineBlosTopMapper mineBlosTopMapper;

    /**
     * 查询最近的操作
     * @param
     * @return
     */
    public int writeMineBlos(MineBlosTop mineBlosTop){
        return mineBlosTopMapper.writeMineBlos(mineBlosTop);
    }

}
