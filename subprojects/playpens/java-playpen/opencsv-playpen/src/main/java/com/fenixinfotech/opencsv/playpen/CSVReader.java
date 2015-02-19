package com.fenixinfotech.opencsv.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVReader
{
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(File csvFile)
    {
        logger.debug("In readCSV with csvFile: {}", csvFile);

        au.com.bytecode.opencsv.CSVReader csvReader;
        try {
            csvReader = new au.com.bytecode.opencsv.CSVReader(new FileReader(csvFile), ',', '"', 1);
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
