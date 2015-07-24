package com.fenixinfotech.jmx.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Notification;
import javax.management.NotificationListener;

public class MbeanListener implements NotificationListener
{
    private static final Logger logger = LoggerFactory.getLogger(MbeanListener.class);

    public void handleNotification(Notification notification, Object handback)
    {
        if (notification != null)
        {
            logger.info("Received notification {}", notification);
        }
        else
        {
            logger.warn("null notification, how the hell did that happen!");
        }
    }
}
