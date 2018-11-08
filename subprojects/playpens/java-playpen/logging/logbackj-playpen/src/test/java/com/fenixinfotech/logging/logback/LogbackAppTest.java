package com.fenixinfotech.logging.logback;

import org.junit.Test;

public class LogbackAppTest {

    @Test
    public void run() {
        new LogbackApp().run("a random param");
    }
}