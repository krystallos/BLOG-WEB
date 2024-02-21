package com.example.activityLike.service;

import com.example.activityLike.enity.ActivityLike;
import com.example.activityLike.mapper.ActivityLikeMapper;
import com.example.emil.enity.MineEmil;
import com.example.emil.mapper.MineEmilMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收藏夹控制页
 * @author Enoki
 */
@Service
public class ActivityLikeService {

    @Resource
    ActivityLikeMapper activityLikeMapper;

    /*带分页查询*/
    public List<ActivityLike> selectPageLike(ActivityLike activityLike){
        PageHelper.offsetPage(activityLike.getStartTab(), activityLike.getHasTab());
        List<ActivityLike> tab = activityLikeMapper.selectPageLike(activityLike);
        return tab;
    }

    /*不带分页查询*/
    public List<ActivityLike> selectPageNoLike(ActivityLike activityLike){
        List<ActivityLike> tab = activityLikeMapper.selectPageLike(activityLike);
        return tab;
    }

    /*单条查询*/
    public ActivityLike getActivityLike(String ids){
        return activityLikeMapper.getActivityLike(ids);
    }

    /*收藏夹保存*/
    public int insertLike(ActivityLike activityLike){
        activityLike.setIds(rsaKey.uuid(""));
        activityLike.getNowDate("");
        activityLike.setDelFlag("0");
        return activityLikeMapper.insertLike(activityLike);
    }

    public int updateLike(ActivityLike activityLike){
        return activityLikeMapper.updateLike(activityLike);
    }

}
