package com.example.fileConfig.web;

import com.example.fileConfig.enity.imageAlbum.GetImageAlbumVo;
import com.example.fileConfig.service.ImageAlbumService;
import com.example.person.enity.Person;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * 云相册控制页
 * @author Enoki
 */
@RestController
public class ImageAlbumConteroller {

    private static final Logger log = Logger.getLogger(ImageAlbumConteroller.class);

    @Resource
    private ImageAlbumService imageAlbumService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取相册列表
     */
    @Log(title = "获取相册列表", type = LogEnum.SELECT)
    @PostMapping("api/selectImageAlbum.act")
    public ResultBody selectImageAlbum(HttpSession session , @RequestBody GetImageAlbumVo vo){
        try{
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            vo.setCreateId(person.getIds());
            vo.pubImplPage(vo.getNowTab(),vo.getHasTab());
            List<GetImageAlbumVo> itemPath = imageAlbumService.selectImageAlbum(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, itemPath, (int) new PageInfo<>(itemPath).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 更新照片信息
     */
    @Log(title = "更新照片信息", type = LogEnum.EDIT)
    @PostMapping("api/editImageAlbum.act")
    public ResultBody editImageAlbum(@RequestBody GetImageAlbumVo vo){
        try{
            imageAlbumService.updateImageAlbum(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, "更新完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 收藏、取消收藏照片信息
     */
    @Log(title = "更新照片信息", type = LogEnum.EDIT)
    @PostMapping("api/likeImageAlbum.act")
    public ResultBody likeImageAlbum(@RequestBody GetImageAlbumVo vo){
        try{
            imageAlbumService.likeImageAlbum(vo);
            return new ResultBody(ApiResultEnum.SUCCESS, "更新完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除照片信息
     */
    @Log(title = "删除照片信息", type = LogEnum.DELETE)
    @PostMapping("api/delImageAlbum.act")
    public ResultBody delImageAlbum(@RequestBody GetImageAlbumVo vo){
        try{
            GetImageAlbumVo getImageAlbumVo = imageAlbumService.get(vo);
            imageAlbumService.delImageAlbum(getImageAlbumVo.getIds());
            File image = new File(redisUtils.getConfig(ConfigDicEnum.imageAlbum.message) + getImageAlbumVo.getImagePath() + "/" + getImageAlbumVo.getImageName() + "." + getImageAlbumVo.getFileType());
            File imageThum = new File(redisUtils.getConfig(ConfigDicEnum.imageAlbumThumbanil.message) + getImageAlbumVo.getImagePath() + "/" + getImageAlbumVo.getImageName() + "." + getImageAlbumVo.getFileType());
            boolean isIm = image.delete();
            boolean isImth = imageThum.delete();
            if(isIm && isImth) {
                return new ResultBody(ApiResultEnum.SUCCESS, "删除完成");
            }else{
                return new ResultBody(ApiResultEnum.FAIL, "删除失败，请在服务器中手动删除");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
