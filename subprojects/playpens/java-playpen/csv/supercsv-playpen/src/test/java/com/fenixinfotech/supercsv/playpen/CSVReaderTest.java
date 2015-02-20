package com.fenixinfotech.supercsv.playpen;

import com.fenixinfotech.csv.common.test.CSVReaderTestBase;
import org.junit.Test;

import java.io.File;

public class CSVReaderTest extends CSVReaderTestBase
{
    @Test
    public void testRead()
    {
        new CSVReader().readCSV(getTestFileAsStream());
    }
}
