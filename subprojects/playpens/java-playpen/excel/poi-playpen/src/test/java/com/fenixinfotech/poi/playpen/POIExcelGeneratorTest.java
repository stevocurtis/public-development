package com.fenixinfotech.poi.playpen;

import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class POIExcelGeneratorTest
{
    private final File testFolder = new File("target" + File.separator + this.getClass().getSimpleName());
    private final int  maxNumRows = 10;
    private final int  numColumns = 5;

    private String getTimestamp()
    {
        return new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
    }

    @Test
    public void testWriteHSSFSpreadSheet() throws Exception
    {
        for (int numRows=0; numRows<=maxNumRows; numRows++)
        {
            String fileName = "Output_HSSF_" + getTimestamp() + ".xls";
            File outputFile = new File(testFolder, fileName);

            new POIExcelGenerator().writeHSSFSpreadSheet(outputFile, numRows, numColumns);
            validateFile(outputFile);
        }
    }

    @Test
    public void testWriteXSSFSpreadSheet() throws Exception
    {
        for (int numRows=0; numRows<=maxNumRows; numRows++)
        {
            String fileName = "Output_XSSF_" + getTimestamp() + ".xlsx";
            File outputFile = new File(testFolder, fileName);

            new POIExcelGenerator().writeXSSFSpreadSheet(outputFile, numRows, numColumns);
            validateFile(outputFile);
        }
    }

    private void validateFile(File file)
    {
        assertNotNull(file);
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }
}