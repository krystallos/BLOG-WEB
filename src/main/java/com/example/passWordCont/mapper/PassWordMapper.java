package com.example.passWordCont.mapper;

import com.example.passWordCont.enity.PassWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 密码本实现页
 * @author Enoki
 */
@Mapper
public interface PassWordMapper {

	/**
	 * 密码本查询页
	 * @param passWord
	 */
	List<PassWord> passWordMineSelect(@Param("passWord") PassWord passWord);

	/**
	 * 获取唯一值
	 * @param passWord
	 * @return
	 */
	PassWord get(@Param("passWord") PassWord passWord);

	/**
	 * 插入密码本
	 * @param passWord
	 * @return
	 */
	int insertPassWord(@Param("passWord")PassWord passWord);

	/**
	 * 更新密码本
	 * @param passWord
	 * @return
	 */
	int updatePassWord(@Param("passWord")PassWord passWord);

}
