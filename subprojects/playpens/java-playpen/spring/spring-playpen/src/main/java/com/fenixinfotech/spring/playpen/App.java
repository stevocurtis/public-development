package com.fenixinfotech.spring.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.fenixinfotech")
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
