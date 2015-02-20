package com.fenixinfotech.supercsv.playpen;

import org.junit.Test;

import java.io.File;

public class CSVReaderTest
{
    @Test
    public void testRead()
    {
        File csvFile = new File("src" + File.separator + "test" + File.separator + "data", "sample.csv");
        new CSVReader().readCSV(csvFile);
    }
}
