package com.example.fileConfig.mapper;

import com.example.fileConfig.enity.fiction.FictionTag;
import com.example.util.dic.DicVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FictionUtilMapper {

	/**
	 * 获取唯一实例
	 * @param fictionTag
	 * @return
	 */
	FictionTag get(@Param("fictionTag")FictionTag fictionTag);

	/**
	 * 获取是否唯一名称
	 * @param tagName
	 * @return
	 */
	int selectCountHaving(@Param("tagName") String tagName, @Param("ids")String ids);

	/**
	 * 查询标签
	 * @param fictionTag
	 */
	List<FictionTag> selectFictionUtilTab(@Param("fictionTag") FictionTag fictionTag);

	/**
	 * 字典规范查询不分页的标签
	 * @return
	 */
	List<DicVo> selectFileUtilDicNoTab();

	/**
	 * APP首页能显示的指定标签的分类数量
	 * @param num
	 * @return
	 */
	List<FictionTag> selectFileUtilToNum(@Param("num") int num);

	/**
     * 插入标签路径（未启用存储过程）
	 * @param fictionTag
     * @return
     */
	int insertFictionUtil(@Param("fictionTag") FictionTag fictionTag);


	/**
	 * 更新标签配置
	 * @param fictionTag
	 * @return
	 */
	int updateFictionUtil(@Param("fictionTag")FictionTag fictionTag);

	/**
	 * 删除数据
	 * @param fictionTag
	 * @return
	 */
	int delFictionUtil(@Param("fictionTag")FictionTag fictionTag);

}
