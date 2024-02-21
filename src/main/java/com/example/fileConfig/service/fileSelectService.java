package com.example.fileConfig.service;


import com.example.fileConfig.enity.FilePixiv;
import com.example.fileConfig.enity.sauce.SauceData;
import com.example.fileConfig.mapper.FileSelectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class fileSelectService {

    @Resource
    private FileSelectMapper fileSelectMapper;

    /*获取唯一实例*/
    public FilePixiv get(FilePixiv filePixiv){
        return fileSelectMapper.get(filePixiv);
    }

    public int insert(FilePixiv filePixiv){
        return fileSelectMapper.insert(filePixiv);
    }

    public FilePixiv findGetPixiv(SauceData sauceData){
        return fileSelectMapper.findGetPixiv(sauceData);
    }


}
