package com.example.job.ddnsJob.service;

import com.example.job.ddnsJob.entry.DDNSToken;
import com.example.job.ddnsJob.mapper.TokenMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {

    @Resource
    private TokenMapper tokenMapper;

    /**
     * 保存阿里云域名操作记录
     * @param ddnsToken
     * @return
     */
    public int insertTokenSession(DDNSToken ddnsToken){
        return tokenMapper.insertTokenSession(ddnsToken);
    }

}
