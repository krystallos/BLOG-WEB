package com.example.person.mapper;

import com.example.person.enity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonMapper {

	/**
	 * 注册人员
	 * @param person 人员实体类
	 * @return
	 */
	Integer createNewPerson(@Param("person") Person person);

	/**
	 * 查询社区用户
	 * @param uuid
	 * @return
	 */
	List<Person> selectPersonList(@Param("uuid")String uuid);

	/**
	 * 更新社区人员
	 * @param person
	 */
	int updatePerson(@Param("person") Person person);

	/**
	 * 查询社区人员（更多查询条件）
	 */
	List<Person> selectAllListPerson(@Param("person") Person person);

	/**
	 * 获取指定人员
	 */
	Person get(@Param("ids")String ids);
}
