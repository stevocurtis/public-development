package com.fenixinfotech.snakeyaml.playpen;

public class YAMLPojoEntryMap
{
    private String att;
    private String key;

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "YAMLPojoEntryMap{" +
                "att='" + att + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
