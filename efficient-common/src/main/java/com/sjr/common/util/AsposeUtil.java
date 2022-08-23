package com.sjr.common.util;

import com.aspose.cells.*;

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

}
