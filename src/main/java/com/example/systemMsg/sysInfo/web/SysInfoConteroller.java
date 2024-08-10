package com.example.systemMsg.sysInfo.web;

import com.example.job.systemJob.entry.DirSystemEntry;
import com.example.job.systemJob.entry.SystemConfigEnerty;
import com.example.job.systemJob.entry.SystemEntry;
import com.example.job.systemJob.entry.SystemNetWork;
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
import java.net.InetAddress;
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
     * 获取系统信息
     */
    @Log(title = "系统信息详情", type = LogEnum.SELECT)
    @PostMapping("api/getSystemConfig.act")
    public ResultBody getSystemConfig(@RequestBody SystemConfigEnerty systemConfigEnerty) {
        try{
            systemConfigEnerty.setIp(InetAddress.getLocalHost().getHostAddress());
            return new ResultBody(ApiResultEnum.SUCCESS, systemJobService.getSystemConfig(systemConfigEnerty));
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取系统内存信息
     */
    @Log(title = "系统内存详情", type = LogEnum.SELECT)
    @PostMapping("api/getSystemMerony.act")
    public ResultBody getSystemMerony(@RequestBody SystemEntry systemEntry) {
        try{
            return new ResultBody(ApiResultEnum.SUCCESS, systemJobService.selectSystem(systemEntry));
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 查询磁盘信息
     */
    @Log(title = "查询磁盘信息", type = LogEnum.SELECT)
    @PostMapping("api/selectDic.act")
    public ResultBody selectDic(@RequestBody DirSystemEntry dirSystemEntry) {
        try{
            dirSystemEntry.pubImplPage(dirSystemEntry.getNowTab(), dirSystemEntry.getHasTab());
            List<DirSystemEntry> list = systemJobService.selectDic(dirSystemEntry);
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
    public ResultBody echartFindList(@RequestBody SystemEntry systemEntry) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
            Calendar cal = Calendar.getInstance();
            String strEnd = formatter.format(cal.getTime()).substring(0, 13);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String strBegin = formatter.format(cal.getTime()).substring(0, 13);
            List<SystemEntry> list = systemJobService.selectGroupList(strBegin, strEnd, systemEntry.getSystemId());
            return new ResultBody(ApiResultEnum.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 查询近一天的分组
     */
    @Log(title = "近一天网络信息", type = LogEnum.SELECT)
    @PostMapping("api/echartNetWorkList.act")
    public ResultBody echartNetWorkList(@RequestBody SystemEntry systemEntry) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
            Calendar cal = Calendar.getInstance();
            String strEnd = formatter.format(cal.getTime()).substring(0, 13);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String strBegin = formatter.format(cal.getTime()).substring(0, 13);
            List<SystemNetWork> list = systemJobService.selectGroupNetWork(strBegin, strEnd, systemEntry.getSystemId());
            return new ResultBody(ApiResultEnum.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}