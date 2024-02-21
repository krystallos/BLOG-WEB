package com.example.systemMsg.sysInfo.web;

import com.example.job.systemJob.entry.SystemEntry;
import com.example.job.systemJob.service.SystemJobService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * 系统信息
 * @author Enoki
 */
@RestController
public class SysInfoConteroller {

    private static final Logger log = Logger.getLogger(SysInfoConteroller.class);

    @Resource
    private SystemJobService systemJobService;

    /**
     * 查询日志
     */
    @Log(title = "系统信息列表", type = LogEnum.SELECT)
    @PostMapping("api/selectSysInfo.act")
    public ResultBody selectSysInfo(@RequestBody SystemEntry systemEntry) {
        try{
            systemEntry.pubImplPage(systemEntry.getNowTab(), systemEntry.getHasTab());
            List<SystemEntry> list = systemJobService.selectFindList(systemEntry);
            return new ResultBody(ApiResultEnum.SUCCESS, list , (int) new PageInfo<>(list).getTotal());
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 查询近一天的分组
     */
    @Log(title = "近一天内存信息", type = LogEnum.SELECT)
    @PostMapping("api/echartFindList.act")
    public ResultBody echartFindList() {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
            Calendar cal = Calendar.getInstance();
            String strEnd = formatter.format(cal.getTime()).substring(0, 13);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String strBegin = formatter.format(cal.getTime()).substring(0, 13);

            List<SystemEntry> list = systemJobService.selectGroupList(strBegin, strEnd);
            return new ResultBody(ApiResultEnum.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}