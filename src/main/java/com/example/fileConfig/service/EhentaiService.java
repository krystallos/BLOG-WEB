package com.example.fileConfig.service;

import com.example.fileConfig.enity.ehentai.GetEhentaiVo;
import com.example.fileConfig.mapper.EhentaiMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class EhentaiService {

    @Resource
    EhentaiMapper ehentaiMapper;

    /*获取唯一实例*/
    public GetEhentaiVo get(GetEhentaiVo getEhentaiVo){
        return ehentaiMapper.get(getEhentaiVo);
    }

    /*获取全部的E-hentai文件*/
    public List<GetEhentaiVo> selectEhentaiName(){
        return ehentaiMapper.selectEhentaiName();
    }

    /*获取E-hentai文件列表*/
    public List<GetEhentaiVo> selectEhentaiFile(GetEhentaiVo vo){
        PageHelper.offsetPage(vo.getStartTab(), vo.getHasTab());
        List<GetEhentaiVo> list = ehentaiMapper.selectEhentaiFile(vo);
        return list;
    }

    /*新增文件*/
    public int insertEhentai(GetEhentaiVo getEhentaiVo){
        getEhentaiVo.setIds(rsaKey.uuid(null));
        getEhentaiVo.setUpdateDate(getEhentaiVo.getNowDate(""));
        getEhentaiVo.setBookTimeStamp(new Date().getTime() + "");
        return ehentaiMapper.insertEhentai(getEhentaiVo);
    }

    /*更新文件*/
    public int updateEhentaiFile(GetEhentaiVo vo){
        return ehentaiMapper.updateEhentai(vo);
    }

}
