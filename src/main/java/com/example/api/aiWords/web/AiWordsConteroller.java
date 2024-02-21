package com.example.api.aiWords.web;

import com.example.aiWordsApi.service.AiWordsService;
import com.example.api.aiWords.enity.AiWordsChild;
import com.example.api.aiWords.enity.AiWordsFather;
import com.example.api.aiWords.enity.AiWordsPage;
import com.example.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AI词汇控制页
 * @author Enoki
 */
@RestController
public class AiWordsConteroller {

    private static final Logger log = Logger.getLogger(AiWordsConteroller.class);

    @Resource
    private AiWordsService aiWordsService;
    @Resource
    private requestUTF requestUTF;

    /*获取顶级词汇*/
    @PostMapping("aiFixedDicName.act")
    public void aiFatherDicName(HttpServletRequest request, HttpServletResponse response, String type){
        try {
            requestUTF.uTFonE(request, response);
            List<AiWordsPage> item = aiWordsService.selectTabType(type);
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    /*获取二级TAB词汇*/
    @PostMapping("aiFixedTabDicChi.act")
    public void aiFixedTabDicChi(HttpServletRequest request, HttpServletResponse response, String type){
        try {
            requestUTF.uTFonE(request, response);
            AiWordsFather aiWordsFather = new AiWordsFather();
            AiWordsChild aiWordsChild = new AiWordsChild();
            aiWordsFather.setTabType(type);
            List<AiWordsFather> aiWordsFatherList = aiWordsService.selectNoPageFaWords(aiWordsFather);
            for(AiWordsFather item : aiWordsFatherList){
                aiWordsChild.setFatherId(item.getIds());
                item.setAiWordsChildList(aiWordsService.selectNoPageChWords(aiWordsChild));
            }
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

}
