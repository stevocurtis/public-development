package com.fenixinfotech.supercsv.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;

public class CSVReader
{
    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public void readCSV(InputStream csvInputStream)
    {
        logger.debug("In readCSV with csvInputStream: {}", csvInputStream);

        ICsvBeanReader iCsvBeanReader = null;
        try
        {
            iCsvBeanReader = new CsvBeanReader(new InputStreamReader(csvInputStream), CsvPreference.STANDARD_PREFERENCE);
            final String[] header = new String[]{"key", "col1", "col2", "col3"};
            final CellProcessor[] processors = getProcessors();

            CSVPojo csvPojo;
            while ((csvPojo = iCsvBeanReader.read(CSVPojo.class, header, processors)) != null)
            {
                logger.info("Found csvPojo {}", csvPojo);
            }
        }
        catch (IOException e) {
            logger.error("IOException thrown when reading csvInputStream {}, detail {}", csvInputStream, e);
        }
        finally {
            if (iCsvBeanReader != null) {
                try {
                    iCsvBeanReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.debug("FinishedCSV");
    }

    private static CellProcessor[] getProcessors()
    {
        final CellProcessor[] processors = new CellProcessor[] {
                new UniqueHashCode(), // key (must be unique)
                new Optional(),
                new Optional(),
                new Optional()
        };

        return processors;
    }
}
