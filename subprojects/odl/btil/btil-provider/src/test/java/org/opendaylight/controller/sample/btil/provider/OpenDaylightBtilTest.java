/*
* Copyright (c) 2014 Brocade Communications Systems, Inc. and others.  All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/epl-v10.html
*/
package org.opendaylight.controller.sample.btil.provider;

import com.bt.sitb.opendaylight.controller.sample.btil.provider.OpendaylightBtil;
import com.google.common.base.Optional;
import org.junit.Ignore;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.test.AbstractDataBrokerTest;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.*;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;

import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class OpenDaylightBtilTest extends AbstractDataBrokerTest{

    private static InstanceIdentifier<SitbBtil> TOASTER_IID =
                        InstanceIdentifier.builder( SitbBtil.class ).build();
    OpendaylightBtil btil;

    @Override
    protected void setupWithDataBroker(DataBroker dataBroker) {
        btil = new OpendaylightBtil();
        btil.setDataProvider( dataBroker );

        /**
         * Doesn't look like we have support for the NotificationProviderService yet, so mock it
         * for now.
         */
        NotificationProviderService mockNotification = mock( NotificationProviderService.class );
        btil.setNotificationProvider( mockNotification );
    }

    @Test
    public void testBtilInitOnStartUp() throws Exception {
        DataBroker broker = getDataBroker();

        ReadOnlyTransaction rTx = broker.newReadOnlyTransaction();
        Optional<SitbBtil> optional = rTx.read( LogicalDatastoreType.OPERATIONAL, TOASTER_IID ).get();
        assertNotNull( optional );
        assertTrue( "Operational btil not present", optional.isPresent() );

        SitbBtil btil = optional.get();

        assertEquals( SitbBtil.BtilStatus.Up, btil.getBtilStatus() );
        assertEquals( new DisplayString("Opendaylight"),
                      btil.getBtilManufacturer() );
        assertEquals( new DisplayString("Model 1 - Binding Aware"),
                      btil.getBtilModelNumber() );

        Optional<SitbBtil> configBtil =
                            rTx.read( LogicalDatastoreType.CONFIGURATION, TOASTER_IID ).get();
        assertFalse( "Didn't expect config data for btil.",
                     configBtil.isPresent() );
    }

    @Test
    @Ignore //ignored because it is not an e test right now. Illustrative purposes only.
    public void testSomething() throws Exception{
        MakeToastInput toastInput = new MakeToastInputBuilder()
                                        .setBtilDoneness( 1L )
                                        .setBtilToastType( WheatBread.class )
                                        .build();

        //NOTE: In a real test we would want to override the Thread.sleep() to prevent our junit test
        //for sleeping for a second...
        Future<RpcResult<Void>> makeToast = btil.makeToast( toastInput );

        RpcResult<Void> rpcResult = makeToast.get();

        assertNotNull( rpcResult );
        assertTrue( rpcResult.isSuccessful() );
         //etc
    }

}
