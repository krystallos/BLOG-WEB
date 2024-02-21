package com.example.aiWordsApi.web;

import com.example.aiWordsApi.service.AiWordsService;
import com.example.api.aiWords.enity.AiWordsChild;
import com.example.api.aiWords.enity.AiWordsFather;
import com.example.api.aiWords.enity.AiWordsPage;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * AI词汇控制页
 * @author Enoki
 */
@RestController
public class AiWordsInfoConteroller {

    private static final Logger log = Logger.getLogger(AiWordsInfoConteroller.class);

    @Resource
    private AiWordsService aiWordsService;

    /*获取一级词汇*/
    @Log(title = "一级词汇列表", type = LogEnum.SELECT)
    @PostMapping("api/aiFixedDicInfoName.act")
    public ResultBody aiFixedDicInfoName(@RequestBody AiWordsPage aiWordsPage){
        try {
            aiWordsPage.pubImplPage(aiWordsPage.getNowTab(),aiWordsPage.getHasTab());
            List<AiWordsPage> item = aiWordsService.selectPageFaWords(aiWordsPage);
            return new ResultBody(ApiResultEnum.SUCCESS, item, (int) new PageInfo<>(item).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*顶级词汇详情*/
    @Log(title = "一级词汇详情", type = LogEnum.DETIAL)
    @PostMapping("api/aiWordsTop.act")
    public ResultBody aiWordsTop(@RequestBody AiWordsFather aiWordsFather){
        aiWordsFather = aiWordsService.getFa(aiWordsFather.getIds());
        return new ResultBody(ApiResultEnum.SUCCESS, aiWordsFather);
    }

    /*获取二级词汇*/
    @Log(title = "二级词汇列表", type = LogEnum.SELECT)
    @PostMapping("api/aiSecondDicInfoName.act")
    public ResultBody aiSecondDicInfoName(@RequestBody AiWordsPage aiWordsPage){
        try {
            aiWordsPage.pubImplPage(aiWordsPage.getNowTab(),aiWordsPage.getHasTab());
            List<AiWordsPage> item = aiWordsService.selectPageChWords(aiWordsPage);
            return new ResultBody(ApiResultEnum.SUCCESS, item, (int) new PageInfo<>(item).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*二级词汇详情*/
    @Log(title = "二级词汇详情", type = LogEnum.DETIAL)
    @GetMapping("api/aiWordsSecond.act")
    public ResultBody aiWordsSecond(@RequestBody AiWordsChild aiWordsChild){
        aiWordsChild = aiWordsService.getCh(aiWordsChild.getIds());
        return new ResultBody(ApiResultEnum.SUCCESS, aiWordsChild);
    }

    /*二级词汇关联顶级词汇*/
    @Log(title = "二级词汇关联一级词汇列表", type = LogEnum.SELECT)
    @GetMapping("api/aiWordsSecondLinkTop.act")
    public ResultBody aiWordsSecondLinkTop(){
        List<AiWordsFather> item = aiWordsService.selectFaTabIdList();
        return new ResultBody(ApiResultEnum.SUCCESS, item);
    }

    /**
     * 词缀查询
     */
    @Log(title = "词汇查询", type = LogEnum.SELECT)
    @PostMapping("api/apiSelectTab.act")
    public ResultBody apiSelectTab(@RequestBody AiWordsPage aiWordsPage){
        try{
            aiWordsPage.pubImplPage(aiWordsPage.getNowTab(),aiWordsPage.getHasTab());
            List<AiWordsPage> listSize = aiWordsService.selectPageFaAndChWords(aiWordsPage);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*增改词缀*/
    @Log(title = "增改词汇", type = LogEnum.EDIT)
    @PostMapping("api/editAiWordsDate.act")
    public ResultBody editAiWordsDate(@RequestBody AiWordsPage aiWordsPage) {
        try {
            if(aiWordsPage.getAddType() == 1){//父级菜单
                List<AiWordsFather> list = aiWordsService.selectDoubleFaWords(aiWordsPage);
                if(list.size()>0){
                    return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "已存在为[" + list.get(0).getDicFatherName() + "]及["+ list.get(0).getChiName() +"]的词汇!");
                }
                if(aiWordsPage.getFatherId() != null && !aiWordsPage.getFatherId().equals("")){
                    //非空是修改
                    aiWordsService.updateAiWordsFa(aiWordsPage);
                }else{
                    //空是新增
                    aiWordsPage.setFatherId(rsaKey.uuid(""));
                    int typ = aiWordsService.selectTabTypeIsNull(aiWordsPage.getTabType(),aiWordsPage.getDicType());
                    Map<String, BigDecimal> map = aiWordsService.selectTabChiSort(typ,aiWordsPage.getTabType(),aiWordsPage.getDicType());
                    aiWordsPage.setTabSort(map.get("TABSORT").toString());
                    aiWordsPage.setChiSort(map.get("CHISORT").toString());
                    aiWordsService.insertAiWordsFa(aiWordsPage);
                }
                return new ResultBody(ApiResultEnum.SUCCESS, "插入成功！");
            }else if(aiWordsPage.getAddType() == 2){//子级菜单
                List<AiWordsChild> list = aiWordsService.selectDoubleChWords(aiWordsPage);
                if(list.size()>0){
                    return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "已存在为[" + list.get(0).getDicCheName() + "]及["+ list.get(0).getChiName() +"]的词汇!");
                }
                if(aiWordsPage.getIds() != null && !aiWordsPage.getIds().equals("")){
                    //非空是修改
                    aiWordsService.updateAiWordsCh(aiWordsPage);
                }else{
                    //空是新增
                    aiWordsPage.setIds(rsaKey.uuid(""));
                    aiWordsService.insertAiWordsCh(aiWordsPage);
                }
                return new ResultBody(ApiResultEnum.SUCCESS, "插入成功！");
            }else{
                return new ResultBody(ApiResultEnum.DATA_ERROR, "找不到对应的操作记录");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*删除词缀*/
    @Log(title = "密码本首页列表", type = LogEnum.DELETE)
    @PostMapping("api/delWordsAi.act")
    public ResultBody delWordsAi(@RequestBody AiWordsPage aiWordsPage) {
        try {
            String[] ids = new String[]{aiWordsPage.getIds()};
            if(aiWordsPage.getAddType() == 1){//顶级词性
                aiWordsService.delWordsAiFa(ids);
            }else if(aiWordsPage.getAddType() == 2){//子级词性
                aiWordsService.delWordsAiCh(ids);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
