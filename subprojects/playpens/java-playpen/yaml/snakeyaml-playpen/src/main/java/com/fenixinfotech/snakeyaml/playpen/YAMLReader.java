package com.fenixinfotech.snakeyaml.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YAMLReader
{
    private static final Logger logger = LoggerFactory.getLogger(YAMLReader.class);

    public YAMLPojo readYAML(URL yamlInputUrl) throws IOException
    {
        logger.debug("In readYAML with yamlInputUrl: {}", yamlInputUrl);

        Constructor constructor = new Constructor(YAMLPojo.class);
        TypeDescription yamlPojoDescription = new TypeDescription(YAMLPojo.class);
        yamlPojoDescription.putMapPropertyType("entrymap", YAMLPojoEntryMap.class, Object.class);
        constructor.addTypeDescription(yamlPojoDescription);

        Yaml yaml = new Yaml(constructor);
        YAMLPojo yamlPojo = (YAMLPojo) yaml.load(yamlInputUrl.openStream());

        logger.info("readYAML with yamlInputUrl {} returning yamlPojo {}", yamlInputUrl, yamlPojo);
        return yamlPojo;
    }
}