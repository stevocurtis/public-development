package com.fenixinfotech.snakeyaml.playpen;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YAMLReaderTest
{
    @Test
    public void testRead() throws IOException
    {
        URL sampleUrl = ClassLoader.getSystemResource("sample.yaml");
        YAMLPojo yamlPojo = new YAMLReader().readYAML(sampleUrl);

        assertNotNull(yamlPojo);
        assertEquals("1", yamlPojo.getId());
        assertNotNull(yamlPojo.getEntrymap());
        assertTrue(3 == yamlPojo.getEntrymap().size());
    }
}
