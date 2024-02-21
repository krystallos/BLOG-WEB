package com.example.postApi.service;

import com.example.postApi.enity.PostApiEnity;
import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.postApi.mapper.ApiPostListMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiPostListService {

    @Resource
    private ApiPostListMapper apiPostListMapper;

    /**
     * 查询接口列表
     * @param apiPostEnity
     * @return
     */
    public List<PostApiEnity> apiPostListPage(PostApiEnity apiPostEnity) {
        PageHelper.offsetPage(apiPostEnity.getStartTab(), apiPostEnity.getHasTab(),true);
        List<PostApiEnity> item = apiPostListMapper.apiPostListPage(apiPostEnity);
        return item;
    }

    public int countApiPostListPage(PostApiEnity apiPostEnity){
        return apiPostListMapper.countApiPostListPage(apiPostEnity);
    }

    public int insertApiPost(PostApiEnity postApiEnity){
        postApiEnity.setIds(rsaKey.uuid(null));
        return apiPostListMapper.insertApiPost(postApiEnity);
    }

    public int deleteApiPost(PostApiEnity postApiEnity){
        return apiPostListMapper.deleteApiPost(postApiEnity);
    }

}
