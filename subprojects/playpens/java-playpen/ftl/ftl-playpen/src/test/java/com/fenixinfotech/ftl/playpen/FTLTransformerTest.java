package com.fenixinfotech.ftl.playpen;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class FTLTransformerTest
{
    @Test
    public void testTransform() throws Exception
    {
        // Test simple transformation
        assertEquals("This is a test template!",
                     new FTLTransformer().transform("/simpleTestTemplate.ftl", null));

        // Test transformation with data map
        Map<String, String> datamap = new LinkedHashMap();
        for (int i=1; i<=3; i++)
        {
            datamap.put("key" + i, "value" + i);
        }
        assertEquals("This is a template with data mapping! value1 value2 value3",
                     new FTLTransformer().transform("/datamapTestTemplate.ftl", datamap));
    }
}