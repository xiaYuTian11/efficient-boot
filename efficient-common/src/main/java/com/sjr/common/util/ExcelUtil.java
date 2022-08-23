package com.sjr.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TMW
 * @since 2022/5/24 15:28
 */
@Slf4j
public class ExcelUtil {

    public static String FONT_SONG = "宋体";

    public static HSSFRow getRow(HSSFSheet sheet, Integer rowIndex) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (Objects.isNull(row)) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }

    public static HSSFCell gtCell(HSSFRow row, Integer cellIndex) {
        HSSFCell cell = row.getCell(cellIndex);
        if (Objects.isNull(cell)) {
            cell = row.createCell(cellIndex);
        }
        return cell;
    }

    public static HSSFCell getCell(HSSFRow row, Integer cellIndex, CellStyle style) {
        HSSFCell cell = row.getCell(cellIndex);
        if (Objects.isNull(cell)) {
            cell = row.createCell(cellIndex);
        }
        cell.setCellStyle(style);
        return cell;
    }

    public static void setCell(HSSFCell cell, Object value) {
        if (Objects.isNull(value)) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(String.valueOf(value));
        }
    }

    public static void setCell(HSSFCell cell, Object value, CellStyle style) {
        if (Objects.isNull(value) || StrUtil.equals(value.toString(), "null")) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(String.valueOf(value));
        }
        cell.setCellStyle(style);
    }

    public static CellStyle getCellStyle(HSSFWorkbook workbook, int fontHeight) {
        return getCellStyle(workbook, FONT_SONG, fontHeight);
    }

    public static CellStyle getCellStyle(HSSFWorkbook workbook, String fontName, int fontHeight) {
        CellStyle css = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        css.setDataFormat(format.getFormat("@"));
        HSSFFont font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeight((short) fontHeight);
        css.setFont(font);
        //设置水平对齐方式
        css.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐方式
        css.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(css);
        return css;
    }

    public static void setBorder(CellStyle css) {
        //设置上边框线条类型
        css.setBorderTop(BorderStyle.THIN);
        //设置右边框线条类型
        css.setBorderRight(BorderStyle.THIN);
        //设置下边框线条类型
        css.setBorderBottom(BorderStyle.THIN);
        //设置左边框线条类型
        css.setBorderLeft(BorderStyle.THIN);
    }

    /**
     * 写入数据
     *
     * @param workbook
     * @param sheet
     * @param rowIndex
     * @param mapList
     */
    public static void writeForeach(HSSFWorkbook workbook, HSSFSheet sheet, Integer rowIndex, List<Map<String, String>> mapList) {
        for (Map<String, String> map : mapList) {
            int cellCount = 0;
            for (String value : map.values()) {
                ExcelUtil.setCell(ExcelUtil.getCell(ExcelUtil.getRow(sheet, rowIndex), cellCount), value);
                cellCount++;
            }
            rowIndex++;
        }
    }

    /**
     * @param tempFilePath   模板路径
     * @param targetFilePath 导出路径
     * @param list           列表数据集合
     * @return 导出文件
     * @throws Exception
     */
    public static File write(String tempFilePath, String targetFilePath, List<?> list) throws Exception {
        return write(tempFilePath, targetFilePath, null, null, null, null, list);
    }

    /**
     * @param tempFilePath   模板路径
     * @param targetFilePath 导出路径
     * @param title          文件标题
     * @param list           列表数据集合
     * @return 导出文件
     * @throws Exception
     */
    public static File write(String tempFilePath, String targetFilePath, String title, List<String> remarkList, List<?> list) throws Exception {
        return write(tempFilePath, targetFilePath, title, remarkList, null, null, list);
    }

    public static File write(String tempFilePath, String targetFilePath, String title,
                             List<CellRangeAddress> rangeAddList, List<?> rangeList, List<?> list) throws Exception {
        return write(tempFilePath, targetFilePath, title, null, rangeAddList, rangeList, list);
    }

    /**
     * @param tempFilePath   模板路径
     * @param targetFilePath 导出路径
     * @param title          标题
     * @param rangeAddList   合并单元格
     * @param rangeList      合并单元格数据
     * @param list           列表数据集合
     * @return 导出文件
     * @throws Exception
     */
    public static File write(String tempFilePath, String targetFilePath, String title, List<String> remarkList, List<CellRangeAddress> rangeAddList, List<?> rangeList, List<?> list) throws Exception {
        InputStream inp = new FileInputStream(tempFilePath);
        //创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook(inp);
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 标题
        if (StrUtil.isNotBlank(title)) {
            HSSFRow row0 = ExcelUtil.getRow(sheet, 0);
            HSSFCell cell01 = row0.getCell(0);
            cell01.setCellValue(title);
            workbook.setSheetName(0, title);
        }
        // 取最后一排样式
        int lastRowNum = sheet.getLastRowNum();
        HSSFCellStyle cellStyleOld = ExcelUtil.getRow(sheet, lastRowNum - 1).getCell(0).getCellStyle();
        HSSFFont font = cellStyleOld.getFont(workbook);
        String fontName = font.getFontName();
        short fontHeight = font.getFontHeight();
        CellStyle cellStyle = ExcelUtil.getCellStyle(workbook, fontName, fontHeight);

        // 合并单元格处理
        if (CollUtil.isNotEmpty(rangeAddList) && CollUtil.isNotEmpty(rangeList) && rangeAddList.size() == rangeList.size()) {
            List<Map<String, String>> rangeMapList = BeanUtil.beanListToMap(rangeList, DatePattern.NORM_DATE_PATTERN);
            for (int i = 0; i < rangeAddList.size(); i++) {
                Map<String, String> rangeMap = rangeMapList.get(i);
                CellRangeAddress cellAddresses = rangeAddList.get(i);
                sheet.addMergedRegion(cellAddresses);
                int firstColumn = cellAddresses.getFirstColumn();
                int lastColumn = cellAddresses.getLastColumn();
                int lastRow = cellAddresses.getLastRow();
                HSSFRow row = ExcelUtil.getRow(sheet, lastRow);
                AtomicInteger count = new AtomicInteger(0);
                rangeMap.forEach((k, v) -> {
                    int countFor = count.get();
                    if (countFor > lastColumn || countFor == firstColumn) {
                        ExcelUtil.setCell(ExcelUtil.getCell(row, countFor), v, cellStyle);
                    }
                    count.incrementAndGet();
                });

            }
            lastRowNum += rangeAddList.size() + 1;
        } else {
            lastRowNum += 1;
        }

        int cellCount = 0;
        if (CollUtil.isNotEmpty(list)) {
            List<Map<String, String>> mapList = BeanUtil.beanListToMap(list, DatePattern.NORM_DATE_PATTERN);
            cellCount = mapList.get(0).size();
            ExcelUtil.writeForeachWithStyle(workbook, sheet, lastRowNum, mapList, cellStyle);
            lastRowNum += mapList.size();
        }

        if (CollUtil.isNotEmpty(remarkList)) {
            for (String remark : remarkList) {
                CellRangeAddress cellAddresses = new CellRangeAddress(lastRowNum, lastRowNum, 0, cellCount);
                sheet.addMergedRegion(cellAddresses);
                HSSFRow row = ExcelUtil.getRow(sheet, lastRowNum);
                HSSFCell cell = getCell(row, 0);
                cell.setCellValue(remark);
                lastRowNum++;
            }
        }

        FileOutputStream out = new FileOutputStream(targetFilePath);
        workbook.write(out);
        out.close();
        workbook.close();
        return new File(targetFilePath);
    }

    /**
     * 写入数据
     *
     * @param workbook
     * @param sheet
     * @param rowIndex
     * @param mapList
     */
    public static void writeForeachWithStyle(HSSFWorkbook workbook, HSSFSheet sheet, Integer rowIndex, List<Map<String, String>> mapList, CellStyle style) {
        for (Map<String, String> map : mapList) {
            int cellCount = 0;
            for (String value : map.values()) {
                ExcelUtil.setCell(ExcelUtil.getCell(ExcelUtil.getRow(sheet, rowIndex), cellCount), value, style);
                cellCount++;
            }
            rowIndex++;
        }
    }

    /**
     * 找到需要插入的行数，并新建一个POI的row对象
     *
     * @param sheet    当前sheet页的对象
     * @param rowIndex 要插入的当前行数
     * @return
     */
    private static HSSFRow createRow(HSSFSheet sheet, Integer rowIndex) {
        HSSFRow row = null;
        if (sheet.getRow(rowIndex) != null) {
            int lastRowNo = sheet.getLastRowNum();
            // rowIndex 当前行 lastRowNo 末尾行 1 往下移动一行 (正数代表往下移动，负数表示往上移动)
            sheet.shiftRows(rowIndex, lastRowNo, 1);
        }
        row = sheet.createRow(rowIndex);
        return row;
    }

    /**
     * 找到需要删除的行数，并新建一个POI的row对象
     *
     * @param sheet    当前sheet页的对象
     * @param rowIndex 要插入的当前行数
     * @return
     */
    private static void removeRow(HSSFSheet sheet, Integer rowIndex) {
        if (sheet.getRow(rowIndex) != null) {
            int lastRowNo = sheet.getLastRowNum();
            sheet.shiftRows(rowIndex, lastRowNo, -1);// rowIndex 当前行 lastRowNo 末尾行 1 往下移动一行 (正数代表往下移动，负数表示往上移动)
        }
    }

    public static HSSFCell getCell(HSSFRow row, Integer cellIndex) {
        HSSFCell cell = row.getCell(cellIndex);
        if (Objects.isNull(cell)) {
            cell = row.createCell(cellIndex);
        }
        return cell;
    }
}
