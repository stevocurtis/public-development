package com.fenixinfotech.snakeyaml.playpen;

import java.util.List;

public class YAMLPojo
{
    private String id;
    private List<YAMLPojoEntryMap> entrymap;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<YAMLPojoEntryMap> getEntrymap()
    {
        return entrymap;
    }

    public void setEntrymap(List<YAMLPojoEntryMap> entrymap)
    {
        this.entrymap = entrymap;
    }

    @Override
    public String toString()
    {
        return "YAMLPojo{" +
                "id='" + id + '\'' +
                ", entrymap=" + entrymap +
                '}';
    }
}