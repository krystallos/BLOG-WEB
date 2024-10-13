package com.example.job.ddnsJob.mapper;

import com.example.job.ddnsJob.entry.DDNSToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TokenMapper {

	/**
	 * 保存阿里云域名操作记录
	 * @param ddnsToken
	 */
	Integer insertTokenSession(@Param("ddnsToken") DDNSToken ddnsToken);

}
