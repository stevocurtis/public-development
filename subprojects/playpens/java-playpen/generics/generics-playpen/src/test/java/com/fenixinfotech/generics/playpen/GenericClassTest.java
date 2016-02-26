package com.fenixinfotech.generics.playpen;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenericClassTest
{
    @Test
    public void testGenericClass()
    {
        GenericClass<Integer, String> genericClass1 = new GenericClass<>(1, "First");
        GenericClass<Integer, String> genericClass2 = new GenericClass<>(2, "Two");

        assertFalse(genericClass1.equals(genericClass2));
    }
}