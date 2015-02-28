package com.fenixinfotech.csveed.playpen;

import org.csveed.api.CsvClient;
import org.csveed.api.CsvClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(InputStream csvInputStream) {
        logger.info("readCSV with csvInputStream {}", csvInputStream);

        Reader reader;
        List<CSVPojo> csvPojos = new ArrayList<CSVPojo>();
        reader = new InputStreamReader(csvInputStream);
        CsvClient<CSVPojo> csvReader = new CsvClientImpl<CSVPojo>(reader, CSVPojo.class);
        csvPojos = csvReader.readBeans();

        logger.info("readCSV with csvInputStream {} found csvPojos {}", csvInputStream, csvPojos);
    }
}
