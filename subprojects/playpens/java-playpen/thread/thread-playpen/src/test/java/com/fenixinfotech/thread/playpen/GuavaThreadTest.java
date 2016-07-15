package com.fenixinfotech.thread.playpen;

import org.junit.Test;

import static org.junit.Assert.*;

public class GuavaThreadTest {
    @Test
    public void fineGrainedConcurrency() throws Exception {
        new GuavaThread().fineGrainedConcurrency();
    }

}