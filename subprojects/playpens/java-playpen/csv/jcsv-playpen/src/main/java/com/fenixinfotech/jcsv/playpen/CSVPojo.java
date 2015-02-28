package com.fenixinfotech.jcsv.playpen;

import com.fenixinfotech.csv.common.playpen.CSVPojoBase;
import com.googlecode.jcsv.annotations.MapToColumn;

import java.util.Date;

public class CSVPojo extends CSVPojoBase
{
    @MapToColumn(column = 0)
    private String key;
    @MapToColumn(column = 1)
    private String col1;
    @MapToColumn(column = 2)
    private String col2;
    @MapToColumn(column = 3)
    private String col3;
    //@MapToColumn(column = 4, type = Date.class)
    @MapToColumn(column = 4)
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