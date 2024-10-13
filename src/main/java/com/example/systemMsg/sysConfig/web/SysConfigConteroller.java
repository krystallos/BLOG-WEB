package com.example.systemMsg.sysConfig.web;

import com.example.systemMsg.sysConfig.enity.EditSystemConfigDicEnity;
import com.example.systemMsg.sysConfig.enity.SystemConfigDicEnity;
import com.example.systemMsg.sysConfig.service.SysConfigService;
import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;


/**
 * 系统初始化配置文件
 * @author Enoki
 */
@RestController
public class SysConfigConteroller {

    private static final Logger log = Logger.getLogger(SysConfigConteroller.class);

    @Value("${fileConfig}")
    private String fileConfigType;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 启动项目自动运行，注入配置到redis
     */
    @PostConstruct
    public void runSystemConfig(){
        SystemConfigDicEnity vo = new SystemConfigDicEnity();
        vo.setIsTest(fileConfigType);
        List<SystemConfigDicEnity> dicVo = sysConfigService.selectConfigDic(vo);
        for(SystemConfigDicEnity fvo : dicVo){
            if(redisUtils.get(fvo.getDicKey()) == null){
                redisUtils.set(fvo.getDicKey(), fvo.getDicValue());
            }
        }
        log.info("配置文件注入结束，当前环境为{ " + fileConfigType + " }，累计导入配置文件 { "+dicVo.size()+" } 条");
    }

    /**
     * 系统BUG通知
     */
    @Log(title = "获取字典列表", type = LogEnum.SELECT)
    @PostMapping("api/sysConfigList.act")
    public ResultBody sysMsgError(@RequestBody SystemConfigDicEnity systemConfigDicEnity){
        try {
            systemConfigDicEnity.pubImplPage(systemConfigDicEnity.getNowTab(),systemConfigDicEnity.getHasTab());
            List<SystemConfigDicEnity> tab = sysConfigService.selectConfigDicFind(systemConfigDicEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, tab, (int) new PageInfo<>(tab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取字典详情
     */
    @Log(title = "获取字典详情", type = LogEnum.SELECT)
    @PostMapping("api/getSysConfig.act")
    public ResultBody getSysConfig(@RequestBody SystemConfigDicEnity systemConfigDicEnity){
        try {
            return new ResultBody(ApiResultEnum.SUCCESS, sysConfigService.getConfigDic(systemConfigDicEnity));
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除字典
     */
    @Log(title = "删除字典", type = LogEnum.DELETE)
    @PostMapping("api/delSysConfig.act")
    public ResultBody delSysConfig(@RequestBody SystemConfigDicEnity systemConfigDicEnity){
        try {
            sysConfigService.delSysConfig(systemConfigDicEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 修改、新增字典
     */
    @Log(title = "修改、新增字典", type = LogEnum.EDIT)
    @PostMapping("api/editSysConfig.act")
    public ResultBody editSysConfig(@RequestBody EditSystemConfigDicEnity systemConfigDicEnity){
        try {
            if(systemConfigDicEnity.getFroms() == null){
                return new ResultBody(ApiResultEnum.INVALID_FLAG, "标识丢失");
            }else if(systemConfigDicEnity.getFroms().equals("add")){
                sysConfigService.insertSysConfig(systemConfigDicEnity);
            }else if(systemConfigDicEnity.getFroms().equals("edit")){
                sysConfigService.editSysConfig(systemConfigDicEnity);
            }else{
                return new ResultBody(ApiResultEnum.DATA_ERROR, "参数错误");
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "操作完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}