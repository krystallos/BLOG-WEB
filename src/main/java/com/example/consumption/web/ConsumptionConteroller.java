package com.example.consumption.web;

import com.example.consumption.enity.ConsumptionVo;
import com.example.consumption.enity.CountConsumptionVo;
import com.example.consumption.enity.EchartForConsumption;
import com.example.consumption.service.ConsumptionService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import com.example.util.dic.DateFomart;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 消费记录页
 * @author Enoki
 */
@RestController
public class ConsumptionConteroller {

    private static final Logger log = Logger.getLogger(ConsumptionConteroller.class);

    @Resource
    private ConsumptionService consumptionService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private requestUTF requestUTF;

    @Log(title = "消费报表描述视图", type = LogEnum.SELECT)
    @PostMapping("api/consumptionDescriptions.act")
    public ResultBody consumptionDescriptions(HttpSession session,@RequestBody CountConsumptionVo consumptionVo){
        try {
            Person list = (Person) redisUtils.get(session.getId());
            CountConsumptionVo newCountConsumptionVo = new CountConsumptionVo();
            String month = null;
            String year = consumptionVo.getYear();
            if(consumptionVo.getMonth() == null || consumptionVo.getMonth().equals("")){
                Calendar calendar = Calendar.getInstance();
                int mon = calendar.get(Calendar.MONTH) + 1;
                month = mon < 10 ? "0" + mon : mon + "";
            }else{
                month = consumptionVo.getMonth();
            }
            consumptionVo.setMonth(null);
            consumptionVo.setCreatId(list.getIds());
            consumptionVo = consumptionService.consumptionDescriptions(consumptionVo);
            if(consumptionVo == null){
                return new ResultBody(ApiResultEnum.SUCCESS, newCountConsumptionVo);
            }
            consumptionVo.setMonth(month);
            consumptionVo.setCreatId(list.getIds());
            consumptionVo.setYear(year);
            newCountConsumptionVo = consumptionService.consumptionDescriptions(consumptionVo);
            if(newCountConsumptionVo == null){
                return new ResultBody(ApiResultEnum.SUCCESS, consumptionVo);
            }
            consumptionVo.setMonthIn(newCountConsumptionVo.getMonthIn());
            consumptionVo.setMonthOut(newCountConsumptionVo.getMonthOut());
            consumptionVo.setMonthMax(newCountConsumptionVo.getMonthMax());
            return new ResultBody(ApiResultEnum.SUCCESS, consumptionVo);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    @Log(title = "消费记录统计图", type = LogEnum.SELECT)
    @PostMapping("api/echartConsumptionLeft.act")
    public ResultBody echartConsumptionLeft(HttpSession session,@RequestBody EchartForConsumption echartForConsumption){
        try {
            Person list = (Person)redisUtils.get(session.getId());
            if(list == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            echartForConsumption.setCreateId(list.getIds());
            //统计支出
            echartForConsumption.setApiType("1");
            if(echartForConsumption.getOrderType() != null){
                echartForConsumption.setMoneyOrder(1000);
            }
            List<EchartForConsumption> item1 = consumptionService.selectDate(echartForConsumption);
            Map<Integer, List<String>> hasMap = new HashMap<>();
            //12个月
            for(int a=1;a<13;a++){
                List<String> value = new ArrayList<>();
                for(EchartForConsumption item : item1){
                    if(item.getMonth() == a){
                        value.add(item.getY());
                    }
                }
                hasMap.put(a, value);
            }

            return new ResultBody(ApiResultEnum.SUCCESS, hasMap);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 处理表格时间文档方法
     * @param monthAndDays 日期查询数据
     * @param type 为月还是日 1为月，2为年
     * @param  newStr 完整时间格式(2022-04)/(2022)
     * @return
     */
    public EchartForConsumption timeGroup(List<DateFomart> item1,List<DateFomart> item2, String type, String newStr){
        final String year = "2";
        final String month = "1";
        EchartForConsumption orderChartVo = new EchartForConsumption();
        if(year.equals(type)){
            orderChartVo = dayFor(item1,item2,12,"月");
        }else if(month.equals(type)){
            Calendar c = Calendar.getInstance();
            int days = DateUtils.getDaysByYearMonth(c.get(Calendar.YEAR),Integer.valueOf(newStr.split("-")[1]));
            orderChartVo = dayFor(item1,item2,days,"日");
        }
        return orderChartVo;
    }

    public EchartForConsumption dayFor(List<DateFomart> item1,List<DateFomart> item2,int dayTotal, String dayMessage){
        String[] nowDay = new String[dayTotal];
        String[] nowTodal1 = new String[dayTotal];
        String[] nowTodal2 = new String[dayTotal];
        EchartForConsumption item = new EchartForConsumption();
        Map<Integer,String> newMothAndDays1 = item1.stream().collect(Collectors.toMap(DateFomart::getX, DateFomart::getY));
        Map<Integer,String> newMothAndDays2 = item2.stream().collect(Collectors.toMap(DateFomart::getX, DateFomart::getY));
        for(int a=0;a<dayTotal;a++){
            //转换字符串
            nowDay[a] = (a+1)+dayMessage;
            //转换数据
            nowTodal1[a] = newMothAndDays1.get(a+1)==null?"0":newMothAndDays1.get(a+1);
            nowTodal2[a] = newMothAndDays2.get(a+1)==null?"0":newMothAndDays2.get(a+1);
        }
        item.setXAxisData(nowDay);
        item.setData1(nowTodal1);
        item.setData2(nowTodal2);
        return item;

    }

    /**
     * 订单查询
     * @param session
     * @param response
     * @param request
     */
    @Log(title = "消费记录列表", type = LogEnum.SELECT)
    @PostMapping("api/consumptionList.act")
    public ResultBody consumptionList(HttpSession session, @RequestBody ConsumptionVo consumptionVo){
        try{
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            consumptionVo.setCreateId(person.getIds());
            if(consumptionVo.getMoneyOrder() != null && !consumptionVo.getMoneyOrder().equals("")){
                if(consumptionVo.getMoneyOrder().indexOf("<") != -1){
                    consumptionVo.setMoneyOrder(consumptionVo.getMoneyOrder().replace("<",""));
                    consumptionVo.setOrderType("1");
                }else if(consumptionVo.getMoneyOrder().indexOf(">") != -1){
                    consumptionVo.setMoneyOrder(consumptionVo.getMoneyOrder().replace(">",""));
                    consumptionVo.setOrderType("2");
                }else{
                    consumptionVo.setOrderType("3");
                }
            }
            consumptionVo.pubImplPage(consumptionVo.getNowTab(),consumptionVo.getHasTab());
            List<ConsumptionVo> listSize = consumptionService.selectConsumptionTab(consumptionVo);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 订单导入
     * @param multipartFile
     * @param session
     * @param request
     * @param response
     */
    @Log(title = "消费记录导入", type = LogEnum.UPLOAD)
    @PostMapping("upload/importExcl.act")
    public ResultBody importFile(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        try {
            Person list = (Person)redisUtils.get(session.getId());
            if(list == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            InputStream inputStream = multipartFile.getInputStream();
            File fileIn = new File(redisUtils.getConfig(ConfigDicEnum.tempFile.message) + "zfbFile");
            if(!fileIn.exists() && !fileIn.isDirectory()){
                log.error("找不到" + fileIn + "目录，正在自行建立");
                fileIn.mkdirs();//支持自主生成全新目录
                log.error("--------------------------"+fileIn+"完整图片目录生成成功-------------------------");
            }
            String fileName = null;
            if(multipartFile.getOriginalFilename() == null){
                fileName = ".notSub";
            }else {
                fileName = multipartFile.getOriginalFilename().lastIndexOf(".") == 1 ? ".notSub" : multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            }
            //获取输入流
            InputStream input = multipartFile.getInputStream();
            //建立输出流
            FileOutputStream fileOutputStream = new FileOutputStream(redisUtils.getConfig(ConfigDicEnum.tempFile.message)+"zfbFile"+"/"+ new Date().getTime() + "支付宝账单" +fileName);//创建文件输出流
            byte buffer[] = new byte[2048];//建立文件缓冲，由2048比特构成
            int len = 0;//结束标识符
            while((len=input.read(buffer))>0) {
                fileOutputStream.write(buffer,0,len);
            }
            input.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            List<String> fileContents = new ArrayList<String>();
            if (multipartFile.getOriginalFilename().contains("xls")) {
                fileContents = importExcel("xls", inputStream);
            } else if (multipartFile.getOriginalFilename().contains("csv")) {
                fileContents = importExcel("csv", inputStream);
            }else{
                log.error("文件格式不支持");
                return new ResultBody(ApiResultEnum.DATA_ERROR, "文件格式不支持");
            }
            List<ConsumptionVo> listItem = new ArrayList<>();
            String createId = list.getIds();
            int lenSize = 0;
            for(int a=5;a<fileContents.size();a++){
                String[] spli = fileContents.get(a).split(",");
                ConsumptionVo vo = setBuildEnity(spli, a, createId);
                listItem.add(vo);
                lenSize++;
                if(listItem.size() == 100 || a == fileContents.size()-1){
                    consumptionService.insertFileAllExcl(listItem);
                    listItem.clear();
                }
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "累计导入成功" + lenSize + "条数据");
        } catch (Throwable t) {
            t.printStackTrace();
            log.error("文件格式不支持");
            return new ResultBody(ApiResultEnum.ERR, t.getMessage());
        }
    }

    private ConsumptionVo setBuildEnity(String[] spli, Integer num, String createId){
        if(spli.length<15){
            return null;
        }
        try {
            ConsumptionVo vo = new ConsumptionVo();
            vo.setTransactionNum(spli[0].toString().replaceAll("(?:\t|\")"," "));
            vo.setOrder(spli[1].toString().replaceAll("(?:\t|\")",""));
            vo.setPayTime(spli[2].toString().trim());
            vo.setPayFrom(spli[5].toString().trim());
            vo.setPayType(spli[6].toString().trim());
            vo.setPayPerson(spli[7].toString().trim());
            vo.setCommodity(spli[8].toString().trim());
            vo.setAmount(spli[9].toString().trim());
            vo.setIncomeOrExpenditure(spli[10].trim());
            vo.setProgress(spli[11].trim());
            vo.setService(spli[12].trim());
            vo.setRefundAmount(spli[13].trim());
            vo.setFundStatus(spli[15].trim());
            vo.setIds((new Date().getTime() + num)+"");
            vo.setCreateId(createId);
            vo.setCreateDate(vo.getNowDate(""));
            return vo;
        }catch (Exception e){
            log.error("数组第" + num+1 + "行出错，数据参数异常！不做导入");
            return null;
        }
    }

    /**
     * 导入文件
     * @param type
     * @param inputStream
     * @return
     */
    public List<String> importExcel(String type, InputStream inputStream) {
        List<String> list = new ArrayList<>();
        if (type.equals("xls")) {
            list = JxlUtil.xlsContent(inputStream);
        } else {
            list = JxlUtil.csvContent(inputStream);
        }
        return list;
    }

}
