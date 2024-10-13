package com.example.logAspect.mapper;

import com.example.fileConfig.enity.FileSelectPixiv;
import com.example.job.ddnsJob.entry.DDNSToken;
import com.example.logAspect.enity.LogAspect;
import com.example.logAspect.enity.LogAspectList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogAspectMapper {

    int insertLog(@Param("logAspect") LogAspect logAspect);

    List<LogAspectList> findListLog(@Param("logAspect") LogAspectList logAspectList);

    List<FileSelectPixiv> findListImg(@Param("fileSelectPixiv") FileSelectPixiv fileSelectPixiv);

    List<DDNSToken> findListToken(@Param("token") DDNSToken token);

}
