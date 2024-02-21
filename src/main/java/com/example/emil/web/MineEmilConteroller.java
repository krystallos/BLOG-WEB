package com.example.emil.web;

import com.example.emil.enity.DelMineEmil;
import com.example.emil.enity.MineEmil;
import com.example.emil.serivce.MineEmilService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 邮件控制页
 * @author Enoki
 */
@RestController
public class MineEmilConteroller {

    private static final Logger log = Logger.getLogger(MineEmilConteroller.class);

    @Resource
    private MineEmilService mineEmilService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private PersonService personService;

    /*分页查询*/
    @Log(title = "我的邮件列表", type = LogEnum.SELECT)
    @PostMapping("api/mineEmilTab.act")
    public ResultBody mineEmilTab(HttpSession session, @RequestBody MineEmil mineEmil){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            mineEmil.pubImplPage(mineEmil.getNowTab(),mineEmil.getHasTab());
            mineEmil.setFromPersonId(person.getIds());
            mineEmil.setIntoPersonId(person.getIds());
            List<MineEmil> tab = mineEmilService.selectPageEmil(mineEmil);
            return new ResultBody(ApiResultEnum.SUCCESS, tab, (int) new PageInfo<>(tab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*指定查询*/
    @Log(title = "邮件详情", type = LogEnum.DETIAL)
    @PostMapping("api/getMineEmil.act")
    public ResultBody getMineEmil(HttpSession httpSession,@RequestBody MineEmil mineEmil){
        try {
            Person personList = (Person)redisUtils.get(httpSession.getId());
            if(null == personList){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID, "您的登入日志已过期");
            }
            //这个操作是做已读更新
            MineEmil minItem = new MineEmil();
            minItem.setIsLook("1");
            minItem.setIds(mineEmil.getIds());
            mineEmilService.updateEmil(minItem);
            MineEmil itemMine = mineEmilService.getMineEmil(mineEmil);
            return new ResultBody(ApiResultEnum.SUCCESS, itemMine);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*可以用作批量删除、删除和批量已读和转为重要文件*/
    @Log(title = "邮件状态转换", type = LogEnum.EDIT)
    @PostMapping("api/typeFlagEmail.act")
    public ResultBody delFlagEmail(@RequestBody DelMineEmil delMineEmil){

        try{
            if(null == delMineEmil || delMineEmil.getMineEmilId().length == 0){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "删除邮件失败，该邮件已不存在");
            }
            mineEmilService.arrDelEmail(delMineEmil.getMineEmilId(),delMineEmil.getType());
            switch (delMineEmil.getType()){
                case 1:
                    return new ResultBody(ApiResultEnum.SUCCESS, "删除邮件成功");
                case 2:
                    return new ResultBody(ApiResultEnum.SUCCESS, "设置已读成功");
                case 3:
                    return new ResultBody(ApiResultEnum.SUCCESS, "设置关注成功");
                default:
                    return new ResultBody(ApiResultEnum.SUCCESS, "操作成功");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * @param httpSession
     * @param mineEmil 里面的type着重处理，1为正常发送，2为草稿箱
     */
    @Log(title = "发送邮件", type = LogEnum.INSERT)
    @PostMapping(value = "api/sendEmail.act")
    public ResultBody sendEmail(HttpSession httpSession,@RequestBody MineEmil mineEmil){
        try {
            //--------------------用来查询发件人员的---------------------
            Person fromPersonList = (Person)redisUtils.get(httpSession.getId());
            //--------------------结束-----------------------------
            //--------------------用来查询收件人员的---------------------
            Person newPerson = new Person();
            newPerson.setEmail(mineEmil.getIntoPersonEmil());
            List<Person> intoPersonList = personService.selectAllListPerson(newPerson);
            //--------------------结束-----------------------------
            String itemMsg = "";
            if(intoPersonList == null || intoPersonList.size() == 0){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "无法找到邮箱使用者");
            }
            if(mineEmil.getType() == 2){//草稿箱
                mineEmil.setIsLook("2");
                itemMsg = "已存为草稿箱";
            }else{//未读
                mineEmil.setIsLook("0");
                itemMsg = "发送成功！";
            }
            mineEmil.setContent(mineEmil.getContent());
            //收件人
            mineEmil.setIntoPersonId(intoPersonList.get(0).getIds());
            mineEmil.setIntoPersonName(intoPersonList.get(0).getPsnName());
            mineEmil.setIntoPersonEmil(intoPersonList.get(0).getEmail());
            //发件人
            mineEmil.setFromPersonName(fromPersonList.getPsnName());
            mineEmil.setFromPersonId(fromPersonList.getIds());
            mineEmil.setFromPersonEmil(fromPersonList.getEmail());
            mineEmil.getUuidCreateUpdate(fromPersonList.getIds());
            mineEmil.getNowDate(null);
            if(mineEmil.getIds()!=null && !"".equals(mineEmil.getIds())){
                mineEmilService.updateEmil(mineEmil);
            }else {
                mineEmil.setIds(rsaKey.uuid(null));
                mineEmilService.insertEmil(mineEmil);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, itemMsg);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}