package com.fenixinfotech.csv.common.test;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class CSVReaderTestBase
{
    public InputStream getTestFileAsStream()
    {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.csv");
        return inputStream;
    }

    public boolean verifyCSVOutput(List records)
    {
        return (records != null && records.size() == 5);
    }
}