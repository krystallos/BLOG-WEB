package com.example.mineBlos.myDrama.service;

import com.example.mineBlos.myDrama.enity.DramaList;
import com.example.mineBlos.myDrama.mapper.DramaMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DramaService {

    @Resource
    private DramaMapper dramaMapper;

    /**
     * 查询博客文章列表
     * @param dramaList
     * @return
     */
    public List<DramaList> dramaList(DramaList dramaList){
        PageHelper.offsetPage(dramaList.getStartTab(), dramaList.getHasTab());
        List<DramaList> item = dramaMapper.dramaList(dramaList);
        return item;
    }

}
