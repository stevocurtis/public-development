import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestMyClass
{
    @Test
    public void testMethods()
    {
        MyClass myClass = new MyClass();
        assertEquals(0, myClass.getValue());
        assertEquals(1, myClass.increment());
        assertEquals(1, myClass.getValue());
        assertEquals(0, myClass.decrement());
        assertEquals(0, myClass.getValue());
    }

    @Test
    public void testMethods2()
    {
        MyClass myClass = new MyClass();
        assertEquals(0, myClass.getValue());
        assertEquals(1, myClass.increment());
        assertEquals(1, myClass.getValue());
        assertEquals(0, myClass.decrement());
        assertEquals(0, myClass.getValue());
    }
}