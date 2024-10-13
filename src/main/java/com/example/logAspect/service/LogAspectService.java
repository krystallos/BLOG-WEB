package com.example.logAspect.service;

import com.example.fileConfig.enity.FileSelectPixiv;
import com.example.job.ddnsJob.entry.DDNSToken;
import com.example.logAspect.enity.LogAspect;
import com.example.logAspect.enity.LogAspectList;
import com.example.logAspect.mapper.LogAspectMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogAspectService {

    @Resource
    private LogAspectMapper logAspectMapper;

    public int insertLog(LogAspect logAspect){
        logAspectMapper.insertLog(logAspect);
        return 1;
    }

    public List<LogAspectList> findListLog(LogAspectList logAspectList){
        PageHelper.offsetPage(logAspectList.getStartTab(), logAspectList.getHasTab());
        List<LogAspectList> logAspectsList = logAspectMapper.findListLog(logAspectList);
        return logAspectsList;
    }

    public List<FileSelectPixiv> findListImg(FileSelectPixiv filePixiv){
        PageHelper.offsetPage(filePixiv.getStartTab(), filePixiv.getHasTab());
        List<FileSelectPixiv> logAspectsList = logAspectMapper.findListImg(filePixiv);
        return logAspectsList;
    }

    public List<DDNSToken> findListToken(DDNSToken token){
        PageHelper.offsetPage(token.getStartTab(), token.getHasTab());
        List<DDNSToken> logAspectsList = logAspectMapper.findListToken(token);
        return logAspectsList;
    }

}
