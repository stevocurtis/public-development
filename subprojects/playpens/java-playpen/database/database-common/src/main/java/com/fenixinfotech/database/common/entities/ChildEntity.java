package com.fenixinfotech.database.common.entities;

import javax.persistence.*;

@Entity
public class ChildEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private ParentEntity parentEntity;

    public ChildEntity() {
    }

    @Override
    public String toString() {
        return "ChildEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentEntity=" + parentEntity +
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

    public ParentEntity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(ParentEntity parentEntity) {
        this.parentEntity = parentEntity;
    }
}