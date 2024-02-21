package com.example.codeFile.service;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.codeFile.mapper.CodeFileMapper;
import com.example.emil.enity.MineEmil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 二维码服务页
 * @author Enoki
 */
@Service
public class CodeFileService {

    @Resource
    CodeFileMapper codeFileMapper;

    /*批量查询*/
    public List<CodeFileEnity> selectPageCodeFile(CodeFileEnity codeFileEnity){
        PageHelper.offsetPage(codeFileEnity.getStartTab(), codeFileEnity.getHasTab());
        List<CodeFileEnity> tab = codeFileMapper.selectPageCodeFile(codeFileEnity);
        return tab;
    }

    /*单条查询*/
    public CodeFileEnity getCodeFile(CodeFileEnity codeFileEnity){
        return codeFileMapper.getCodeFile(codeFileEnity);
    }

    /*逻辑删除二维码*/
    public int delCodeFile(CodeFileEnity codeFileEnity){
        return codeFileMapper.delCodeFile(codeFileEnity);
    }

    /*插入二维码*/
    public int insertCodeFile(CodeFileEnity codeFileEnity){
        return codeFileMapper.insertCodeFile(codeFileEnity);
    }

    /*更新二维码*/
    public int updateCodeFile(CodeFileEnity codeFileEnity){
        return codeFileMapper.updateCodeFile(codeFileEnity);
    }

    /*更新密码*/
    public int updatePassWord(String md5, String passWord){
        return codeFileMapper.updatePassWord(md5,passWord);
    }
}
