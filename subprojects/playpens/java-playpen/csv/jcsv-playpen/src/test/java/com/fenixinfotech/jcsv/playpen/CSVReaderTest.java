package com.fenixinfotech.jcsv.playpen;

import com.fenixinfotech.csv.common.test.CSVReaderTestBase;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class CSVReaderTest extends CSVReaderTestBase
{
    @Ignore
    @Test
    public void testRead() throws IOException
    {
        new CSVReader().readCSV(getTestFileAsUrl());
    }
}