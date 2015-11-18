package com.fenixinfotech.poi.playpen;

import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class POIExcelGeneratorTest
{
    private final File testFolder = new File("target" + File.separator + this.getClass().getSimpleName());
    private final int  numRows    = 250;
    private final int  numColumns = 50;

    @Test
    public void testWriteXSSFSpreadSheet() throws Exception
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Output_" + timeStamp + ".xlsx";
        File outputFile = new File(testFolder, fileName);
        new POIExcelGenerator().writeXSSFSpreadSheet(outputFile, numRows, numColumns);
//        new POIExcelGenerator().writeXSSFSpreadSheet(outputFile, 1, 5);
        assertNotNull(outputFile);
        assertTrue(outputFile.exists());
        assertTrue(outputFile.isFile());
    }
}