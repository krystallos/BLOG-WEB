package com.example.fileConfig.service;

import com.example.fileConfig.enity.FileUtil;
import com.example.fileConfig.mapper.FileUtilMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileUtilService {

    @Resource
    FileUtilMapper fileUtilMapper;

    /*获取唯一实例*/
    public FileUtil get(FileUtil fileUtil){
        return fileUtilMapper.get(fileUtil);
    }

    /*查询文件路径*/
    public List<FileUtil> selectFileUtilTab(FileUtil fileUtil){
        PageHelper.offsetPage(fileUtil.getStartTab(), fileUtil.getHasTab());
        List<FileUtil> tab = fileUtilMapper.selectFileUtilTab(fileUtil);
        return tab;
    }

    /*查询文件路径(不带分页)*/
    public List<FileUtil> selectFileUtilNoTab(FileUtil fileUtil){
        List<FileUtil> tab = fileUtilMapper.selectFileUtilTab(fileUtil);
        return tab;
    }

    /*插入数据（未启动存储过程版）*/
    public int insertFileUtil(FileUtil fileUtil){
        fileUtil.setIds(rsaKey.uuid(null));
        return fileUtilMapper.insertFileUtil(fileUtil);
    }

    /*更新数据*/
    public int updateLog(FileUtil fileUtil){
        return fileUtilMapper.updateLog(fileUtil);
    }

}
