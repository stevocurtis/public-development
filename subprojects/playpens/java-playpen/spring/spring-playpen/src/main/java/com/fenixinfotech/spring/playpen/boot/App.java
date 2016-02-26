package com.fenixinfotech.spring.playpen.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@EnableAutoConfiguration
@ComponentScan("com.fenixinfotech.spring.playpen.web")
public class App
{
    public static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args)
    {
        logger.info("starting Spring app");

        SpringApplication springApplication = new SpringApplication(App.class);

        // set the derby home to create derby log in target
        System.setProperty("derby.system.home", "target" + File.separator + "derbydb");

        springApplication.run(args);
    }
}
