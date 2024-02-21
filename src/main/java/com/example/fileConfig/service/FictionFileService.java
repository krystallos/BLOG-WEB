package com.example.fileConfig.service;

import com.example.fileConfig.enity.fiction.FictionBook;
import com.example.fileConfig.enity.fiction.FictionFile;
import com.example.fileConfig.mapper.FictionFileMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FictionFileService {

    @Resource
    FictionFileMapper fictionFileMapper;

    /*获取唯一实例*/
    public FictionFile get(FictionFile fictionFile){
        return fictionFileMapper.get(fictionFile);
    }

    /*获取重复小说数量*/
    public int selectFictionCount(String fictionName, String fictionNameFt, String ids){
        return fictionFileMapper.selectFictionCount(fictionName,fictionNameFt,ids);
    }

    /*查询小说(分页)*/
    public List<FictionFile> selectFictionFileTab(FictionFile fictionFile){
        PageHelper.offsetPage(fictionFile.getStartTab(), fictionFile.getHasTab());
        List<FictionFile> tab = fictionFileMapper.selectFictionFileTab(fictionFile);
        return tab;
    }

    /*获取APP端首页指定标签的前N本书*/
    public List<FictionFile> selectRecommend(int num, String tagId){
        return fictionFileMapper.selectRecommend(num, tagId);
    }

    /*获取APP端首页最近更新的前N本书*/
    public List<FictionFile> selectRecommendForDate(int num){
        return fictionFileMapper.selectRecommendForDate(num);
    }

    /*统计小说数量*/
    public int selectAllCountFiction(FictionFile fictionFile){
        return fictionFileMapper.selectAllCountFiction(fictionFile);
    }

    /*统计小说数量*/
    public int selectAllCountBook(FictionFile fictionFile){
        return fictionFileMapper.selectAllCountBook(fictionFile);
    }

    /*插入数据（未启动存储过程版）*/
    public int insertFictionFile(FictionFile fictionFile){
        fictionFile.setIds(rsaKey.uuid(null));
        return fictionFileMapper.insertFictionFile(fictionFile);
    }

    /*更新数据*/
    public int updateFictionFile(FictionFile fictionFile){
        return fictionFileMapper.updateFictionFile(fictionFile);
    }

    /*删除数据*/
    public int delFictionFile(FictionFile fictionFile){
        return fictionFileMapper.delFictionFile(fictionFile);
    }

    /*查询指定书籍*/
    public FictionBook getFictionBook(FictionBook fictionBook){
        return fictionFileMapper.getFictionBook(fictionBook);
    }

    /*查询书籍路径*/
    public List<FictionBook> selectFictionMainTab(FictionBook fictionBook){
        PageHelper.offsetPage(fictionBook.getStartTab(), fictionBook.getHasTab());
        List<FictionBook> tab = fictionFileMapper.selectFictionMainTab(fictionBook);
        return tab;
    }

    /*插入书本数据（未启动存储过程版）*/
    public int insertFictionMain(FictionBook fictionBook){
        fictionBook.setIds(rsaKey.uuid(null));
        return fictionFileMapper.insertFictionMain(fictionBook);
    }

    /*删除书本数据*/
    public int delFictionMain(FictionBook fictionBook){
        return fictionFileMapper.delFictionMain(fictionBook);
    }

    /*获取书本封面*/
    public String lookBookMainCover(FictionFile fictionFile){
        return fictionFileMapper.lookBookMainCover(fictionFile);
    }

}
