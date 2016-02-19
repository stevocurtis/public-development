package com.fenixinfotech.spring.playpen.services;

import com.fenixinfotech.database.common.entities.ChildEntity;
import com.fenixinfotech.database.common.entities.ParentEntity;
import com.fenixinfotech.spring.playpen.data.ParentEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SpringDataService
{
    Logger logger = LoggerFactory.getLogger(SpringDataService.class);

    @Autowired
    ParentEntityRepository parentEntityRepository;

    public void createParentEntity(String parentEntityName, Set<String> childEntityNames)
    {
        logger.info("running createParentEntity with parent entity name {} and child entity names {}", parentEntityName, childEntityNames);

        ParentEntity parentEntity = null;
        if (parentEntityName != null)
        {
            parentEntity = new ParentEntity();
            parentEntity.setName(parentEntityName);

            if (childEntityNames != null)
            {
                for (String childEntityName : childEntityNames)
                {
                    ChildEntity childEntity = new ChildEntity();
                    childEntity.setName(childEntityName);
                    childEntity.setParentEntity(parentEntity);
                }

                parentEntityRepository.save(parentEntity);
            }
        }

        logger.info("finished createParentEntity {}", parentEntity);
    }

    public List<ParentEntity> readAllParentEntities()
    {
        logger.info("running readAllParentEntities");

        return (List<ParentEntity>) parentEntityRepository.findAll();
    }
}