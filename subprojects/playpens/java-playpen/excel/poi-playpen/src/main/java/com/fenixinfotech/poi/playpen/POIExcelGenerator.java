package com.fenixinfotech.poi.playpen;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class POIExcelGenerator
{
    public static final Logger logger = LoggerFactory.getLogger(POIExcelGenerator.class);
    public static final String defaultSheetName = "Sheet1";

    public void writeHSSFSpreadSheet(File location, int numRows, int numColumns) throws IOException
    {
        writeSpreadSheet(new HSSFWorkbook(), location, numRows, numColumns);
    }

    public void writeXSSFSpreadSheet(File location, int numRows, int numColumns) throws IOException
    {
        writeSpreadSheet(new XSSFWorkbook(), location, numRows, numColumns);
    }

    private void writeSpreadSheet(Workbook workbook, File location, int numRows, int numColumns) throws IOException
    {
        String workbookType = (workbook == null) ? "null" : workbook.getClass().getSimpleName();

        logger.debug("running writeSpreadSheet with workbook {} location {} numRows {} numColumns {}", workbookType, location, numRows, numColumns);

        long startTimestamp = new Date().getTime();

        if (location != null)
        {
            // check if parent folder exists
            if (location.getParentFile() != null && !location.getParentFile().exists())
            {
                logger.info("creating folder structure {}", location.getParentFile());
                location.getParentFile().mkdirs();
            }

            logger.debug("creating workbook and sheet");
            Sheet sheet = workbook.createSheet(defaultSheetName);

            for (int rowCount=1; rowCount <= numRows; rowCount++)
            {
                logger.debug("creating row {}", rowCount);
                Row row = sheet.createRow(rowCount - 1);

                for (int colCount=1; colCount<=numColumns; colCount++)
                {
                    logger.debug("creating cell at row {} and column {}", rowCount, colCount);
                    Cell cell = row.createCell(colCount - 1);
                    cell.setCellValue("Data" + colCount + "." + rowCount);
                }
            }

            logger.debug("writing output to {}", location);
            FileOutputStream fileOutputStream = new FileOutputStream(location);


            // Write output file
            long filewriteStartTimestamp = new Date().getTime();

            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            long finishTimestamp      = new Date().getTime();
            long filewriteElapsedTime = finishTimestamp - filewriteStartTimestamp;
            long elapsedTime          = finishTimestamp - startTimestamp;

            logger.info("finished workbook {} {} rows and {} columns in {} millis filesize file write time {} millis file size {} bytes", workbookType, numRows, numColumns, elapsedTime, filewriteElapsedTime, location.length());
        }
    }
}
