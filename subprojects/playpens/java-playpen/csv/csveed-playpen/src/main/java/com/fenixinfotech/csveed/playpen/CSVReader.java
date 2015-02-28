package com.fenixinfotech.csveed.playpen;

import org.csveed.api.CsvClient;
import org.csveed.api.CsvClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.List;

public class CSVReader {
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(URL csvInputUrl) throws IOException
    {
        logger.info("readCSV with csvInputUrl {}", csvInputUrl);

        Reader reader = new InputStreamReader(csvInputUrl.openStream());
        CsvClient<CSVPojo> csvReader = new CsvClientImpl<CSVPojo>(reader, CSVPojo.class);
        List<CSVPojo> csvPojos = csvReader.readBeans();

        logger.info("readCSV with csvInputUrl {} found csvPojos {}", csvInputUrl, csvPojos);
    }
}
