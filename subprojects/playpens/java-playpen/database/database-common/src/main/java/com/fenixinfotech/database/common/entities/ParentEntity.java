package com.fenixinfotech.database.common.entities;

import javax.persistence.*;

@Entity
public class ParentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}