package com.example.fileConfig.mapper;

import com.example.fileConfig.enity.imageAlbum.GetImageAlbumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageAlbumMapper {

	/**
	 * 获取唯一实例
	 * @param getImageAlbumVo
	 * @return
	 */
	GetImageAlbumVo get(@Param("getImageAlbumVo")GetImageAlbumVo getImageAlbumVo);

	/**
	 * 获取分页文件
	 * @param getImageAlbumVo
	 * @return
	 */
	List<GetImageAlbumVo> selectImageAlbum(@Param("getImageAlbumVo")GetImageAlbumVo getImageAlbumVo);

	/**
	 * 新增文件
	 * @param getImageAlbumVo
	 * @return
	 */
	int insertImageAlbum(@Param("getImageAlbumVo")GetImageAlbumVo getImageAlbumVo);

	/**
	 * 更新文件
	 * @param getImageAlbumVo
	 * @return
	 */
	int updateImageAlbum(@Param("getImageAlbumVo")GetImageAlbumVo getImageAlbumVo);

	/**
	 * 收藏、取消收藏文件
	 * @param ids
	 * @param isLike
	 * @return
	 */
	int likeImageAlbum(@Param("ids")String ids, @Param("isLike")String isLike);

	/**
	 * 删除文件
	 * @param ids
	 * @return
	 */
	int delImageAlbum(@Param("ids")String ids);

	/**
	 * 获取相册时间轴
	 * @param personId
	 * @return
	 */
	List<GetImageAlbumVo> selectImageAlbumGroupTime(@Param("personId")String personId);

}
