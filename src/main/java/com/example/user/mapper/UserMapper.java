package com.example.user.mapper;

import com.example.user.enity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

	/**
	 * 查询用户
	 * @return
	 */
	List<User> selectUserList();

	/**
	 * 查询指定ID用户
	 * @param uuid
	 * @return
	 */
	User getUser(@Param("uuid")String uuid);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	Integer updateUser(@Param("user") User user);

}
