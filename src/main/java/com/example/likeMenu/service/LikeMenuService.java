package com.example.likeMenu.service;

import com.example.likeMenu.enity.LikeMenu;
import com.example.likeMenu.mapper.LikeMenuMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LikeMenuService {

    @Resource
    LikeMenuMapper likeMenuMapper;

    /*查询全部的社区信息*/
    public List<LikeMenu> selectLikeMenuTab(LikeMenu likeMenu){
        PageHelper.offsetPage(likeMenu.getStartTab(), likeMenu.getHasTab());
        List<LikeMenu> tab = likeMenuMapper.selectLikeMenu(likeMenu);
        return tab;
    }

    /*查询全部的社区信息(不带分页)*/
    public List<LikeMenu> selectLikeMenuNoTab(LikeMenu likeMenu){
        List<LikeMenu> tab = likeMenuMapper.selectLikeMenu(likeMenu);
        return tab;
    }

    /*修改更新社区信息*/
    public int updateLikeMenuTab(LikeMenu likeMenu){
        likeMenu.setUpdateDate(likeMenu.getNowDate(null));//更新时间设置
        return likeMenuMapper.updateLikeMenuTab(likeMenu);
    }

    /*删除/恢复社区信息*/
    public int delLikeMenuTab(String ids,String type){
        LikeMenu likeMenu = new LikeMenu();
        likeMenu.setUpdateDate(likeMenu.getNowDate(null));//更新时间设置
        likeMenu.setIds(ids);
        if(type.equals("删除")){
            likeMenu.setDelFlag("1");
        }else{
            likeMenu.setDelFlag("0");
        }
        return likeMenuMapper.delLikeMenuTab(likeMenu);
    }

    public LikeMenu getLikeMenu(String ids) {
        return likeMenuMapper.getLikeMenu(ids);
    }

    /*新增社区*/
    public String insertLikeMenuTab(LikeMenu likeMenu){
        String ids = rsaKey.uuid(null);
        likeMenu.setIds(ids);
        likeMenu.getNowDate(null);
        int a = likeMenuMapper.insertLikeMenuTab(likeMenu);
        if(a>0){
            return ids;
        }
        return null;
    }

    /*查询默认社区*/
    public LikeMenu selectDefLikeMenu(){
        return likeMenuMapper.selectDefLikeMenu();
    }

}
