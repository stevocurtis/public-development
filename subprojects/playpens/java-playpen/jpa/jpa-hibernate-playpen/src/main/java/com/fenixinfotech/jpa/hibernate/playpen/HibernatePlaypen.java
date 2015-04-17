package com.fenixinfotech.jpa.hibernate.playpen;

import com.fenixinfotech.jpa.common.entities.ParentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;

public class HibernatePlaypen
{
    private static final Logger logger = LoggerFactory.getLogger(HibernatePlaypen.class);
    protected HibernatePUEnum hibernatePUEnum;

    public HibernatePlaypen()
    {
        this(HibernatePUEnum.UNIT_TEST_PU_JNDI_CONN);
    }

    public HibernatePlaypen(HibernatePUEnum hibernatePUEnum)
    {
        this.hibernatePUEnum = hibernatePUEnum;
        logger.info("instantiated with hibernatePUEnum {}", hibernatePUEnum);
    }

    public ParentEntity createParentEntity(String parentEntityName)
    {
        logger.info("creating ParentEntity from parentEntityName {}", parentEntityName);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(hibernatePUEnum.toString());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setName(parentEntityName);
        entityManager.persist(parentEntity);
        entityManager.getTransaction().commit();
        logger.info("created parentEntity {}", parentEntity);
        entityManager.close();
        z
        entityManagerFactory.close();
        return parentEntity;
    }

    public ParentEntity readParentEntity(long id)
    {
        logger.info("reading ParentEntity from id {}", id);
        EntityManager entityManager = Persistence.createEntityManagerFactory(hibernatePUEnum.toString()).createEntityManager();
        ParentEntity parentEntity = entityManager.find(ParentEntity.class, id);
        logger.info("read parentEntity {} from id {}", parentEntity, id);
        return parentEntity;
    }

    public Collection<ParentEntity> readAllParentEntities()
    {
        logger.info("reading all ParentEntities");
        EntityManager entityManager = Persistence.createEntityManagerFactory(hibernatePUEnum.toString()).createEntityManager();
        Query query = entityManager.createQuery("SELECT p FROM ParentEntity p");
        Collection<ParentEntity> parentEntities = query.getResultList();
        logger.info("found {} parentEntities {}", (parentEntities == null ? 0 : parentEntities.size()), parentEntities);
        return parentEntities;
    }
}