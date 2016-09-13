package com.fenixinfotech.instrumentation.carrotsearch;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class MemoryTest
{
    private ConcurrentHashMap<Integer, BigObjectValue> bigObject;
    private int    numberOfBigObjectElements = 1000000;

    @Before
    public void setup() {
        bigObject = new ConcurrentHashMap<Integer, BigObjectValue>();
        for (int i=1; i<=numberOfBigObjectElements; i++) {
            bigObject.put(i, new BigObjectValue(i));
        }
    }

    @Test
    public void testSizeof() {
        assertEquals(0, new Memory().getSizeof(null));
        assertTrue(0 < new Memory().getSizeof(bigObject));
    }

    @Test
    public void testHumanReadableSizeof() {
        assertEquals("0 bytes", new Memory().getHumanReadableSizeof(null));
        assertNotNull(new Memory().getHumanReadableSizeof(bigObject));
    }

    private class BigObjectValue {
        private Date date = new Date();
        private int index;
        private String content;

        public BigObjectValue(int index)
        {
            this.index = index;
            this.content = "some default string value with index " + index;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof BigObjectValue)) return false;

            BigObjectValue that = (BigObjectValue) o;

            if (index != that.index) return false;
            if (date != null ? !date.equals(that.date) : that.date != null) return false;
            return content != null ? content.equals(that.content) : that.content == null;

        }

        @Override
        public int hashCode()
        {
            int result = date != null ? date.hashCode() : 0;
            result = 31 * result + index;
            result = 31 * result + (content != null ? content.hashCode() : 0);
            return result;
        }

        @Override
        public String toString()
        {
            return "BigObjectValue{" +
                    "date=" + date +
                    ", index=" + index +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}