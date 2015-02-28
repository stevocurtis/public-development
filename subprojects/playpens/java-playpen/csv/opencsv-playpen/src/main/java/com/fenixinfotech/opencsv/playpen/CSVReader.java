package com.fenixinfotech.opencsv.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class CSVReader
{
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(URL csvInputUrl) throws IOException
    {
        logger.debug("In readCSV with csvInputUrl: {}", csvInputUrl);

        au.com.bytecode.opencsv.CSVReader csvReader;
        csvReader = new au.com.bytecode.opencsv.CSVReader(new InputStreamReader(csvInputUrl.openStream()), ',', '"', 0);
        List csvPojos = csvReader.readAll();

        logger.info("readCSV with csvInputStream {} found csvPojos {}", csvInputUrl, csvPojos);
    }
}
