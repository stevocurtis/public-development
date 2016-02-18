package com.fenixinfotech.spring.playpen.data;

import com.fenixinfotech.database.common.entities.ParentEntity;
import com.fenixinfotech.spring.playpen.SpringDataTestAppConfig;
import com.fenixinfotech.spring.playpen.services.SpringDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDataTestAppConfig.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {"spring.datasource.driverClassName=org.apache.derby.jdbc.EmbeddedDriver",
                                  "spring.datasource.url=jdbc:derby:derbyJunit;create=true",
                                  "spring.jpa.hibernate.ddl-auto=update"}
)
@EnableJpaRepositories("com.fenixinfotech")
public class SpringDataTest
{
    private static final Logger logger = LoggerFactory.getLogger(SpringDataTest.class);

    @Autowired
    ParentEntityRepository parentEntityRepository;

    @Autowired
    SpringDataService springDateService;

    private static final int parentBatchSize = 5;
    private static final int childBatchSize  = 2;

    @Before
    public void init()
    {
        assertNotNull(parentEntityRepository);
    }

    @Test
    @Rollback(value = false)
    public void testSaveParentEntities()
    {
        int initalSize = springDateService.readAllParentEntities().size();

        for (int parentCount=1; parentCount<=parentBatchSize; parentCount++)
        {
            Set<String> childNames = new HashSet<String>();
            for (int childCount=1; childCount<=childBatchSize; childCount++)
            {
                childNames.add("Child Name " + childCount);
            }
            springDateService.createParentEntity("Parent Name " + parentCount, childNames);
        }

        int finalSize = springDateService.readAllParentEntities().size();
        assertEquals(parentBatchSize, (finalSize - initalSize));
    }

    @Test
    @Rollback(value = false)
    public void testShowAllParentEntities()
    {
        List<ParentEntity> parentEntityList = (List<ParentEntity>) parentEntityRepository.findAll();
        assertNotNull(parentEntityList);

        for(ParentEntity parentEntity : parentEntityList)
        {
            logger.info("found parent entity {}", parentEntity);
        }
    }
}