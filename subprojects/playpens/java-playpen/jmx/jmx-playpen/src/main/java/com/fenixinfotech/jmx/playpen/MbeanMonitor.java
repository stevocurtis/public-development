package com.fenixinfotech.jmx.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.*;

/**
 * Class to monitor mbean status.
 */
public class MbeanMonitor
{
    private static final Logger logger = LoggerFactory.getLogger(MbeanMonitor.class);

    public void connect(String host, String port) throws IOException
    {
        connect(host, port, null, null);
    }

    public void connect(String host, String port, String username, String password) throws IOException
    {
        logger.info("connect with host {} port {} username {} password {}", host, port, username, password);

        if (port != null)
        {
            // retrieve the RMI connector server stub jmxrmi
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:" + port + "/jmxrmi");
            Map<String, String[]> env = new HashMap<String, String[]>();
            if (username != null && password != null)
            {
                env.put(JMXConnector.CREDENTIALS, new String[]{username, password});
            }
            JMXConnector jmxConnector = JMXConnectorFactory.connect(url, env);
            logger.debug("created RMI connector client to url {} with env ", url, env);

            MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
            logger.debug("opened mbean server connection");

            // print out info about domains
            String domains[] = mBeanServerConnection.getDomains();
            Arrays.sort(domains);
            for (String domain : domains)
            {
                logger.debug("found mbean domain {}", domain);
            }

            logger.debug("default domain name is {}", mBeanServerConnection.getDefaultDomain());
            logger.debug("mbean count is {}", mBeanServerConnection.getMBeanCount());

            Set<ObjectName> objectNames = new TreeSet<ObjectName>(mBeanServerConnection.queryNames(null, null));
            for (ObjectName objectName : objectNames)
            {
                logger.debug("found objectName {}", objectName);
            }

            // close connection
            jmxConnector.close();
        }
    }

    public static void main(String[] args) throws IOException
    {
        logger.info("Started main with args {}", args);
        if (args.length > 1)
        {
            new MbeanMonitor().connect(args[0], args[1]);
        }
    }
}
