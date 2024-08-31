package com.example.consumption.mapper;

import com.example.consumption.enity.ConsumptionVo;
import com.example.consumption.enity.CountConsumptionVo;
import com.example.consumption.enity.EchartForConsumption;
import com.example.fileConfig.enity.FileUtil;
import com.example.util.dic.DateFomart;
import com.example.util.dic.DicVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConsumptionMapper {

	/**
	 * 获取唯一实例
	 * @param consumptionVo
	 * @return
	 */
	FileUtil get(@Param("consumptionVo")ConsumptionVo consumptionVo);

	/**
	 * 查询消费记录
	 * @param consumptionVo
	 */
	List<ConsumptionVo> selectConsumptionTab(@Param("consumptionVo") ConsumptionVo consumptionVo);
	List<ConsumptionVo> selectConsumptionNotDeitlTab(@Param("consumptionVo") ConsumptionVo consumptionVo);

	/**
	 * 统计消费记录
	 * @param consumptionVo
	 * @return
	 */
	int countSelectConsumptionTab(@Param("consumptionVo") ConsumptionVo consumptionVo);

	/**
	 * 统计当年、月报表信息
	 * @param consumptionVo
	 * @return
	 */
	CountConsumptionVo consumptionDescriptions(@Param("consumptionVo") CountConsumptionVo consumptionVo);

	/**
	 * 查询详细信息
	 * @param ids
	 * @return
	 */
	ConsumptionVo getConsumptionDeitl (@Param("ids") String ids);

	/**
	 * 日期统计报表查询
	 * @param echartForConsumption
	 * @return
	 */
	List<EchartForConsumption> selectDate(@Param("echartForConsumption") EchartForConsumption echartForConsumption);

	/**
	 * 饼图表格式查询
	 * @param echartForConsumption
	 * @return
	 */
	List<DicVo> selectBin(@Param("echartForConsumption") EchartForConsumption echartForConsumption);

	/**
     * 插入文件路径（大批量数据）
	 * @param consumptionVos
     * @return
     */
	int insertFileAllExcl(@Param("consumptionVos") List<ConsumptionVo> consumptionVos);

}
