package com.fenixinfotech.csveed.playpen;

import com.fenixinfotech.csv.common.playpen.CSVPojoBase;
import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;
import org.csveed.annotations.CsvFile;

import java.util.Date;

@CsvFile(separator=',')
public class CSVPojo extends CSVPojoBase
{
    @CsvCell(columnIndex = 1)
    private String key;
    @CsvCell(columnIndex = 2)
    private String col1;
    @CsvCell(columnIndex = 3)
    private String col2;
    @CsvCell(columnIndex = 4)
    private String col3;
    @CsvCell(columnIndex = 5)
    @CsvDate(format = "dd-MMM-yyyy")
    private Date col4;

    public CSVPojo()
    {
        super();
    }

    public CSVPojo(String key, String col1, String col2, String col3, Date col4)
    {
        super(key, col1, col2, col3, col4);
    }
}