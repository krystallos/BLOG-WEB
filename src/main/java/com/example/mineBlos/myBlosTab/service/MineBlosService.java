package com.example.mineBlos.myBlosTab.service;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.mapper.MineBlosMapper;
import com.example.util.dic.DicListVo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MineBlosService {

    @Resource
    private MineBlosMapper mineBlosMapper;

    /**
     * 查询最近的操作
     * @param
     * @return
     */
    public List<MineBlos> mineBlosSysLook(MineBlos mineBlos){
        PageHelper.offsetPage(mineBlos.getStartTab(), mineBlos.getHasTab());
        List<MineBlos> item = new ArrayList<>();
        if(mineBlos.getType()==null || mineBlos.getType().equals("")){
            return null;
        }else if(mineBlos.getType().equals("1")){//最近
            item = mineBlosMapper.selectNvClass(mineBlos);
            return item;
        }else if(mineBlos.getType().equals("2")){//文章
            item = mineBlosMapper.selectNvBook(mineBlos);
            return item;
        }else if(mineBlos.getType().equals("3")){//资源更新
            item = mineBlosMapper.selectNvFile(mineBlos);
            return item;
        }else if(mineBlos.getType().equals("4")){//收藏
            item = mineBlosMapper.selectNvLike(mineBlos);
            return item;
        }
        return item;
    }

    public int countMineBlosSysLook(MineBlos mineBlos){
        return mineBlosMapper.countMineBlosSysLook(mineBlos);
    }

    /**
     * 新增博客插入
     * @param mineBlos
     * @return
     */
    public int insertMineBlos(MineBlos mineBlos){
        return mineBlosMapper.insertMineBlos(mineBlos);
    }


    /**
     * 查询博客详细信息
     * @param mineBlos
     * @return
     */
    public MineBlos selectMineBlosSemmIn(MineBlos mineBlos){
        return mineBlosMapper.selectMineBlosSemmIn(mineBlos);
    }

    /**
     * 查询建立博客的全部类型
     * @param mineBlos
     * @return
     */
    public List<MineBlos> selectRightBlos(MineBlos mineBlos){
        return mineBlosMapper.selectRightBlos(mineBlos);
    }

    /**
     * 查询指定博客的类型内数据
     * @param mineBlos
     * @return
     */
    public List<MineBlos> mineBlosRightDivLook(MineBlos mineBlos) {
        PageHelper.offsetPage(mineBlos.getStartTab(), mineBlos.getHasTab(),true);
        List<MineBlos> item = mineBlosMapper.mineBlosRightDivLook(mineBlos);
        return item;
    }

    /**
     * 个人空间查询博客
     * @param mineBlos
     * @return
     */
    public List<MineBlos> mineBlosApiDivLook(MineBlos mineBlos) {
        PageHelper.offsetPage(mineBlos.getStartTab(), mineBlos.getHasTab(),true);
        List<MineBlos> item = mineBlosMapper.mineBlosApiDivLook(mineBlos);
        return item;
    }

    /**
     * 查询分页数量
     * @param mineBlos
     * @return
     */
    public int allBlosRightDivTab(MineBlos mineBlos){
        return mineBlosMapper.allBlosRightDivTab(mineBlos);
    }

    /**
     * 查询个人博客的日志+分类统计
     * @param mineBlos
     * @return
     */
    public String mineBlosCount(MineBlos mineBlos){
        return mineBlosMapper.mineBlosCount(mineBlos);
    }

    /**
     * 查询个人博客的时间分组
     * @return
     */
    public List<DicListVo> mineBlosGroupDate(String personId){
        return mineBlosMapper.mineBlosGroupDate(personId);
    }

    /**
     * 查询博客名称+ID
     * @param type 查询类型
     * @return
     */
    public List<MineBlos> hisBlosType(String type, String ids){
        return mineBlosMapper.hisBlosType(type,ids);
    }

}
