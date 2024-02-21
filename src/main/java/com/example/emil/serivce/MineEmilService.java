package com.example.emil.serivce;

import com.example.emil.enity.MineEmil;
import com.example.emil.mapper.MineEmilMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 邮件控制页
 * @author Enoki
 */
@Service
public class MineEmilService {

    @Resource
    MineEmilMapper mineEmilMapper;

    /*带分页查询*/
    public List<MineEmil> selectPageEmil(MineEmil mineEmil){
        PageHelper.offsetPage(mineEmil.getStartTab(), mineEmil.getHasTab());
        List<MineEmil> tab = mineEmilMapper.selectPageEmil(mineEmil);
        return tab;
    }

    /*不带分页查询*/
    public List<MineEmil> selectPageNoEmil(MineEmil mineEmil){
        List<MineEmil> tab = mineEmilMapper.selectPageEmil(mineEmil);
        return tab;
    }

    /*数量查询*/
    public int selectPageNoEmilInt(MineEmil mineEmil){
        return mineEmilMapper.selectPageNoEmilInt(mineEmil);
    }

    /*单条查询*/
    public MineEmil getMineEmil(MineEmil mineEmil){
        return mineEmilMapper.getMineEmil(mineEmil);
    }

    /*逻辑删除邮件*/
    public int delEmail(MineEmil mineEmil){
        return mineEmilMapper.delEmail(mineEmil);
    }

    /*大批量逻辑处理邮件*/
    public int arrDelEmail(String[] mineEmil, int type){
        return mineEmilMapper.arrTypeEmail(mineEmil,type);
    }

    /*发送邮件保存*/
    public int insertEmil(MineEmil mineEmil){
        return mineEmilMapper.insertEmil(mineEmil);
    }

    public int updateEmil(MineEmil mineEmil){
        return mineEmilMapper.updateEmil(mineEmil);
    }

}
