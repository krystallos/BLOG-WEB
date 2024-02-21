package com.example.emil.mapper;

import com.example.emil.enity.MineEmil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮件控制页
 * @author Enoki
 */
@Mapper
public interface MineEmilMapper {

    /**
     * 带分页查询
     * @param mineEmil
     * @return
     */
    List<MineEmil> selectPageEmil(@Param("mineEmil") MineEmil mineEmil);
    int selectPageNoEmilInt(@Param("mineEmil") MineEmil mineEmil);

    /**
     * 单条查询
     * @param mineEmil
     * @return
     */
    MineEmil getMineEmil(@Param("mineEmil") MineEmil mineEmil);

    /**
     * 逻辑删除邮件
     * @param mineEmil
     * @return
     */
    int delEmail(@Param("mineEmil") MineEmil mineEmil);

    /**
     * 大批量逻辑处理邮件
     * @param mineEmil
     * @return
     */
    int arrTypeEmail(@Param("mineEmil") String[] mineEmil, @Param("type") int type);

    /**
     * 发送新增邮件
     * @param mineEmil
     * @return
     */
    int insertEmil(@Param("mineEmil") MineEmil mineEmil);

    /**
     * 更新邮件
     * @param mineEmil
     * @return
     */
    int updateEmil(@Param("mineEmil") MineEmil mineEmil);
}
