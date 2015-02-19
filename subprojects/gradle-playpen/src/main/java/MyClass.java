public class MyClass
{
    private volatile int theInt = 0;

    public int increment()
    {
        theInt++;
        return theInt;
    }

    public int decrement()
    {
        theInt--;
        return theInt;
    }

    public int getValue()
    {
        return theInt;
    }
}