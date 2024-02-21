package com.example.login.mapper;

import com.example.login.enity.GetBackPassWord;
import com.example.login.enity.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RegistMapper {

	/**
	 * 注册用户
	 * @param login 登入实体类
	 * @return
	 */
	Integer createNewUser(@Param("login") Login login);

	/**
	 * 登入用户查重
	 * @param login
	 */
	List<Login> selectProUser(@Param("login") Login login);

	/**
	 * 找回密码
	 * @param getBackPassWord
	 * @return
	 */
	int changePassWord(@Param("getBackPassWord") GetBackPassWord getBackPassWord);

}
