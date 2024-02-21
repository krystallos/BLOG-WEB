package com.example.fileConfig.service;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.fileConfig.mapper.FileLookMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileLookService {

    @Resource
    FileLookMapper fileLookMapper;

    /*获取唯一实例*/
    public CodeFileEnity get(CodeFileEnity codeFileEnity){
        return fileLookMapper.get(codeFileEnity);
    }

    /*查询文件路径*/
    public List<CodeFileEnity> selectFileLookTab(CodeFileEnity codeFileEnity){
        PageHelper.offsetPage(codeFileEnity.getStartTab(), codeFileEnity.getHasTab());
        List<CodeFileEnity> tab = fileLookMapper.selectFileLookTab(codeFileEnity);
        return tab;
    }

}
