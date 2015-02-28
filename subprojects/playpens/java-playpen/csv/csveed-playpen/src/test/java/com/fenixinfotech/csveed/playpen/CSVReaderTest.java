package com.fenixinfotech.csveed.playpen;

import com.fenixinfotech.csv.common.test.CSVReaderTestBase;
import org.junit.Test;

public class CSVReaderTest extends CSVReaderTestBase
{
    @Test
    public void testRead()
    {
        new CSVReader().readCSV(getTestFileAsStream());
    }
}