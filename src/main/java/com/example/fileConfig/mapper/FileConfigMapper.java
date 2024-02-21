package com.example.fileConfig.mapper;

import com.example.api.randomImg.enity.FileRandom;
import com.example.api.randomImg.enity.FileScore;
import com.example.api.randomImg.enity.GetFileRandom;
import com.example.fileConfig.enity.*;
import com.example.fileConfig.enity.authorEnity.PixivAuthor;
import com.example.fileConfig.enity.pixivEnity.PixivHasUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileConfigMapper {

	FileDetial selectDetialImage(@Param("fileUnit") FileUnit fileUnit);

	/**
	 * 查询文件
	 * @param fileConfig
	 */
	int selectFileConfigCount(@Param("fileConfig") FileConfig fileConfig);
	List<FileConfig> selectLikeConfig(@Param("fileConfig") FileConfig fileConfig);
	List<FileGroupFa> selectFileConfigGroup(@Param("fileConfig") FileConfig fileConfig);
	int selectLikeConfigInt(@Param("fileConfig") FileConfig fileConfig);
	int countSelectFileConfigGroup(@Param("fileConfig") FileConfig fileConfig);

	/**
	 * 批量ID查询图片(约束图片)
	 * @param ids
	 */
	List<FileUtil> selectIdListImg(@Param("ids") String ids);

	/**
	 * 查询ID的图片路径
	 * @param ids
	 * @return
	 */
	List<String> selectListPathImg(@Param("ids") String[] ids);

	/**
	 * 随机查询图片(约束图片)
	 * @param getFileRandom
	 */
	List<FileRandom> selectRandomImg(@Param("getFileRandom") GetFileRandom getFileRandom);

	Map<String, Object> selectRandomScore(@Param("imageId") String imageId);

	/**
	 * 插入文件（未启用存储过程）
	 * @param fileConfig
	 * @return
	 */
	int insertFileConfig(@Param("fileConfig") FileConfig fileConfig);

	/**
	 * 插入文件（大批量版）
	 * @param msg
	 * @return
	 */
	int insertFileAllConfig(@Param("msg") List<FileConfig> msg);

	/**
	 * 删除文件（大批量版）
	 * @param msg
	 * @return
	 */
	int delFlagFileAllConfig(@Param("msg") String[] msg,@Param("psnId") String psnId);

	/**
	 * 删除文件(物理)
	 * @param fileConfig
	 * @return
	 */
	int delPsnImg(@Param("fileConfig") FileConfig fileConfig);

	/**
	 * 查询相似度图片
	 * @param fileConfig
	 * @return
	 */
	List<FileConfig> selectLikeDouble(@Param("fileConfig") FileConfig fileConfig);

	/**
	 * 查询错误页面的图片
	 * @return
	 */
	List<FileConfig> selectErrImg();

	/**
	 * 新增评价
	 * @return
	 */
	int insertFileScore(@Param("fileScore") FileScore fileScore);

	/**
	 * 访客新增图片标签
	 * @param fileRandom
	 * @return
	 */
	int updateImageTag(@Param("fileRandom") FileRandom fileRandom);

	/**
	 * 系统新增访客标签
	 * @param fileConfig
	 * @return
	 */
	int insertTag(@Param("fileConfig") FileConfig fileConfig);

	List<PixivHasUrl> selectGroupAuthPid(@Param("voReturn") FileConfig voReturn);

	String selectGroupAuthUid(@Param("pixivHasUrl") PixivHasUrl pixivHasUrl);

	String selectHasAuthor(@Param("authorId") String authorId);

	int insertAuthor(@Param("pixivAuthor") PixivAuthor pixivAuthor);

	int updateAuthor(@Param("pixivAuthor") PixivAuthor pixivAuthor);

	List<FileUnit> selectGroupLissImagePid(@Param("pid") String pid);

	int updateSync(@Param("ids") String ids, @Param("authorIds") String authorIds);

}
