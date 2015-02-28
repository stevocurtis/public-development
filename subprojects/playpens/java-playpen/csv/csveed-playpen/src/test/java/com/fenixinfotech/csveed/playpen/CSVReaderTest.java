package com.fenixinfotech.csveed.playpen;

import com.fenixinfotech.csv.common.test.CSVReaderTestBase;
import org.junit.Test;

import java.io.IOException;

public class CSVReaderTest extends CSVReaderTestBase
{
    @Test
    public void testRead() throws IOException
    {
        new CSVReader().readCSV(getTestFileAsUrl());
    }
}