package com.efficient.common.util;

import com.aspose.cells.*;
import com.aspose.words.Document;
import com.aspose.words.FontSettings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * aspose 工具类
 *
 * @author TMW
 * @since 2022/5/31 9:16
 */
public class AsposeUtil {
    static {
        try {
            License license = new License();
            license.setLicense(AsposeLicense.getInputStream());
            com.aspose.words.License licenseWord = new com.aspose.words.License();
            licenseWord.setLicense(AsposeLicense.getInputStream());
        } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
    }

    /**
     * excel 转 pdf
     *
     * @param excelPath excel 路径
     * @param pdfPath   pdf 存储路径
     * @throws Exception
     */
    public static void excelToPdf(String excelPath, String pdfPath) throws Exception {
        Workbook workbook = new Workbook(excelPath);
        PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
        pdfSaveOptions.setOnePagePerSheet(true);
        workbook.save(pdfPath, pdfSaveOptions);
    }

    /**
     * excel 转 word
     *
     * @param excelPath excel 路径
     * @param wordPath  wordPath 存储路径
     * @throws Exception
     */
    public static void excelToWord(String excelPath, String wordPath) throws Exception {
        Workbook workbook = new Workbook(excelPath);
        DocxSaveOptions docxSaveOptions = new DocxSaveOptions();
        docxSaveOptions.setMergeAreas(true);
        workbook.save(wordPath, SaveFormat.DOCX);
    }

    public static void doc2pdf(String inPath, String outPath) {
        FileOutputStream os = null;
        try {
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);

            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // FontSettings.getDefaultInstance().setFontsFolder(WordTopdfUtil.class.getClassLoader().getResource("./fonts").toURI().getPath(), true);

            doc.save(os, com.aspose.words.SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void excel2pdf(String sourcePath, String targetPath) {
        FileOutputStream os = null;
        try {
            //设置默认字体
            FontConfigs.setDefaultFontName("宋体");
            //解决linux生成pdf文件乱码问题, window下可以不需要设置字体
            // FontConfigs.setFontFolder(ExcelToPdfUtil.class.getClassLoader().getResource("./fonts").toURI().getPath(), true);
            // 新建一个空白pdf文档
            File targetPdfFile = new File(targetPath);
            os = new FileOutputStream(targetPdfFile);
            Workbook wb = new Workbook(sourcePath);
            PdfSaveOptions options = new PdfSaveOptions();
            //如果OnePagePerSheet为true，则一页纸的所有内容将只输出到一页。页面设置的纸张尺寸将无效，并且页面设置的其他设置仍将生效。
            options.setOnePagePerSheet(true);
            //如果AllColumnsInOnePagePerSheet为true，则一页纸的所有列内容将仅输出到一页。pagesetup的纸张尺寸宽度将被忽略，而pagesetup的其他设置仍将生效。
//            options.setAllColumnsInOnePagePerSheet(true);

            //指示是否检查文本中每个字符的字体兼容性。默认值是true。
            // 禁用此属性可能会提供更好的性能。但是，当无法使用默认或指定的文本/字符字体来呈现它时，
            // 生成的pdf中可能会出现无法读取的字符（例如，块）。在这种情况下，用户应将此属性保留为true，
            // 以便可以搜索替代字体并用来呈现文本。
            options.setCheckFontCompatibility(true);

            //指示是否仅在单元格字体不兼容时替换字符字体。默认为false。
            // 我们将首先尝试使用Workbook和PdfSaveOption / system的默认字体作为单元字体。
            options.setFontSubstitutionCharGranularity(true);
            //将根据此属性中的PdfCompliance将工作簿转换为pdf。该属性的值为PdfCompliance整数常量。
//            options.setCompliance(PdfCompliance.NONE);
            //            int[] autoDrawSheets = {3};
            //当excel中对应的sheet页宽度太大时，在PDF中会拆断并分页。此处等比缩放。
//            autoDraw(wb, autoDrawSheets);
//            int[] showSheets = {0};
            //隐藏workbook中不需要的sheet页。
//            printSheetPage(wb, showSheets);

            wb.save(os, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
