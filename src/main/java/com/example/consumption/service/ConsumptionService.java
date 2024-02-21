package com.example.consumption.service;

import com.example.consumption.enity.ConsumptionVo;
import com.example.consumption.enity.EchartForConsumption;
import com.example.consumption.mapper.ConsumptionMapper;
import com.example.util.dic.DateFomart;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConsumptionService {

    @Resource
    private ConsumptionMapper consumptionMapper;

    /*查询列表（分页）*/
    public List<ConsumptionVo> selectConsumptionTab(ConsumptionVo consumptionVo){
        PageHelper.offsetPage(consumptionVo.getStartTab(), consumptionVo.getHasTab());
        List<ConsumptionVo> tab = consumptionMapper.selectConsumptionTab(consumptionVo);
        return tab;
    }

    /*查询非详细信息列表（分页）*/
    public List<ConsumptionVo> selectConsumptionNotDeitlTab(ConsumptionVo consumptionVo){
        PageHelper.offsetPage(consumptionVo.getStartTab(), consumptionVo.getHasTab());
        List<ConsumptionVo> tab = consumptionMapper.selectConsumptionNotDeitlTab(consumptionVo);
        return tab;
    }

    /*查询总数据量*/
    public int countSelectConsumptionTab(ConsumptionVo consumptionVo){
        return consumptionMapper.countSelectConsumptionTab(consumptionVo);
    }

    public ConsumptionVo getConsumptionDeitl(String ids){
        return consumptionMapper.getConsumptionDeitl(ids);
    }

    /*多数据图表格式查询*/
    public List<EchartForConsumption> selectDate(EchartForConsumption echartForConsumption){
        return consumptionMapper.selectDate(echartForConsumption);
    }

    /*饼图表格式查询*/
    public List<DicVo> selectBin(EchartForConsumption echartForConsumption){
        return consumptionMapper.selectBin(echartForConsumption);
    }

    /*插入数据（大批量处理）*/
    public int insertFileAllExcl(List<ConsumptionVo> msg){
        return consumptionMapper.insertFileAllExcl(msg);
    }

}
