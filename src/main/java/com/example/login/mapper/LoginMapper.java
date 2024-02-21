package com.example.login.mapper;

import com.example.login.enity.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper {

	/**
	 * 登入
	 * @param uname 用户名
	 * @return
	 */
	List<Login> selectuser(@Param("uname")String uname);

	/**
	 * 查询邮箱
	 * @param email 邮箱
	 * @return
	 */
	Login selectuserOrEmil(@Param("emil")String email);

	/**
	 * 根据人员psnid查询Loginid
	 * @param id 人员id
	 * @return
	 */
	Login get(@Param("id") String id);

}
