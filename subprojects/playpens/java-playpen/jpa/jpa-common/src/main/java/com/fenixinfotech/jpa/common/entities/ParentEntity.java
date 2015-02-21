package com.fenixinfotech.jpa.common.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ParentEntity
{
    @Id
    @GeneratedValue
    private long id;

    private String name;

    public ParentEntity() {
    }

    @Override
    public String toString() {
        return "ParentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
