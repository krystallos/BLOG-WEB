package com.example.systemMsg.sysMsg.web;

import com.example.emil.enity.MineEmil;
import com.example.emil.serivce.MineEmilService;
import com.example.fileConfig.enity.FileConfig;
import com.example.fileConfig.enity.fiction.FictionFile;
import com.example.fileConfig.service.FictionFileService;
import com.example.fileConfig.service.FileConfigService;
import com.example.person.enity.Person;
import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysMsg.enity.SysSayErrMsg;
import com.example.systemMsg.sysMsg.service.SysErrMsgService;
import com.example.systemMsg.sysMsg.service.SysSayErrMsgService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统通知页
 * @author Enoki
 */
@RestController
public class SysMsgConteroller {

    private static final Logger log = Logger.getLogger(SysMsgConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private FileConfigService fileConfigService;//文件服务
    @Resource
    private MineEmilService mineEmilService;//邮件服务
    @Resource
    private SysErrMsgService sysErrMsgService;//错误论坛
    @Resource
    private FictionFileService fictionFileService;//小说
    @Resource
    private SysSayErrMsgService sysSayErrMsgService;

    @Value("${assessUrlBlos}")
    private String assessUrlBlos;

    /**
     * 系统消息通知页
     * @param session
     */
    @Log(title = "个人信息统计页面", type = LogEnum.SELECT)
    @PostMapping("api/mineSysMsgGet.act")
    public ResultBody mineSysMsgGet(HttpSession session){
        try{
            SysMsg sysMsg = new SysMsg();
            Person personId = (Person)redisUtils.get(session.getId());
            //管理员设置
            if(personId==null){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID, "用户鉴权失败，请重新登入");
            }else if(personId.getIds().equals("1")){
                sysMsg.setIsAdmin("管理员");
            } else {
                sysMsg.setIsAdmin("用户");
            }
            sysMsg.setIds(personId.getIds());
            sysMsg.setName(personId.getPsnName());
            //文件管理
            FileConfig fileConfig = new FileConfig();
            fileConfig.setPsnId(sysMsg.getIds());
            int a = fileConfigService.selectFileConfigInt(fileConfig);
            sysMsg.setHasFile(a+"");
            FictionFile fictionFile = new FictionFile();
            fictionFile.setCreateId(personId.getIds());
            int bookLen = fictionFileService.selectAllCountBook(fictionFile);
            sysMsg.setHasBook(bookLen + "");
            MineEmil mineEmil = new MineEmil();
            mineEmil.setFromPersonId(sysMsg.getIds());
            mineEmil.setIntoPersonId(sysMsg.getIds());
            int b = mineEmilService.selectPageNoEmilInt(mineEmil);
            sysMsg.setHasEmail(b+"");
            sysMsg.setBlosUrl(assessUrlBlos + "#/mineBlos?ids=" + personId.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, sysMsg);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 系统BUG通知
     */
    @Log(title = "系统BUG列表", type = LogEnum.SELECT)
    @PostMapping("api/sysMsgError.act")
    public ResultBody sysMsgError(@RequestBody SysErrMsg sysErrMsg){
        try {
            sysErrMsg.pubImplPage(sysErrMsg.getNowTab(),sysErrMsg.getHasTab());
            List<SysErrMsg> tab = sysErrMsgService.selectSysErrMsgTab(sysErrMsg);
            return new ResultBody(ApiResultEnum.SUCCESS, tab, (int) new PageInfo<>(tab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * BUG选单
     * @param sysErrMsg
     */
    @Log(title = "系统BUG详情", type = LogEnum.DETIAL)
    @PostMapping("api/getErrBug.act")
    public ResultBody getErrBug(@RequestBody SysErrMsg sysErrMsg){
        try {
            sysErrMsg = sysErrMsgService.get(sysErrMsg);
            SysSayErrMsg sysSayErrMsg = new SysSayErrMsg();
            sysSayErrMsg.setErrId(sysErrMsg.getIds());
            List<SysSayErrMsg> sysSayErrMsgList = sysSayErrMsgService.selectSysSayErrMsg(sysSayErrMsg);
            Map<String, Object> item = new HashMap<>();
            item.put("sysErrMsg", sysErrMsg);
            item.put("sysSayErrMsg", sysSayErrMsgList);
            return new ResultBody(ApiResultEnum.SUCCESS, item);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 提交bug
     * @param session
     * @param sysErrMsg
     */
    @Log(title = "提交BUG", type = LogEnum.INSERT)
    @PostMapping("api/upErrFromBug.act")
    public ResultBody upErrFromBug(HttpSession session,@RequestBody SysErrMsg sysErrMsg){
        try {
            Person personList = (Person) redisUtils.get(session.getId());
            sysErrMsg.setAdminBack("");
            sysErrMsg.setAdminCenter("");
            sysErrMsg.setErrPsnId(personList.getIds());
            sysErrMsg.setErrPsnName(personList.getPsnName());
            sysErrMsg.getUuidCreateUpdate(personList.getIds());
            sysErrMsgService.insetSysErrMsg(sysErrMsg);
            return new ResultBody(ApiResultEnum.SUCCESS, "提交成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 撤回bug
     * @param session
     * @param sysErrMsg
     */
    @Log(title = "撤回BUG", type = LogEnum.DELETE)
    @PostMapping("api/backErrFromAdd.act")
    public ResultBody backErrFromAdd(HttpSession session,@RequestBody SysErrMsg sysErrMsg){
        try {
            Person personList = (Person) redisUtils.get(session.getId());
            sysErrMsg.setErrPsnId(personList.getIds());
            sysErrMsg.setIsOk("已撤回");
            int a = 0;
            a += sysErrMsgService.updateSyeErrMsg(sysErrMsg);
            if(a>0){
                return new ResultBody(ApiResultEnum.SUCCESS, "撤回成功"+a+"条信息");
            }else{
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "撤回失败，请确定这条BUG是否归属于自己");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 提交bug评论
     * @param session
     * @param sysSayErrMsg
     */
    @Log(title = "发表BUG评论", type = LogEnum.INSERT)
    @PostMapping("api/sayErrFromBug.act")
    public ResultBody sayErrFromBug(HttpSession session,@RequestBody SysSayErrMsg sysSayErrMsg){
        try {
            Person personList = (Person) redisUtils.get(session.getId());
            sysSayErrMsg.setSayPsnId(personList.getIds());
            sysSayErrMsg.setSayPsnName(personList.getPsnName());
            sysSayErrMsg.getUuidCreateUpdate(personList.getIds());
            //如果是ADMIN、即开发者回复就算解决
            if(personList.getIds().equals("1")){
                sysErrMsgService.updateOkSysErrMsg(sysSayErrMsg.getErrId());
            }
            sysSayErrMsgService.insertSysSayErrMsg(sysSayErrMsg);
            return new ResultBody(ApiResultEnum.SUCCESS, "评论成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * BUG评论获取
     * @param sysErrMsg
     */
    @Log(title = "获取BUG评论列表", type = LogEnum.SELECT)
    @PostMapping("api/getSayErrFromBug.act")
    public ResultBody getSayErrFromBug(@RequestBody SysErrMsg sysErrMsg){
        try {
            SysSayErrMsg sysSayErrMsg = new SysSayErrMsg();
            sysSayErrMsg.setErrId(sysErrMsg.getIds());
            List<SysSayErrMsg> sysSayErrMsgList = sysSayErrMsgService.selectSysSayErrMsg(sysSayErrMsg);
            return new ResultBody(ApiResultEnum.SUCCESS, sysSayErrMsgList);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
