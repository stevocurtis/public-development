package com.fenixinfotech.poi.playpen;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
    public static final int megabytes = 1024 * 1024;

    public void writeXSSFSpreadSheet(File location, int numRows, int numColumns) throws IOException
    {
        logger.info("running writeXSSFSpreadSheet with location {} numRows {} numColumns {}", location, numRows, numColumns);

        long startTimestamp = new Date().getTime();

        if (location != null)
        {
            // check if parent folder exists
            if (location.getParentFile() != null && !location.getParentFile().exists())
            {
                logger.info("creating folder structure {}", location.getParentFile());
                location.getParentFile().mkdirs();
            }
            else
            {
                logger.info("not creating parent folders");
            }

            logger.info("creating workbook and sheet");
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(defaultSheetName);

            for (int rowCount=1; rowCount <= numRows; rowCount++)
            {
                logger.info("creating row {}", rowCount);
                XSSFRow xssfRow = sheet.createRow(rowCount - 1);

                for (int colCount=1; colCount<=numColumns; colCount++)
                {
                    logger.info("creating cell at row {} and column {}", rowCount, colCount);
                    XSSFCell xssfCell = xssfRow.createCell(colCount - 1);
                    xssfCell.setCellValue("Data" + colCount + "." + rowCount);
                }
            }

            logger.info("writing output to {}", location);
            FileOutputStream fileOutputStream = new FileOutputStream(location);

            // Write output file
            wb.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            long elapsedTime = new Date().getTime() - startTimestamp;

            logger.info("finished {} rows and {} columns in {} millis filesize {} Mb", numRows, numColumns, elapsedTime, location.length()/megabytes);
        }
    }
}
