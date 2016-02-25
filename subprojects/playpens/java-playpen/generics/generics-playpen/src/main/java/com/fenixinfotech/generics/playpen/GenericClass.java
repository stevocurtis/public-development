package com.fenixinfotech.generics.playpen;

public class GenericClass<S, T>
{
    private S s;
    private T t;

    public GenericClass(S s, T t)
    {
        super();
        this.s = s;
        this.t = t;
    }

    public S getS()
    {
        return s;
    }

    public void setS(S s)
    {
        this.s = s;
    }

    public T getT()
    {
        return t;
    }

    public void setT(T t)
    {
        this.t = t;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof GenericClass)) return false;

        GenericClass<?, ?> that = (GenericClass<?, ?>) o;

        if (s != null ? !s.equals(that.s) : that.s != null) return false;
        return !(t != null ? !t.equals(that.t) : that.t != null);

    }

    @Override
    public int hashCode()
    {
        int result = s != null ? s.hashCode() : 0;
        result = 31 * result + (t != null ? t.hashCode() : 0);
        return result;
    }
}