package com.example.api.openImageAlbum;

import com.example.fileConfig.enity.imageAlbum.GetImageAlbumVo;
import com.example.fileConfig.service.ImageAlbumService;
import com.example.login.enity.Login;
import com.example.login.service.LoginService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.rsaKey;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 云相册公开接口
 * @author Enoki
 */
@RestController
public class OpenImageAlbumConteroller {

    private static final Logger log = Logger.getLogger(OpenImageAlbumConteroller.class);

    @Resource
    private LoginService loginService;
    @Resource
    private PersonService personService;
    @Resource
    private ImageAlbumService imageAlbumService;

    /*账号验证*/
    @Log(title = "相册账号验证", type = LogEnum.SELECT)
    @PostMapping("open/selectUser.act")
    public ResultBody selectUser(@RequestBody Login login){
        try{
            List<Login> userList = loginService.LoginUser(login.getLogname());
            if(userList.size()==0){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA,"账号或密码错误");
            }else if(userList.size()==1){
                String returnKey = rsaKey.setKeyBtye(userList.get(0).getUserPassWord());
                if(returnKey.equals(login.getLogpass())){
                    List<Person> personList = personService.selectListPerson(userList.get(0).getIds());
                    return new ResultBody(ApiResultEnum.SUCCESS, "登入成功", personList.get(0).getIds());
                }
            }
            return new ResultBody(ApiResultEnum.FAIL, "账号或密码错误");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*获取相册时间轴*/
    @Log(title = "获取相册时间轴", type = LogEnum.SELECT)
    @PostMapping("open/selectImageGroupTime.act")
    public ResultBody selectImageGroupTime(@RequestBody Login login){
        try{
            List<GetImageAlbumVo> vo = imageAlbumService.selectImageAlbumGroupTime(login.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, vo);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*获取相册时间轴*/
    @Log(title = "获取对应时间的相册", type = LogEnum.SELECT)
    @PostMapping("open/selectImageGroupAlbum.act")
    public ResultBody selectImageGroupAlbum(@RequestBody GetImageAlbumVo getImageAlbumVo){
        try{
            getImageAlbumVo.pubImplPage(getImageAlbumVo.getNowTab(),getImageAlbumVo.getHasTab());
            List<GetImageAlbumVo> itemPath = imageAlbumService.selectImageAlbum(getImageAlbumVo);
            return new ResultBody(ApiResultEnum.SUCCESS, itemPath, (int) new PageInfo<>(itemPath).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
