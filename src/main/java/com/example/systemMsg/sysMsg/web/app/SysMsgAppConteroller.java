package com.example.systemMsg.sysMsg.web.app;

import com.example.emil.enity.MineEmil;
import com.example.emil.serivce.MineEmilService;
import com.example.fileConfig.enity.FileConfig;
import com.example.fileConfig.service.FileConfigService;
import com.example.person.enity.Person;
import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysMsg.service.SysErrMsgService;
import com.example.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 系统通知APP页
 * @author Enoki
 */
@RestController
public class SysMsgAppConteroller {

    private final Logger logger = LoggerFactory.getLogger(SysMsgAppConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private FileConfigService fileConfigService;//文件服务
    @Resource
    private MineEmilService mineEmilService;//邮件服务
    @Resource
    private SysErrMsgService sysErrMsgService;//错误论坛

    /**
     * 系统消息通知页
     * @param request
     * @param response
     */
    @PostMapping("api/sysMsgAppGet.act")
    public ResultBody sysMsgAppGet(HttpServletRequest request, HttpServletResponse response, @RequestBody Person person){
        try{
            requestUTF.uTFonE(request,response);
            SysMsg sysMsg = new SysMsg();
            //管理员设置
            sysMsg.setIsAdmin("基础用户");
            //文件管理
            FileConfig fileConfig = new FileConfig();
            fileConfig.setPsnId(person.getIds());
            int a = fileConfigService.selectFileConfigInt(fileConfig);
            sysMsg.setIsFile(a>0?"拥有":"未拥有");
            sysMsg.setHasFile(a+"");
            MineEmil mineEmil = new MineEmil();
            mineEmil.setFromPersonId(person.getIds());
            mineEmil.setIntoPersonId(person.getIds());
            mineEmil.setIsAll("1");
            int b = mineEmilService.selectPageNoEmilInt(mineEmil);
            sysMsg.setIsEmail(b>0?"有":"没有");
            sysMsg.setHasEmail(b+"");
            sysMsg.setBlosUrl("hisBlosMain.act?pid=");
            return new ResultBody(ApiResultEnum.SUCCESS, sysMsg);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, "服务连接超时");
        }
    }

    /**
     * 系统BUG通知
     * @param request
     * @param response
     */
    @PostMapping("api/sysMsgAppError.act")
    public ResultBody sysMsgAppError(HttpServletRequest request, HttpServletResponse response, SysErrMsg sysErrMsg){
        try {
            requestUTF.uTFonE(request,response);
            sysErrMsg.pubImplPage(sysErrMsg.getNowTab(),sysErrMsg.getHasTab());
            List<SysErrMsg> tab = sysErrMsgService.selectSysErrMsgTab(sysErrMsg);
            return new ResultBody(ApiResultEnum.SUCCESS, tab);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResultBody(ApiResultEnum.SUCCESS, "服务器连接超时");
        }
    }

}
