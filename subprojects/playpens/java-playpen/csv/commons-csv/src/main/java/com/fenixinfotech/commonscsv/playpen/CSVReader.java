package com.fenixinfotech.commonscsv.playpen;

import com.fenixinfotech.csv.common.playpen.CSVPojoBase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(URL csvInputUrl) throws IOException
    {
        logger.info("readCSV with csvInputUrl {}", csvInputUrl);

        Reader reader = new InputStreamReader(csvInputUrl.openStream());

        List<CSVPojoBase> csvPojos = new ArrayList<CSVPojoBase>();
        Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.parse(reader);
        if (csvRecords != null)
        {
            for (CSVRecord csvRecord : csvRecords)
            {
                logger.debug("found csvRecord {}", csvRecord);
            }
        }

        logger.info("readCSV with csvInputUrl {} found csvRecords {}", csvInputUrl, csvRecords);
    }
}
