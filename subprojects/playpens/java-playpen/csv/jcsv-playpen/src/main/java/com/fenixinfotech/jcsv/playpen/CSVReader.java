package com.fenixinfotech.jcsv.playpen;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.List;

public class CSVReader
{
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(URL csvInputUrl) throws IOException
    {
        logger.info("readCSV with csvInputUrl {}", csvInputUrl);

        Reader reader;
        reader = new InputStreamReader(csvInputUrl.openStream());
        ValueProcessorProvider vpp = new ValueProcessorProvider();
        com.googlecode.jcsv.reader.CSVReader<CSVPojo> csvReader = new CSVReaderBuilder<CSVPojo>(reader).strategy(CSVStrategy.UK_DEFAULT).entryParser(new AnnotationEntryParser<CSVPojo>(CSVPojo.class, vpp)).build();
        List<CSVPojo> csvPojos = csvReader.readAll();

        logger.info("readCSV with csvInput {} found csvPojos {}", csvInputUrl, csvPojos);
    }
}