package com.fenixinfotech.jaxb.playpen;

import com.fenixinfotech.jaxb.SampleRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBUtil
{
    private static final Logger logger = LoggerFactory.getLogger(JAXBUtil.class);
    private static JAXBContext jaxbContext = null;
    static
    {
        try {
            jaxbContext = JAXBContext.newInstance(SampleRoot.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Object unmarshall(File file)
    {
        logger.info("running unmarshall for file {}", file);
        Object sampleRoot = null;
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            sampleRoot = unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            logger.error("found error parsing file {}", file, e);
        }
        logger.info("returning unmarshalled object {} from file {}", sampleRoot, file);
        return sampleRoot;
    }

    public void marshall(Object object, File file)
    {
        logger.info("running marshall for file {}", file);
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(object, file);
        } catch (JAXBException e) {
            logger.error("found error writing object {}", object, e);
        }
    }
}
