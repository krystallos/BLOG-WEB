package com.example.codeFile.mapper;

import com.example.codeFile.enity.CodeFileEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 二维码控制页
 * @author Enoki
 */
@Mapper
public interface CodeFileMapper {

    /**
     * 带分页查询
     * @param codeFileEnity
     * @return
     */
    List<CodeFileEnity> selectPageCodeFile(@Param("codeFileEnity") CodeFileEnity codeFileEnity);

    /**
     * 单条查询
     * @param codeFileEnity
     * @return
     */
    CodeFileEnity getCodeFile(@Param("codeFileEnity") CodeFileEnity codeFileEnity);

    /**
     * 逻辑删除邮件
     * @param codeFileEnity
     * @return
     */
    int delCodeFile(@Param("codeFileEnity") CodeFileEnity codeFileEnity);

    /**
     * 新增二维码
     * @param codeFileEnity
     * @return
     */
    int insertCodeFile(@Param("codeFileEnity") CodeFileEnity codeFileEnity);

    /**
     * 更新二维码
     * @param codeFileEnity
     * @return
     */
    int updateCodeFile(@Param("codeFileEnity") CodeFileEnity codeFileEnity);

    /**
     * 更新密码
     * @param md5
     * @param passWord
     * @return
     */
    int updatePassWord(@Param("md5")String md5, @Param("passWord")String passWord);

}
