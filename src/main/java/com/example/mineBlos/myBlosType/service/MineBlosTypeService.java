package com.example.mineBlos.myBlosType.service;

import com.example.mineBlos.myBlosType.enity.MineBlosType;
import com.example.mineBlos.myBlosType.mapper.MineBlosTypeMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MineBlosTypeService {

    @Resource
    private MineBlosTypeMapper mineBlosTypeMapper;

    /**
     * 带分页查询博客类型
     * @param
     * @return
     */
    public List<MineBlosType> mineBlosTypeTab(MineBlosType mineBlosType){
        PageHelper.offsetPage(mineBlosType.getStartTab(), mineBlosType.getHasTab());
        List<MineBlosType> item = mineBlosTypeMapper.mineBlosType(mineBlosType);
        return item;
    }

    /**
     * 不带分页查询博客类型
     * @param mineBlosType
     * @return
     */
    public List<MineBlosType> mineBlosTypeNoTab(MineBlosType mineBlosType){
        return mineBlosTypeMapper.mineBlosType(mineBlosType);
    }


    /**
     * 查询单个信息
     */
    public MineBlosType mineBlosTypeGet(String uuid){
        MineBlosType item = mineBlosTypeMapper.mineBlosTypeGet(uuid);
        return item;
    }

    /**
     * 用于更新或是插入数据
     * @param mineBlosType
     * @return
     */
    public int insertMineBlosType(MineBlosType mineBlosType){
        if(mineBlosType.getIds()!=null && !mineBlosType.getIds().equals("")){
            return mineBlosTypeMapper.updateMineBlosType(mineBlosType);
        }
        mineBlosType.setIds(rsaKey.uuid(null));
        return mineBlosTypeMapper.insertMineBlosType(mineBlosType);
    }

}
