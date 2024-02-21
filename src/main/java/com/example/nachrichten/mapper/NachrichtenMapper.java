package com.example.nachrichten.mapper;

import com.example.nachrichten.enity.Nachrichten;
import com.example.passWordCont.enity.PassWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * APP通知实现页
 * @author Enoki
 */
@Mapper
public interface NachrichtenMapper {

	/**
	 * 轮播查询页
	 * @param nachrichten
	 */
	List<Nachrichten> nachrichtenMineTab(@Param("nachrichten") Nachrichten nachrichten);

	/**
	 * 获取唯一值
	 * @param nachrichten
	 * @return
	 */
	Nachrichten get(@Param("nachrichten") Nachrichten nachrichten);

	/**
	 * 删除轮播通知
	 * @param nachrichten
	 * @return
	 */
	int delNachrichten(@Param("nachrichten") Nachrichten nachrichten);

	/**
	 * 新增轮播通知
	 * @param nachrichten
	 * @return
	 */
	int insertNachrichten(@Param("nachrichten") Nachrichten nachrichten);

	/**
	 * 修改轮播通知
	 * @param nachrichten
	 * @return
	 */
	int updateNachrichten(@Param("nachrichten") Nachrichten nachrichten);
}
