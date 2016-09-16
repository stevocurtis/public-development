package com.fenixinfotech.jaxb.playpen;

import com.fenixinfotech.jaxb.SampleRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBUtil
{
    private static final Logger logger = LoggerFactory.getLogger(JAXBUtil.class);
    private static JAXBContext jaxbContext = null;
    static
    {
        try
        {
            jaxbContext = JAXBContext.newInstance(SampleRoot.class);
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }

    private Schema getSchema() throws SAXException
    {

        String schemaFileLocation = "src" + File.separator + "main" + File.separator + "xsd" + File.separator + "Sample.xsd";
        File schemaFile = new File(schemaFileLocation);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        return schema;
    }

    public Object unmarshall(File file)
    {
        logger.info("running unmarshall for file {}", file);
        Object sampleRoot = null;
        try
        {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(getSchema());
            sampleRoot = unmarshaller.unmarshal(file);
        }
        catch (JAXBException e)
        {
            logger.error("found error parsing file {}", file, e);
        }
        catch (SAXException e)
        {
            logger.error("found error parsing file {}", file, e);
        }
        logger.info("returning unmarshalled object {} from file {}", sampleRoot, file);
        return sampleRoot;
    }

    public void marshall(Object object, File file) throws SAXException
    {
        logger.info("running marshall for object {} to file {}", object, file);
        try
        {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(getSchema());
            marshaller.marshal(object, file);
        }
        catch (JAXBException e)
        {
            logger.error("found error writing object {}", object, e);
        }
    }
}
