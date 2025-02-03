package com.example.fileConfig.service;

import com.example.fileConfig.enity.ehentai.GetEhentaiVo;
import com.example.fileConfig.enity.imageAlbum.GetImageAlbumVo;
import com.example.fileConfig.mapper.EhentaiMapper;
import com.example.fileConfig.mapper.ImageAlbumMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ImageAlbumService {

    @Resource
    ImageAlbumMapper imageAlbumMapper;

    /*获取唯一实例*/
    public GetImageAlbumVo get(GetImageAlbumVo getImageAlbumVo){
        return imageAlbumMapper.get(getImageAlbumVo);
    }

    /*获取文件列表*/
    public List<GetImageAlbumVo> selectImageAlbum(GetImageAlbumVo vo){
        PageHelper.offsetPage(vo.getStartTab(), vo.getHasTab());
        List<GetImageAlbumVo> list = imageAlbumMapper.selectImageAlbum(vo);
        return list;
    }

    /*新增文件*/
    public int insertImageAlbum(GetImageAlbumVo getImageAlbumVo){
        getImageAlbumVo.setIds(rsaKey.uuid(null));
        getImageAlbumVo.setUpdateDate(getImageAlbumVo.getNowDate(""));
        return imageAlbumMapper.insertImageAlbum(getImageAlbumVo);
    }

    /*更新文件*/
    public int updateImageAlbum(GetImageAlbumVo vo){
        return imageAlbumMapper.updateImageAlbum(vo);
    }

    /*收藏文件*/
    public int likeImageAlbum(GetImageAlbumVo vo){
        return imageAlbumMapper.likeImageAlbum(vo.getIds(), vo.getIsLike());
    }

    /*删除文件*/
    public int delImageAlbum(String ids){
        return imageAlbumMapper.delImageAlbum(ids);
    }

}
