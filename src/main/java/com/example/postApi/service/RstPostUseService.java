package com.example.postApi.service;

import com.example.postApi.enity.PostApiEnity;
import com.example.postApi.enity.RstChildPostApi;
import com.example.postApi.enity.RstPostApiVo;
import com.example.postApi.enity.RstPostProject;
import com.example.postApi.mapper.ApiPostListMapper;
import com.example.postApi.mapper.RstPostUseMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RstPostUseService {

    @Resource
    private RstPostUseMapper rstPostUseMapper;

    /**
     * 查询接口列表
     * @param rstPostProject
     * @return
     */
    public List<RstPostProject> rstPostList(RstPostProject rstPostProject) {
        PageHelper.offsetPage(rstPostProject.getStartTab(), rstPostProject.getHasTab(),true);
        List<RstPostProject> item = rstPostUseMapper.rstPostList(rstPostProject);
        return item;
    }

    /**
     * 查询接口详情文档
     * @param ids
     * @return
     */
    public List<RstPostProject> rstPostChildList(String ids) {
        return rstPostUseMapper.rstPostChildList(ids);
    }

    /**
     * 查询接口列表(公开)
     * @param rstPostProject
     * @return
     */
    public List<RstPostProject> rstOpenPostList(RstPostProject rstPostProject) {
        PageHelper.offsetPage(rstPostProject.getStartTab(), rstPostProject.getHasTab(),true);
        List<RstPostProject> item = rstPostUseMapper.rstOpenPostList(rstPostProject);
        return item;
    }

    /**
     * 查询列表分页(公开)
     * @param rstPostProject
     * @return
     */
    public int rstOpenPostListCount(RstPostProject rstPostProject){
        return rstPostUseMapper.rstOpenPostListCount(rstPostProject);
    }

    /**
     * 查询列表分页
     * @param rstPostProject
     * @return
     */
    public int rstPostListCount(RstPostProject rstPostProject){
        return rstPostUseMapper.rstPostListCount(rstPostProject);
    }

    /**
     * 根据项目名称查询是否重复
     * @param rstPostProject
     * @return
     */
    public int rstPostListDouble(RstPostProject rstPostProject){
        return rstPostUseMapper.rstPostListDouble(rstPostProject);
    }

    /**
     * 获取文档详情
     * @param rstPostApiVo
     * @return
     */
    public RstChildPostApi getRstPost(RstPostApiVo rstPostApiVo){
        return rstPostUseMapper.getRstPost(rstPostApiVo);
    }

    /**
     * 修改文档详情
     * @param rstPostProject
     * @return
     */
    public int editRstPost(RstPostProject rstPostProject){
        return rstPostUseMapper.editRstPost(rstPostProject);
    }

    /**
     * 查询文档接口列表
     * @param rstPostApiVo
     * @return
     */
    public List<RstPostApiVo> getRstPostList(RstPostApiVo rstPostApiVo) {
        PageHelper.offsetPage(rstPostApiVo.getStartTab(), rstPostApiVo.getHasTab(),true);
        List<RstPostApiVo> item = rstPostUseMapper.getRstPostList(rstPostApiVo);
        return item;
    }

    /**
     * 查询文章子菜单下拉框
     * @return
     */
    public List<RstPostProject> getRstPostChiList() {
        return rstPostUseMapper.getRstPostChiList();
    }

    /**
     * 保存API文档
     * @param rstPostProject
     * @return
     */
    public int insertRstPostApi(RstPostProject rstPostProject) {
        return rstPostUseMapper.insertRstPostApi(rstPostProject);
    }

    /**
     * 删除文档接口
     * @param ids
     * @return
     */
    public int rstDelete(String ids){
        return rstPostUseMapper.rstDelete(ids);
    }

    /**
     * 删除接口文档
     * @param ids
     * @return
     */
    public int rstPostDelete(String ids){
        return rstPostUseMapper.rstPostDelete(ids);
    }

    /**
     * 删除接口
     * @param ids
     * @return
     */
    public int rstPostApiDelete(String ids){
        return rstPostUseMapper.rstPostApiDelete(ids);
    }

    /**
     * 获取API详情
     * @param rstPostApiVo
     * @return
     */
    public RstPostApiVo getRstApiDetial(RstPostApiVo rstPostApiVo){
        return rstPostUseMapper.getRstApiDetial(rstPostApiVo);
    }

    /**
     * 新增接口
     * @param rstPostApiVo
     * @return
     */
    public int insertRstApiDetial(RstPostApiVo rstPostApiVo){
        return rstPostUseMapper.insertRstApiDetial(rstPostApiVo);
    }

    /**
     * 保存接口
     * @param rstPostApiVo
     * @return
     */
    public int saveRstApiDetial(RstPostApiVo rstPostApiVo){
        return rstPostUseMapper.saveRstApiDetial(rstPostApiVo);
    }

}
