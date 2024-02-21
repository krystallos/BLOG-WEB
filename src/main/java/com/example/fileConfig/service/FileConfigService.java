package com.example.fileConfig.service;

import com.example.api.randomImg.enity.FileRandom;
import com.example.api.randomImg.enity.FileScore;
import com.example.api.randomImg.enity.GetFileRandom;
import com.example.fileConfig.enity.*;
import com.example.fileConfig.enity.authorEnity.PixivAuthor;
import com.example.fileConfig.enity.pixivEnity.PixivHasUrl;
import com.example.fileConfig.mapper.FileConfigMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FileConfigService {

    @Resource
    FileConfigMapper fileConfigMapper;

    /*获取文件详情*/
    public FileDetial selectDetialImage(FileUnit fileUnit){
        return fileConfigMapper.selectDetialImage(fileUnit);
    }

    /*查询全部的文件信息*/
    public int selectFileConfigCount(FileConfig fileConfig){
        return fileConfigMapper.selectFileConfigCount(fileConfig);
    }
    public List<FileConfig> selectFileConfigTab(FileConfig fileConfig){
        //PageHelper.offsetPage(fileConfig.getStartTab(), fileConfig.getHasTab());
        List<FileConfig> tab = fileConfigMapper.selectLikeConfig(fileConfig);
        return tab;
    }

    /*查询全部的文件信息（不带分页）*/
    public List<FileConfig> selectFileConfigNoTab(FileConfig fileConfig){
        List<FileConfig> tab = fileConfigMapper.selectLikeConfig(fileConfig);
        return tab;
    }

    /*查询分组的文件信息*/
    public List<FileGroupFa> selectFileConfigGroup(FileConfig fileConfig){
        List<FileGroupFa> tab = fileConfigMapper.selectFileConfigGroup(fileConfig);
        return tab;
    }

    /*查询分组总数量*/
    public int countSelectFileConfigGroup(FileConfig fileConfig){
        return fileConfigMapper.countSelectFileConfigGroup(fileConfig);
    }

    /*查询指定ID集合图片(不带分页)*/
    public List<FileUtil> selectIdListImg(String ids){
        return fileConfigMapper.selectIdListImg(ids);
    }

    /*查询ID的图片路径(不带分页)*/
    public List<String> selectListPathImg(String[] ids){
        return fileConfigMapper.selectListPathImg(ids);
    }

    /*随机查询单张图片(不带分页)*/
    public List<FileRandom> selectRandomImg(GetFileRandom getFileRandom){
        return fileConfigMapper.selectRandomImg(getFileRandom);
    }

    /*随机色图的评分系统*/
    public Map<String, Object> selectRandomScore(String imageId){
        return fileConfigMapper.selectRandomScore(imageId);
    }

    /*查询文件信息总量*/
    public int selectFileConfigInt(FileConfig fileConfig){
        return fileConfigMapper.selectLikeConfigInt(fileConfig);
    }

    /*插入数据（未启动存储过程版）*/
    public int insertFileConfig(FileConfig fileConfig){
        fileConfig.getNowDate(null);
        fileConfig.setIds(rsaKey.uuid(null));
        return fileConfigMapper.insertFileConfig(fileConfig);
    }

    /*插入数据（大批量处理版）*/
    public int insertFileAllConfig(List<FileConfig> msg){
        return fileConfigMapper.insertFileAllConfig(msg);
    }

    /*删除数据（大批量处理版）*/
    public int delFlagFileAllConfig(String[] msg,String psnId){
        return fileConfigMapper.delFlagFileAllConfig(msg,psnId);
    }

    /*物理删除本地文件*/
    public int delPsnImg(FileConfig fileConfig){
        return fileConfigMapper.delPsnImg(fileConfig);
    }

    /*逻辑查询相似度图片*/
    public List<FileConfig> selectLikeDouble(FileConfig fileConfig){
        PageHelper.offsetPage(fileConfig.getStartTab(), fileConfig.getHasTab());
        List<FileConfig> tab =  fileConfigMapper.selectLikeDouble(fileConfig);
        return tab;
    }

    /*错误页面图片2张*/
    public List<FileConfig> selectErrImg(){
        return fileConfigMapper.selectErrImg();
    }

    /*评分*/
    public int insertFileScore(FileScore fileScore){
        return fileConfigMapper.insertFileScore(fileScore);
    }

    /*添加标签*/
    public int updateImageTag(FileRandom fileRandom){
        return fileConfigMapper.updateImageTag(fileRandom);
    }

    public List<PixivHasUrl> selectGroupAuthPid(FileConfig voReturn){
        return fileConfigMapper.selectGroupAuthPid(voReturn);
    }

    public String selectGroupAuthUid(String hasUrl, String notUrl, String isHas, String itemPath){
        PixivHasUrl pixivHasUrl = new PixivHasUrl();
        pixivHasUrl.setIsHas(isHas);
        pixivHasUrl.setSelectPath(itemPath);
        pixivHasUrl.setNotPath(notUrl);
        pixivHasUrl.setPath(hasUrl);
        return fileConfigMapper.selectGroupAuthUid(pixivHasUrl);
    }

    public String selectHasAuthor(String authorId){
        return fileConfigMapper.selectHasAuthor(authorId);
    };

    public int insertAuthor(PixivAuthor pixivAuthor){
        pixivAuthor.setIds(rsaKey.uuid(""));
        return fileConfigMapper.insertAuthor(pixivAuthor);
    };

    public int updateAuthor(PixivAuthor pixivAuthor){
        return fileConfigMapper.updateAuthor(pixivAuthor);
    };

    public List<FileUnit> selectGroupLissImagePid(String pid){
        return fileConfigMapper.selectGroupLissImagePid(pid);
    }

    public int insertTag(FileConfig fileConfig){
        return fileConfigMapper.insertTag(fileConfig);
    }

    public int updateSync(String ids, String authorIds){
        return fileConfigMapper.updateSync(ids,authorIds);
    }

}
