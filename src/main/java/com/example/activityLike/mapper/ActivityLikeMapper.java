package com.example.activityLike.mapper;

import com.example.activityLike.enity.ActivityLike;
import com.example.emil.enity.MineEmil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮件控制页
 * @author Enoki
 */
@Mapper
public interface ActivityLikeMapper {

    /**
     * 带分页查询
     * @param activityLike
     * @return
     */
    List<ActivityLike> selectPageLike(@Param("activityLike") ActivityLike activityLike);


    /**
     * 单条查询
     * @param ids
     * @return
     */
    ActivityLike getActivityLike(@Param("ids") String ids);

    /**
     * 新增收藏
     * @param activityLike
     * @return
     */
    int insertLike(@Param("activityLike") ActivityLike activityLike);

    /**
     * 更新收藏
     * @param activityLike
     * @return
     */
    int updateLike(@Param("activityLike") ActivityLike activityLike);
}
