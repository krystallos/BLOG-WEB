package com.example.fileConfig.service;

import com.example.fileConfig.enity.fiction.FictionTag;
import com.example.fileConfig.mapper.FictionUtilMapper;
import com.example.util.dic.DicVo;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FictionUtilService {

    @Resource
    FictionUtilMapper fictionUtilMapper;

    /*获取唯一实例*/
    public FictionTag get(FictionTag fictionTag){
        return fictionUtilMapper.get(fictionTag);
    }

    /*获取是否唯一名称*/
    public int selectCountHaving(String tagName, String ids){
        return fictionUtilMapper.selectCountHaving(tagName,ids);
    }

    /*查询标签路径*/
    public List<FictionTag> selectFictionUtilTab(FictionTag fictionTag){
        PageHelper.offsetPage(fictionTag.getStartTab(), fictionTag.getHasTab());
        List<FictionTag> tab = fictionUtilMapper.selectFictionUtilTab(fictionTag);
        return tab;
    }

    /*查询标签路径(不带分页)*/
    public List<FictionTag> selectFileUtilNoTab(FictionTag fictionTag){
        List<FictionTag> tab = fictionUtilMapper.selectFictionUtilTab(fictionTag);
        return tab;
    }

    /*字典规范查询标签路径(不带分页)*/
    public List<DicVo> selectFileUtilDicNoTab(){
        List<DicVo> tab = fictionUtilMapper.selectFileUtilDicNoTab();
        return tab;
    }

    /*查询指定数量的标签(不带分页)*/
    public List<FictionTag> selectFileUtilToNum(int num){
        List<FictionTag> tab = fictionUtilMapper.selectFileUtilToNum(num);
        return tab;
    }

    /*插入数据（未启动存储过程版）*/
    public int insertFictionUtil(FictionTag fictionTag){
        fictionTag.setIds(rsaKey.uuid(null));
        return fictionUtilMapper.insertFictionUtil(fictionTag);
    }

    /*更新数据*/
    public int updateFictionUtil(FictionTag fictionTag){
        return fictionUtilMapper.updateFictionUtil(fictionTag);
    }

    /*删除数据*/
    public int delFictionUtil(FictionTag fictionTag){
        return fictionUtilMapper.delFictionUtil(fictionTag);
    }

}
