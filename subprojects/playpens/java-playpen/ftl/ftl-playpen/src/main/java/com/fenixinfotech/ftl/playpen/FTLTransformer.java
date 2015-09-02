package com.fenixinfotech.ftl.playpen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class FTLTransformer
{
    public Logger logger = LoggerFactory.getLogger(FTLTransformer.class);

    private Configuration configuration = new Configuration();

    public FTLTransformer()
    {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), "/");
    }

    public String transform(String templateName, Map dataMap)
    {
        logger.info("starting transformation for templateName {} with dataMap {}", templateName, dataMap);

        ByteArrayOutputStream transformedContent = new ByteArrayOutputStream();
        // Load template
        try
        {
            Template template = configuration.getTemplate(templateName);
            template.process(dataMap, new OutputStreamWriter(transformedContent));
        }
        catch (IOException e)
        {
            logger.error("IOException caught while create template with detail {}", e);
        }
        catch (TemplateException e)
        {
            logger.error("TemplateException caught while processing template with detail {}", e);
        }
        finally
        {
            try
            {
                transformedContent.close();
            }
            catch (IOException e) {}
        }

        logger.info("completed transformation returning transformedContent {}", transformedContent);

        return String.valueOf(transformedContent);
    }
}
