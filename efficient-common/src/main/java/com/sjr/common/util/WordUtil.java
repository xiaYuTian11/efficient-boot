package com.sjr.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.aspose.words.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/6/6 15:32
 */
public class WordUtil {
    static {
        try {
            License licenseWord = new License();
            licenseWord.setLicense(AsposeLicense.getInputStream());
        } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
    }

    public static void init() {

    }

    public static void setCycleTable(Document document, Integer tableIndex, List<?> list, Boolean includeTotal) throws Exception {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        Table table = (Table) document.getChild(NodeType.TABLE, tableIndex, true);

        // DocumentBuilder builder = new DocumentBuilder(document);
        // 移动至第一行第一列
        // builder.moveToCell(tableIndex, 0, 0, 0);
        // // 合并的第一个单元格
        // builder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        // // 移动至第一行第二列
        // builder.moveToCell(tableIndex, 0, 1, 0);
        // // 被合并的单元格
        // builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);

        List<Map<String, String>> mapList = BeanUtil.beanListToMap(list, DatePattern.NORM_DATE_PATTERN);
        int length = table.getRows().toArray().length;
        for (int i = 0; i < mapList.size(); i++) {
            Range range;
            Node deepClone = null;
            boolean flag = includeTotal && i == 0;
            if (flag) {
                range = table.getRows().get(length - 2).getRange();
            } else {
                deepClone = table.getLastRow().deepClone(true);
                range = table.getLastRow().getRange();
            }

            Map<String, String> map = mapList.get(i);
            for (String key : map.keySet()) {
                setRange(range, key, map.get(key));
            }
            if (Objects.nonNull(deepClone)) {
                table.getRows().add(deepClone);
            }
        }
        table.getLastRow().remove();
    }

    public static void setRange(Range range, String filedName, String value) throws Exception {
        value = (value == null || StrUtil.equalsIgnoreCase(value, "null")) ? "" : value;
        value = value.replaceAll("null", "");
        range.replace(filedName, value);
    }

}
