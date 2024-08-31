package com.example.mineBlos.myDrama.service;

import com.example.mineBlos.myDrama.enity.DramaList;
import com.example.mineBlos.myDrama.mapper.DramaMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DramaService {

    @Resource
    private DramaMapper dramaMapper;

    /**
     * 查询番剧列表
     * @param dramaList
     * @return
     */
    public List<DramaList> dramaList(DramaList dramaList){
        PageHelper.offsetPage(dramaList.getStartTab(), dramaList.getHasTab());
        List<DramaList> item = dramaMapper.dramaList(dramaList);
        return item;
    }

    /**
     * 获取番剧详情
     * @param dramaList
     * @return
     */
    public DramaList getDetialdrama(DramaList dramaList){
        return dramaMapper.getDetialdrama(dramaList);
    }

    /**
     * 获取番剧详情
     * @param dramaList
     * @return
     */
    public int delDrama(DramaList dramaList){
        return dramaMapper.delDrama(dramaList);
    }

    /**
     * 更新番剧
     * @param dramaList
     * @return
     */
    public int editDrama(DramaList dramaList){
        return dramaMapper.editDrama(dramaList);
    }

    /**
     * 新增番剧
     * @param dramaList
     * @return
     */
    public int insertDrama(DramaList dramaList){
        return dramaMapper.insertDrama(dramaList);
    }

}
