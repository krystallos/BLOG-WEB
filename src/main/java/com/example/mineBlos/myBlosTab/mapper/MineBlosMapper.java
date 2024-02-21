package com.example.mineBlos.myBlosTab.mapper;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.util.dic.DicListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MineBlosMapper {

	/**
	 * 查询最近操作
	 * @param mineBlos
	 */
	List<MineBlos> selectNvClass(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询最近文章
	 * @param mineBlos
	 * @return
	 */
	List<MineBlos> selectNvBook(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询最近资源更新
	 * @param mineBlos
	 * @return
	 */
	List<MineBlos> selectNvFile(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询最近收藏
	 * @param mineBlos
	 * @return
	 */
	List<MineBlos> selectNvLike(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询文章数量
	 * @param mineBlos
	 * @return
	 */
	Integer countMineBlosSysLook(@Param("mineBlos") MineBlos mineBlos);

	/**
	 * 插入博客数据
	 * @param mineBlos
	 * @return
	 */
	int insertMineBlos(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查看详细博客类型
	 * @param mineBlos
	 * @return
	 */
	MineBlos selectMineBlosSemmIn(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询建立博客的全部类型
	 * @param mineBlos
	 * @return
	 */
	List<MineBlos> selectRightBlos(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询指定博客的类型内数据
	 * @param mineBlos
	 * @return
	 */
	List<MineBlos> mineBlosRightDivLook(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 个人空间查询博客
	 * @param mineBlos
	 * @return
	 */
	List<MineBlos> mineBlosApiDivLook(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询分页总数
	 * @param mineBlos
	 * @return
	 */
	int allBlosRightDivTab(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询个人博客的日志+分类统计
	 * @param mineBlos
	 * @return
	 */
	String mineBlosCount(@Param("mineBlos")MineBlos mineBlos);

	/**
	 * 查询个人博客的时间分组
	 * @return
	 */
	List<DicListVo> mineBlosGroupDate(@Param("personId")String personId);

	/**
	 * 查询博客名称+ID
	 * @param type
	 * @return
	 */
	List<MineBlos> hisBlosType(@Param("type") String type, @Param("ids") String ids);

}
