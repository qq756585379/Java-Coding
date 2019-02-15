package com.coding.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;

/**
 * excel 处理工具类
 */
public class ExcelUtil {

    public static void createStyleHeader(Sheet sheet,
                                         String title,
                                         String subTitle,
                                         String[] headers,
                                         CellStyle headerStyle,
                                         CellStyle contentStyle) {
        //主标题
        Row r = sheet.createRow(0);
        //副标题
        Row r2 = sheet.createRow(1);
        Row r3 = sheet.createRow(2);

        Cell cell = r.createCell(0);
        Cell cell2 = r2.createCell(0);
        Cell cell3 = null;

        for (int i = 0; i < headers.length; i++) {
            cell = r.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(title);

            cell2 = r2.createCell(i);
            cell2.setCellStyle(headerStyle);
            cell2.setCellValue(subTitle);

            cell3 = r3.createCell(i);
            cell3.setCellStyle(contentStyle);
            cell3.setCellValue(headers[i]);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cell.setCellValue(title);
        cell.setCellStyle(headerStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, headers.length - 1));
        cell2.setCellValue(subTitle);
        cell2.setCellStyle(contentStyle);
    }

    public static void main(String[] args) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("A");
        Sheet sheet2 = wb.createSheet("B");
        Sheet sheet3 = wb.createSheet("C");

        // 设置头样式
        Font font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        Font font2 = wb.createFont();
        font2.setColor(HSSFColor.RED.index);

        CellStyle styleHeader = wb.createCellStyle();
        styleHeader.setFont(font);
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        // 设置内容样式
        CellStyle styleContent = wb.createCellStyle();
        styleContent.setFont(font2);
        styleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        styleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        styleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        String[] heads = new String[]{"分店名称", "费用类型", "支付/销售渠道", "借方", "贷方", "结果"};

        ExcelUtil.createStyleHeader(sheet1, "hahha", "eee", heads, styleHeader, styleContent);
//        ExcelUtil.createStyleHeader(sheet2, "hahha", "eee", heads, styleHeader, styleContent);
//        ExcelUtil.createStyleHeader(sheet3, "hahha", "eee", heads, styleHeader, styleContent);

        String fileNamePath = "/Users/yangjun/Desktop/chengjia/order.xls";
        File file = new File(fileNamePath);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
