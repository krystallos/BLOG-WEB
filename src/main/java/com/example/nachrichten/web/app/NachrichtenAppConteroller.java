package com.example.nachrichten.web.app;

import com.example.nachrichten.enity.Nachrichten;
import com.example.nachrichten.service.NachrichtenService;
import com.example.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * APP首页信息通知
 * @author Enoki
 */
@RestController
public class NachrichtenAppConteroller {

    private static final Logger log = Logger.getLogger(NachrichtenAppConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private NachrichtenService nachrichtenService;

    /**
     * 通知列表
     *
     * @param request
     * @param response
     * @param nachrichten 通知实体
     */
    @PostMapping("api/nachrichtenAppList.act")
    public ResultBody nachrichtenAppList(HttpServletRequest request, HttpServletResponse response,@RequestBody Nachrichten nachrichten) {
        try {
            requestUTF.uTFonE(request, response);
            nachrichten.pubImplPage(nachrichten.getNowTab(), nachrichten.getHasTab());
            List<Nachrichten> listTab = nachrichtenService.nachrichtenMineTab(nachrichten);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab);
        } catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
        }
    }

}