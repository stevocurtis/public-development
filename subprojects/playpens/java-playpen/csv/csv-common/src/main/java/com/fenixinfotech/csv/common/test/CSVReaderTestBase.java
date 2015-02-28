package com.fenixinfotech.csv.common.test;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CSVReaderTestBase
{
    public URL getTestFileAsUrl()
    {
        URL url = this.getClass().getResource("/sample.csv");
        return url;
    }

    public boolean verifyCSVOutput(List records)
    {
        return (records != null && records.size() == 5);
    }
}