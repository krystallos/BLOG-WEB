package com.example.fileConfig.mapper;

import com.example.codeFile.enity.CodeFileEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileLookMapper {

	/**
	 * 获取唯一实例
	 * @param codeFileEnity
	 * @return
	 */
	CodeFileEnity get(@Param("codeFileEnity")CodeFileEnity codeFileEnity);

	/**
	 * 查询文件
	 * @param codeFileEnity
	 */
	List<CodeFileEnity> selectFileLookTab(@Param("codeFileEnity") CodeFileEnity codeFileEnity);

}
