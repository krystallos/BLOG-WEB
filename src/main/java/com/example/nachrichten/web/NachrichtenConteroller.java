package com.example.nachrichten.web;

import com.example.nachrichten.enity.Nachrichten;
import com.example.nachrichten.service.NachrichtenService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * APP首页信息通知
 * @author Enoki
 */
@RestController
public class NachrichtenConteroller {

    private static final Logger log = Logger.getLogger(NachrichtenConteroller.class);

    @Resource
    private NachrichtenService nachrichtenService;

    /**
     * 获取详情
     * @param nachrichten
     */
    @Log(title = "获取通知详细信息", type = LogEnum.DETIAL)
    @PostMapping("api/getNachrichten.act")
    public ResultBody getNachrichten(@RequestBody Nachrichten nachrichten){
        try {
            nachrichten = nachrichtenService.get(nachrichten);
            return new ResultBody(ApiResultEnum.SUCCESS, nachrichten);
        }catch (Exception e){
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 通知列表
     * @param nachrichten 通知实体
     */
    @Log(title = "获取通知列表", type = LogEnum.SELECT)
    @PostMapping("api/nachrichtenList.act")
    public ResultBody nachrichtenList(@RequestBody Nachrichten nachrichten) {
        try {
            nachrichten.pubImplPage(nachrichten.getNowTab(), nachrichten.getHasTab());
            List<Nachrichten> listTab = nachrichtenService.nachrichtenMineTab(nachrichten);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        } catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 修改
     * @param nachrichten
     */
    @Log(title = "修改通知信息", type = LogEnum.EDIT)
    @PostMapping("api/updateNachrichten.act")
    public ResultBody updateNachrichten(@RequestBody Nachrichten nachrichten){
        try {
            nachrichtenService.updateNachrichten(nachrichten);
            return new ResultBody(ApiResultEnum.SUCCESS, "更新成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 新增
     * @param nachrichten
     */
    @Log(title = "新增通知信息", type = LogEnum.INSERT)
    @PostMapping("api/insertNachrichten.act")
    public ResultBody insertNachrichten(@RequestBody Nachrichten nachrichten){
        try {
            nachrichtenService.insertNachrichten(nachrichten);
            return new ResultBody(ApiResultEnum.SUCCESS, "提交成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除
     * @param nachrichten
     */
    @Log(title = "删除通知信息", type = LogEnum.DELETE)
    @PostMapping("api/delNachrichten.act")
    public ResultBody delNachrichten(@RequestBody Nachrichten nachrichten){
        try {
            nachrichtenService.delNachrichten(nachrichten);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}