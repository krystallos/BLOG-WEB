package com.example.logAspect.mapper;

import com.example.fileConfig.enity.FileSelectPixiv;
import com.example.logAspect.enity.LogAspect;
import com.example.logAspect.enity.LogAspectList;
import com.example.token.enity.token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogAspectMapper {

    int insertLog(@Param("logAspect") LogAspect logAspect);

    List<LogAspectList> findListLog(@Param("logAspect") LogAspectList logAspectList);

    List<FileSelectPixiv> findListImg(@Param("fileSelectPixiv") FileSelectPixiv fileSelectPixiv);

    List<token> findListToken(@Param("token") token token);

}
