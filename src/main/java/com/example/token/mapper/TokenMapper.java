package com.example.token.mapper;

import com.example.token.enity.token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TokenMapper {

	/**
	 * 获取用户的TOKEN
	 * @param psnId
	 * @param state
	 */
	List<token> selectTokenSession(@Param("psnId")String psnId,@Param("state")String state);

	/**
	 * 保存token到数据库(单人员)
	 * @param token
	 */
	Integer insertTokenSession(@Param("token")token token);

	/**
	 * 注释token（逻辑）
	 * @param token
	 */
	Integer delTokenSession(@Param("token")token token);

}
