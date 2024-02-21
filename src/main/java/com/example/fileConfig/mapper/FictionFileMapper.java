package com.example.fileConfig.mapper;

import com.example.fileConfig.enity.fiction.FictionBook;
import com.example.fileConfig.enity.fiction.FictionFile;
import com.example.fileConfig.enity.fiction.FictionFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FictionFileMapper {

	/**
	 * 获取唯一实例
	 * @param fictionFile
	 * @return
	 */
	FictionFile get(@Param("fictionFile")FictionFile fictionFile);

	/**
	 * 获取重复小说数量
	 * @param fictionName
	 * @param fictionNameFt
	 * @return
	 */
	int selectFictionCount(@Param("fictionName") String fictionName,@Param("fictionNameFt") String fictionNameFt, @Param("ids")String ids);

	/**
	 * 查询小说
	 * @param fictionFile
	 */
	List<FictionFile> selectFictionFileTab(@Param("fictionFile") FictionFile fictionFile);

	/**
	 * 获取APP端首页指定标签的前N本书
	 * @return
	 */
	List<FictionFile> selectRecommend(@Param("num")int num,@Param("tagId") String tagId);

	/**
	 * 获取APP端首页最近更新的前N本书
	 * @param num
	 * @return
	 */
	List<FictionFile> selectRecommendForDate(@Param("num")int num);

	/**
	 * 统计所有的小说数量
	 * @param fictionFile
	 * @return
	 */
	int selectAllCountFiction(@Param("fictionFile") FictionFile fictionFile);

	/**
	 * 统计所有的小说书本数量
	 * @param fictionFile
	 * @return
	 */
	int selectAllCountBook(@Param("fictionFile") FictionFile fictionFile);

	/**
     * 插入文件路径（未启用存储过程）
	 * @param fictionFile
     * @return
     */
	int insertFictionFile(@Param("fictionFile") FictionFile fictionFile);


	/**
	 * 更新文件配置
	 * @param fictionFile
	 * @return
	 */
	int updateFictionFile(@Param("fictionFile")FictionFile fictionFile);

	/**
	 * 删除数据
	 * @param fictionFile
	 * @return
	 */
	int delFictionFile(@Param("fictionFile")FictionFile fictionFile);

	/**
	 * 查询指定书籍
	 */
	FictionBook getFictionBook(@Param("fictionBook") FictionBook fictionBook);

	/**
	 * 查询书籍
	 * @param fictionBook
	 */
	List<FictionBook> selectFictionMainTab(@Param("fictionBook") FictionBook fictionBook);

	/**
	 * 插入书籍路径（未启用存储过程）
	 * @param fictionBook
	 * @return
	 */
	int insertFictionMain(@Param("fictionBook") FictionBook fictionBook);

	/**
	 * 删除数据
	 * @param fictionBook
	 * @return
	 */
	int delFictionMain(@Param("fictionBook")FictionBook fictionBook);

	/**
	 * 获取书籍封面
	 * @param fictionFile
	 * @return
	 */
	String lookBookMainCover(@Param("fictionFile")FictionFile fictionFile);
}
