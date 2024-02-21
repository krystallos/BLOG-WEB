package com.example.aiWordsApi.mapper;

import com.example.api.aiWords.enity.AiWordsChild;
import com.example.api.aiWords.enity.AiWordsFather;
import com.example.api.aiWords.enity.AiWordsPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 邮件控制页
 * @author Enoki
 */
@Mapper
public interface AiWordsMapper {

    /**
     * 获取唯一值
     * @param ids
     * @return
     */
    AiWordsPage get(@Param("ids") String ids);

    /**
     * 获取父级唯一值
     * @param ids
     * @return
     */
    AiWordsFather getFa(@Param("ids") String ids);

    /**
     * 获取子级唯一值
     * @param ids
     * @return
     */
    AiWordsChild getCh(@Param("ids") String ids);

    /**
     *  带分页查询列表
     * @param aiWordsPage
     * @return
     */
    List<AiWordsPage> selectPageFaAndChWords(AiWordsPage aiWordsPage);

    /**
     * 分页查询父类
     * @param aiWordsPage
     * @return
     */
    List<AiWordsPage> selectPageFaWords(AiWordsPage aiWordsPage);

    /**
     * 不分页查询父类
     * @param aiWordsFather
     * @return
     */
    List<AiWordsFather> selectNoPageFaWords(AiWordsFather aiWordsFather);

    /**
     * 不分页查询子类
     * @param aiWordsChild
     * @return
     */
    List<AiWordsChild> selectNoPageChWords(AiWordsChild aiWordsChild);

    /**
     * 分页查询子类
     * @param aiWordsPage
     * @return
     */
    List<AiWordsPage> selectPageChWords(AiWordsPage aiWordsPage);

    /**
     * 查询顶级树ID+名称
     * @return
     */
    List<AiWordsFather> selectFaTabIdList();

    /**
     * 查询顶级树分支
     * @param dicType
     * @return
     */
    List<AiWordsPage> selectTabType(@Param("dicType") String dicType);

    List<AiWordsPage> findSelectTab(AiWordsPage aiWordsPage);

    /**
     * 更新词库(父)
     * @param aiWordsPage
     * @return
     */
    int updateAiWordsFa(AiWordsPage aiWordsPage);

    /**
     * 更新词库(子)
     * @param aiWordsPage
     * @return
     */
    int updateAiWordsCh(AiWordsPage aiWordsPage);

    /**
     * 插入词库(父)
     * @param aiWordsPage
     * @return
     */
    int insertAiWordsFa(AiWordsPage aiWordsPage);

    /**
     * 插入词库(子)
     * @param aiWordsPage
     * @return
     */
    int insertAiWordsCh(AiWordsPage aiWordsPage);

    /**
     * 查询是否存在这个顶级描述
     * @param tabType
     * @param dicType
     * @return
     */
    int selectTabTypeIsNull(@Param("tabType")String tabType, @Param("dicType")String dicType);

    /**
     * 查询顶级排序和父级排序
     * @param type
     * @param tabType
     * @param dicType
     * @return
     */
    Map<String, BigDecimal> selectTabChiSort(@Param("type") int type, @Param("tabType") String tabType, @Param("dicType") String dicType);

    /**
     * 删除父级词性
     * @param ids
     * @return
     */
    int delWordsAiFa(String[] ids);

    /**
     * 删除子级词性
     * @param ids
     * @return
     */
    int delWordsAiCh(String[] ids);

    /**
     * 查询添加/修改时候去重的子类
     * @param aiWordsPage
     * @return
     */
    List<AiWordsChild> selectDoubleChWords(AiWordsPage aiWordsPage);

    /**
     * 查询添加/修改时候去重的父类
     * @param aiWordsPage
     * @return
     */
    List<AiWordsFather> selectDoubleFaWords(AiWordsPage aiWordsPage);
}
