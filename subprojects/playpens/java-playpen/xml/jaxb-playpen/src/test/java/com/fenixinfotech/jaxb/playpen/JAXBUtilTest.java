package com.fenixinfotech.jaxb.playpen;

import com.fenixinfotech.jaxb.SampleRoot;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class JAXBUtilTest
{
    private File testInputFile = null;
    private File testOutputFile = null;
    private JAXBUtil jaxbUtil = null;

    @Before
    public void init()
    {
        // Input file
        String testFileLocation = "src" + File.separator + "test" + File.separator + "data" + File.separator + "test-data.xml";
        testInputFile = new File(testFileLocation);
        assertNotNull(testInputFile);
        assertTrue(testInputFile.exists());

        // Output file
        String testOutputDirectoryLocation = "target" + File.separator + "test" + File.separator + "data";
        File testOutputDirectory = new File(testOutputDirectoryLocation);
        if (!testOutputDirectory.exists())
        {
            testOutputDirectory.mkdirs();
        }
        assertTrue(testOutputDirectory.exists());
        testOutputFile = new File(testOutputDirectory, "test-data.xml");
        assertNotNull(testOutputFile);

        // Util instance
        jaxbUtil = new JAXBUtil();
    }

    @Test
    public void unmarshall() throws Exception
    {
        Object object = jaxbUtil.unmarshall(testInputFile);
        assertNotNull(object);
        assertTrue(object instanceof SampleRoot);
        SampleRoot sampleRoot = (SampleRoot)object;
        assertEquals(2, sampleRoot.getSamples().getSample().size());
    }

    @Test
    public void marshall() throws Exception
    {
        SampleRoot sampleRoot = new SampleRoot();
        jaxbUtil.marshall(sampleRoot, testOutputFile);
    }

}