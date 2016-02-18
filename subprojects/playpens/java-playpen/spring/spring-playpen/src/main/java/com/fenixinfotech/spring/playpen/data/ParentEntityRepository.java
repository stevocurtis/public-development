package com.fenixinfotech.spring.playpen.data;

import com.fenixinfotech.database.common.entities.ParentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParentEntityRepository extends CrudRepository<ParentEntity, Long>
{
    List<ParentEntity> findByName(String name);
}
