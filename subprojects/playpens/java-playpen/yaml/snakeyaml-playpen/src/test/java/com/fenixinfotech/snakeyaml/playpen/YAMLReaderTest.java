package com.fenixinfotech.snakeyaml.playpen;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static junit.framework.TestCase.assertNotNull;

public class YAMLReaderTest
{
    @Test
    public void testRead() throws IOException
    {
        URL sampleUrl = ClassLoader.getSystemResource("sample.yaml");
        YAMLPojo yamlPojo = new YAMLReader().readYAML(sampleUrl);

        assertNotNull(yamlPojo);
    }
}
