package com.example.aiWordsApi.service;

import com.example.aiWordsApi.mapper.AiWordsMapper;
import com.example.api.aiWords.enity.AiWordsChild;
import com.example.api.aiWords.enity.AiWordsFather;
import com.example.api.aiWords.enity.AiWordsPage;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * AI词缀页
 * @author Enoki
 */
@Service
public class AiWordsService {

    @Resource
    private AiWordsMapper aiWordsMapper;

    public AiWordsPage get(String ids){
        return aiWordsMapper.get(ids);
    }

    public AiWordsFather getFa(String ids){
        return aiWordsMapper.getFa(ids);
    }

    public AiWordsChild getCh(String ids){
        return aiWordsMapper.getCh(ids);
    }

    public List<AiWordsFather> selectFaTabIdList(){
        return aiWordsMapper.selectFaTabIdList();
    }

    /*带分页查询(父子类)*/
    public List<AiWordsPage> selectPageFaAndChWords(AiWordsPage aiWordsPage){
        PageHelper.offsetPage(aiWordsPage.getStartTab(), aiWordsPage.getHasTab());
        List<AiWordsPage> tab = aiWordsMapper.selectPageFaAndChWords(aiWordsPage);
        return tab;
    }

    /*带分页查询父类*/
    public List<AiWordsPage> selectPageFaWords(AiWordsPage aiWordsPage){
        PageHelper.offsetPage(aiWordsPage.getStartTab(), aiWordsPage.getHasTab());
        List<AiWordsPage> tab = aiWordsMapper.selectPageFaWords(aiWordsPage);
        return tab;
    }

    /*不带分页查询父类*/
    public List<AiWordsFather> selectNoPageFaWords(AiWordsFather aiWordsFather){
        List<AiWordsFather> tab = aiWordsMapper.selectNoPageFaWords(aiWordsFather);
        return tab;
    }

    /*带分页查询子类*/
    public List<AiWordsPage> selectPageChWords(AiWordsPage aiWordsPage){
        PageHelper.offsetPage(aiWordsPage.getStartTab(), aiWordsPage.getHasTab());
        List<AiWordsPage> tab = aiWordsMapper.selectPageChWords(aiWordsPage);
        return tab;
    }

    /*不带分页查询子类*/
    public List<AiWordsChild> selectNoPageChWords(AiWordsChild aiWordsChild){
        List<AiWordsChild> tab = aiWordsMapper.selectNoPageChWords(aiWordsChild);
        return tab;
    }

    /*查询添加/修改时候去重的子类*/
    public List<AiWordsChild> selectDoubleChWords(AiWordsPage aiWordsPage){
        List<AiWordsChild> tab = aiWordsMapper.selectDoubleChWords(aiWordsPage);
        return tab;
    }

    /*查询添加/修改时候去重的父类*/
    public List<AiWordsFather> selectDoubleFaWords(AiWordsPage aiWordsPage){
        List<AiWordsFather> tab = aiWordsMapper.selectDoubleFaWords(aiWordsPage);
        return tab;
    }

    /*查询顶级树分支*/
    public List<AiWordsPage> selectTabType(String dicType){
        return aiWordsMapper.selectTabType(dicType);
    }

    /*查询顶级树分支（列表）*/
    public List<AiWordsPage> findSelectTab(AiWordsPage aiWordsPage){
        return aiWordsMapper.findSelectTab(aiWordsPage);
    }

    /*更新词库（父）*/
    public int updateAiWordsFa(AiWordsPage aiWordsPage){
        return aiWordsMapper.updateAiWordsFa(aiWordsPage);
    }

    /*更新词库（子）*/
    public int updateAiWordsCh(AiWordsPage aiWordsPage){
        return aiWordsMapper.updateAiWordsCh(aiWordsPage);
    }

    /*插入词库（父）*/
    public int insertAiWordsFa(AiWordsPage aiWordsPage){
        return aiWordsMapper.insertAiWordsFa(aiWordsPage);
    }

    /*插入词库（子）*/
    public int insertAiWordsCh(AiWordsPage aiWordsPage){
        return aiWordsMapper.insertAiWordsCh(aiWordsPage);
    }

    /*查询是否存在这个顶级描述*/
    public int selectTabTypeIsNull(String tabType, String dicType){
        return aiWordsMapper.selectTabTypeIsNull(tabType,dicType);
    }

    /*查询顶级排序和父级排序*/
    public Map<String, BigDecimal> selectTabChiSort(int type, String tabType, String dicType){
        return aiWordsMapper.selectTabChiSort(type,tabType,dicType);
    }

    /*删除父级词性*/
    public int delWordsAiFa(String[] ids){
        return aiWordsMapper.delWordsAiFa(ids);
    }

    /*删除子级词性*/
    public int delWordsAiCh(String[] ids){
        return aiWordsMapper.delWordsAiCh(ids);
    }

}
