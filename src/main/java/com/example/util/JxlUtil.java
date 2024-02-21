package com.example.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * JXL工具类
 * @author Enoki
 * @Date 2023/02/13
 */
public class JxlUtil {

    private static final Logger logger = LoggerFactory.getLogger(JxlUtil.class);

    /**
     * 导出Excel
     *
     * @param fileName 文件地址名称
     * @param Title 导出excel的标题
     * @param listContent 导出的list
     * @return
     */
    public final static boolean exportExcel(String fileName, String[] Title, List<?> listContent) {
        WritableWorkbook workbook = null;
        // 以下开始输出到EXCEL
        try {
            String filePathName = fileName.substring(0,fileName.lastIndexOf("/"));
            File f = new File(filePathName);
            if(!f.exists()){
                f.mkdirs();//创建目录
            }
            // 创建可写入的Excel工作簿
            File file = new File(fileName);
            if (!file.exists()) {
                boolean bool = file.createNewFile();
                logger.info("创建Excel工作簿结果",bool);
            }
            /** **********创建工作簿************ */
            workbook = Workbook.createWorkbook(file);
            /** **********创建工作表************ */
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);
            /** **********设置纵横打印（默认为纵打）、打印纸***************** */
            jxl.SheetSettings sheetset = sheet.getSettings();
            sheetset.setProtected(false);
            /** ************设置单元格字体************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            /** ************以下设置三种单元格样式，灵活备用************ */
            // 用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_center.setWrap(false); // 文字是否换行
            // 用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
            wcf_left.setWrap(false); // 文字是否换行

            /** ***************以下是EXCEL开头大标题，暂时省略********************* */
            // sheet.mergeCells(0, 0, colWidth, 0);
            // sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
            /** ***************以下是EXCEL第一行列标题********************* */
            for (int i = 0; i < Title.length; i++) {
                sheet.addCell(new Label(i, 0, Title[i], wcf_center));
            }
            /** ***************以下是EXCEL正文数据********************* */
            Field[] fields = null;
            int i = 1;
            for (Object obj : listContent) {
                fields = obj.getClass().getDeclaredFields();
                int j = 0;
                for (Field v : fields) {
                    v.setAccessible(true);
                    Object va = v.get(obj);
                    if (va == null) {
                        va = "";
                    }
                    if (va.getClass().getSimpleName().equals("Double")) {
                        sheet.addCell(new Label(j, i, BigDecimal.valueOf((Double) va) + "", wcf_left));
                    } else if (va.getClass().getSimpleName().equals("Float")) {
                        Double vDouble = ((Float) va).doubleValue();

                        sheet.addCell(new Label(j, i, new BigDecimal(new DecimalFormat("#.00").format(vDouble)) + "",
                                wcf_left));
                    } else {
                        sheet.addCell(new Label(j, i, va.toString() + "", wcf_left));
                    }
                    j++;
                }
                i++;
            }
            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();

        } catch (Throwable t) {
            logger.error("系统提示：Excel文件导出失败，原因：",t.getMessage());
            t.printStackTrace();
        }finally {
            try {
                /** *********关闭文件************* */
                workbook.close();
            }catch (Exception e){
                logger.error("系统提示：关闭文件失败，原因：",e.getMessage());
            }
        }
        return true;
    }
    /**
     * 获取CSV文件中的内容
     *
     * @param inputStream
     * @return
     */
    public static List<String> csvContent(InputStream inputStream) {
        List<String> allString = new ArrayList<>();

        if (inputStream != null) {
            InputStreamReader inputStreamReader;
            BufferedReader br = null;
            //FileInputStream fins = new FileInputStream(csv);
            try {
                inputStreamReader = new InputStreamReader(inputStream, "GBK");
                br = new BufferedReader(inputStreamReader);
                String line = "";
                String everyLine = "";
                while ((line = br.readLine()) != null) { // 读取到的内容给line变量
                    everyLine = line + " ,";
                    allString.add(everyLine);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return allString;
    }

    /**
     * 获取xls文件中的内容
     *
     * @param inputStream
     * @return
     */
    public static List<String> xlsContent(InputStream inputStream) {
        List<String> allString = new ArrayList<String>();
        try {
            // 创建输入流，读取Excel
            //InputStream is = new FileInputStream(xls.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(inputStream);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    // sheet.getColumns()返回该页的总列数
                    StringBuffer sb = new StringBuffer();
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        sb.append(cellinfo + ";");
                    }
                    allString.add(sb.toString().substring(0, sb.length() - 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allString;
    }
}

