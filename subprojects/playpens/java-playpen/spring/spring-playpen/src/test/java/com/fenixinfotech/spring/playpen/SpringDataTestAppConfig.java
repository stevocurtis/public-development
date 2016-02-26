package com.fenixinfotech.spring.playpen;

import com.fenixinfotech.spring.playpen.services.BasicService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.fenixinfotech.spring.playpen.data,com.fenixinfotech.spring.playpen.services",
               excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                                      classes = BasicService.class))
@EnableJpaRepositories
@EntityScan("com.fenixinfotech.spring.playpen.data")
public class SpringDataTestAppConfig
{}