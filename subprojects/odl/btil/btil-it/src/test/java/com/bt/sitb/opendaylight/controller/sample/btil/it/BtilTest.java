/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.bt.sitb.opendaylight.controller.sample.btil.it;

import com.bt.sitb.opendaylight.controller.sample.kitchen.api.EggsType;
import com.bt.sitb.opendaylight.controller.sample.kitchen.api.SitbKitchenService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.HashBrown;
import org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.WhiteBread;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.util.Filter;
import org.ops4j.pax.exam.util.PathUtils;

import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.assertEquals;
import static org.opendaylight.controller.test.sal.binding.it.TestHelper.baseModelBundles;
import static org.opendaylight.controller.test.sal.binding.it.TestHelper.bindingAwareSalBundles;
import static org.opendaylight.controller.test.sal.binding.it.TestHelper.configMinumumBundles;
import static org.opendaylight.controller.test.sal.binding.it.TestHelper.flowCapableModelBundles;
import static org.opendaylight.controller.test.sal.binding.it.TestHelper.junitAndMockitoBundles;
import static org.opendaylight.controller.test.sal.binding.it.TestHelper.mdSalCoreBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemPackages;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

@RunWith(PaxExam.class)
public class BtilTest {

    @Inject
    @Filter(timeout=60*1000)
    SitbKitchenService kitchenService;

    @Configuration
    public Option[] config() {
        return options(systemProperty("osgi.console").value("2401"), mavenBundle("org.slf4j", "slf4j-api")
                .versionAsInProject(), //
                          mavenBundle("org.slf4j", "log4j-over-slf4j").versionAsInProject(), //

                                systemProperty("logback.configurationFile").value(
                        "file:" + PathUtils.getBaseDir()
                                + "/src/test/resources/logback.xml"),
                mavenBundle("ch.qos.logback", "logback-core").versionAsInProject(), //
                mavenBundle("ch.qos.logback", "logback-classic").versionAsInProject(), //
                systemProperty("osgi.bundles.defaultStartLevel").value("4"),
                systemPackages("sun.nio.ch"),

                btilBundles(),
                mdSalCoreBundles(),

                bindingAwareSalBundles(),
                configMinumumBundles(),
                // BASE Models
                baseModelBundles(),
                flowCapableModelBundles(),

                // Set fail if unresolved bundle present
                systemProperty("pax.exam.osgi.unresolved.fail").value("true"),
                junitAndMockitoBundles());
    }

    private Option btilBundles() {
        return new DefaultCompositeOption(
                mavenBundle("com.bt.sitb.opendaylight.controller.samples", "sample-btil-provider").versionAsInProject(),
                mavenBundle("com.bt.sitb.opendaylight.controller.samples", "sample-btil-consumer").versionAsInProject(),
                mavenBundle("com.bt.sitb.opendaylight.controller.samples", "sample-btil").versionAsInProject(),
                mavenBundle("org.openexi", "nagasena").versionAsInProject(),
                mavenBundle("org.openexi", "nagasena-rta").versionAsInProject()
        );
    }

    @Test
    @Ignore
    public void testBtil() throws Exception {

        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName providerOn = new ObjectName("org.opendaylight.controller:instanceName=sitb-btil-provider-impl,type=RuntimeBean,moduleFactoryName=btil-provider-impl");

        long toastsMade = (long) platformMBeanServer.getAttribute(providerOn, "ToastsMade");
        assertEquals(0, toastsMade);

        boolean success = true;

        // Make toasts using OSGi service
        success &= kitchenService.makeBreakfast( EggsType.SCRAMBLED, HashBrown.class, 4).get().isSuccessful();
        success &= kitchenService.makeBreakfast( EggsType.POACHED, WhiteBread.class, 8 ).get().isSuccessful();

        Assert.assertTrue("Not all breakfasts succeeded", success);

        // Verify toasts made count on provider via JMX/config-subsystem
        toastsMade = (long) platformMBeanServer.getAttribute(providerOn, "ToastsMade");
        assertEquals(2, toastsMade);
    }

}
