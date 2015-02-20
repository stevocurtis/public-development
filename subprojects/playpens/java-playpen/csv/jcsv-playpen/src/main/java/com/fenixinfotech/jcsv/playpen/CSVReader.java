package com.fenixinfotech.jcsv.playpen;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader
{
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(InputStream csvInputStream)
    {
        logger.info("readCSV with csvFile {}", csvInputStream);

        Reader reader;
        List<CSVPojo> csvPojos = new ArrayList<CSVPojo>();
        try
        {
            reader = new InputStreamReader(csvInputStream);
            ValueProcessorProvider vpp = new ValueProcessorProvider();
            com.googlecode.jcsv.reader.CSVReader<CSVPojo> csvReader = new CSVReaderBuilder<CSVPojo>(reader).strategy(CSVStrategy.UK_DEFAULT).entryParser(new AnnotationEntryParser<CSVPojo>(CSVPojo.class, vpp)).build();
            csvPojos = csvReader.readAll();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        logger.info("readCSV with csvFile {} found csvPojos {}", csvInputStream, csvPojos);
    }
}
