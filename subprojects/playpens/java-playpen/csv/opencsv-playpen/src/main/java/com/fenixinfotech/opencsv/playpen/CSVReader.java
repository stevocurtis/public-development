package com.fenixinfotech.opencsv.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

public class CSVReader
{
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(InputStream csvInputStream)
    {
        logger.debug("In readCSV with csvInputStream: {}", csvInputStream);

        au.com.bytecode.opencsv.CSVReader csvReader;
        try {
            csvReader = new au.com.bytecode.opencsv.CSVReader(new InputStreamReader(csvInputStream), ',', '"', 0);
            String[] nextLine;
            while((nextLine = csvReader.readNext()) != null)
            {
                logger.info("Found nextLine {}", Arrays.toString(nextLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("FinishedCSV");
    }
}
