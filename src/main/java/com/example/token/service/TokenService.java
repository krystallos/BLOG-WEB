package com.example.token.service;

import com.example.menu.enity.menu;
import com.example.menu.mapper.MenuMapper;
import com.example.token.enity.token;
import com.example.token.mapper.TokenMapper;
import com.example.util.rsaKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TokenService {

    @Resource
    private TokenMapper tokenMapper;

    /**
     * 获取菜单
     * @param psnId 用户ID
     * @return
     */
    public List<token> selectTokenSession(String psnId,String state){
        return tokenMapper.selectTokenSession(psnId,state);
    }

    /**
     * 保存tokenId
     * @param token
     * @return
     */
    public int insertTokenSession(token token){
        token.setIds(rsaKey.uuid(null));
        token.setCreateDate(token.getNowDate(null));
        token.setUpdateDate(token.getNowDate(null));
        return tokenMapper.insertTokenSession(token);
    }

    /**
     * 注释tokenId
     * @param token
     * @return
     */
    public int delTokenSession(token token){
        return tokenMapper.delTokenSession(token);
    }

}
