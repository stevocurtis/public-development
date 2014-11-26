/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.bt.sitb.opendaylight.controller.sample.btil.provider;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.opendaylight.controller.config.yang.config.sitb_btil_provider.impl.SitbBtilProviderRuntimeMXBean;
import org.opendaylight.controller.md.sal.binding.api.*;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.*;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.core.*;
import org.opendaylight.controller.sal.topology.IPluginInTopologyService;
import org.opendaylight.controller.sal.topology.IPluginOutTopologyService;
import org.opendaylight.controller.sal.topology.TopoEdgeUpdate;
import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.opendaylight.controller.sal.utils.Status;
import org.opendaylight.controller.switchmanager.ISwitchManager;
import org.opendaylight.controller.switchmanager.Switch;
import org.opendaylight.controller.switchmanager.SwitchConfig;
import org.opendaylight.controller.topologymanager.ITopologyManager;
import org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.*;
import org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.device.rev141018.*;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


public class OpendaylightBtil implements SitbBtilService, SitbBtilProviderRuntimeMXBean,
        DataChangeListener, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(OpendaylightBtil.class);

    public static final InstanceIdentifier<SitbBtil> TOASTER_IID = InstanceIdentifier.builder(SitbBtil.class).build();

    private static final org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.DisplayString TOASTER_MANUFACTURER = new org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.DisplayString("Opendaylight");
    private static final org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.DisplayString TOASTER_MODEL_NUMBER = new org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.btil.rev141018.DisplayString("Model 1 - Binding Aware");

    private NotificationProviderService notificationProvider;
    private DataBroker dataProvider;

    private final ExecutorService executor;

    // The following holds the Future for the current make toast task.
    // This is used to cancel the current toast.
    private final AtomicReference<Future<?>> currentMakeToastTask = new AtomicReference<>();

    private final AtomicLong amountOfBreadInStock = new AtomicLong( 100 );

    private final AtomicLong toastsMade = new AtomicLong(0);

    // Thread safe holder for our darkness multiplier.
    private final AtomicLong darknessFactor = new AtomicLong( 1000 );

    public OpendaylightBtil() {
        LOG.info ("***** construct btil") ;
        executor = Executors.newFixedThreadPool(1);
    }

    public void setNotificationProvider(final NotificationProviderService salService) {
        this.notificationProvider = salService;
    }

    public void setDataProvider(final DataBroker salDataProvider) {
        this.dataProvider = salDataProvider;
        setBtilStatusUp( null );
    }

    /**
     * Implemented from the AutoCloseable interface.
     */
    @Override
    public void close() throws ExecutionException, InterruptedException {
        // When we close this service we need to shutdown our executor!
        executor.shutdown();

        if (dataProvider != null) {
            WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
            tx.delete(LogicalDatastoreType.OPERATIONAL,TOASTER_IID);
            Futures.addCallback(tx.submit(), new FutureCallback<Void>()
            {
                @Override
                public void onSuccess(final Void result)
                {
                    LOG.debug("Delete Btil commit result: " + result);
                }

                @Override
                public void onFailure(final Throwable t)
                {
                    LOG.error("Delete of Btil failed", t);
                }
            });
        }
    }

    private SitbBtil buildBtil( final SitbBtil.BtilStatus status ) {
        LOG.info ("buildBtil") ;
        // note - we are simulating a device whose manufacture and model are
        // fixed (embedded) into the hardware.
        // This is why the manufacture and model number are hardcoded.
        return new SitbBtilBuilder().setBtilManufacturer( TOASTER_MANUFACTURER )
                .setBtilModelNumber( TOASTER_MODEL_NUMBER )
                .setBtilStatus( status )
                .build();
    }

    private SitbDevice buildDevice(String manufacturer) {
        LOG.info ("buildDevice with manufacturer={}", manufacturer) ;
        // note - we are simulating a device whose manufacture is
        // fixed (embedded) into the hardware.
        // This is why the manufacture is hardcoded.
        org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.device.rev141018.DisplayString deviceManufacturer =
                new org.opendaylight.yang.gen.v1.https.office.bt.com.sites.sitb.device.rev141018.DisplayString(manufacturer);
        return new SitbDeviceBuilder().setDeviceManufacturer( deviceManufacturer )
                .build();
    }

    /**
     * Implemented from the DataChangeListener interface.
     */
    @Override
    public void onDataChanged( final AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change ) {
        LOG.info ("onDataChanged for btil {}", change) ;
        DataObject dataObject = change.getUpdatedSubtree();
        if( dataObject instanceof SitbBtil )
        {
            SitbBtil btil = (SitbBtil) dataObject;
            Long darkness = btil.getDarknessFactor();
            if( darkness != null )
            {
                darknessFactor.set( darkness );
            }
        }
    }

    /**
     * RPC call implemented from the BtilService interface that cancels the current
     * toast, if any.
     */
    @Override
    public Future<RpcResult<Void>> cancelToast() {

        LOG.info ("cancelToast") ;
        Future<?> current = currentMakeToastTask.getAndSet( null );
        if( current != null ) {
            current.cancel( true );
        }

        // Always return success from the cancel toast call.
        return Futures.immediateFuture( RpcResultBuilder.<Void> success().build() );
    }

    @Override
    public Future<RpcResult<DumpDatastoreOutput>> dumpDatastore(DumpDatastoreInput input)
    {
        LOG.info ("***** dump data store {}", input) ;


        ITopologyManager topologyManager = (ITopologyManager) ServiceHelper.getInstance(ITopologyManager.class, "default", this);
        ISwitchManager switchManager = (ISwitchManager) ServiceHelper.getInstance(ISwitchManager.class, "default", this);

        StringWriter stringWriter = new StringWriter () ;
        PrintWriter writer = new PrintWriter (stringWriter) ;

        Map<Node, Set<Edge>> nodeEdges = topologyManager.getNodeEdges();
        writer.println("nodeEdges") ;
        for (Map.Entry<Node, Set<Edge>> nodeEdge : nodeEdges.entrySet())
        {
            writer.print("node=" + ReflectionToStringBuilder.toString(nodeEdge.getKey())) ;
            for (Edge edge : nodeEdge.getValue())
            {
                writer.println ("edge=" + ReflectionToStringBuilder.toString(edge)) ;
            }
        }
        Map<Node, Set<NodeConnector>> hostEdges = topologyManager.getNodesWithNodeConnectorHost();
        writer.println("hostEdges") ;
        for (Map.Entry<Node, Set<NodeConnector>> hostEdge : hostEdges.entrySet())
        {
            writer.println ("node=" + ReflectionToStringBuilder.toString(hostEdge.getKey())) ;
            for (NodeConnector nodeConnector : hostEdge.getValue())
            {
                writer.println ("nodeConnector=" + ReflectionToStringBuilder.toString(nodeConnector)) ;
                writer.println ("nodeConnector.node=" + ReflectionToStringBuilder.toString(nodeConnector.getNode())) ;
            }

        }

        writer.println("switchNodes") ;
        List<Switch> nodes = switchManager.getNetworkDevices();
        for(Switch sw : nodes) {
            writer.println ("switch=" + ReflectionToStringBuilder.toString(sw)) ;
            Node node = sw.getNode();
            writer.println ("node=" + ReflectionToStringBuilder.toString(node)) ;
            SwitchConfig config = switchManager.getSwitchConfig(node.toString());
            writer.println ("config=" + ReflectionToStringBuilder.toString(config)) ;
        }

        // Add data store
        writer.println("dataStore") ;
        writer.println ("need to find out how to get all keys from data store") ; //TODO

        DumpDatastoreOutput output = new DumpDatastoreOutputBuilder().setResponse(stringWriter.toString()).build();
        return Futures.immediateFuture(RpcResultBuilder.<DumpDatastoreOutput>success(output).build());    }

    private Switch createSwitch(Long nodeId)
    {
        Switch newSwitch = null;
        Node node = null;
        try
        {
            node = new Node(Node.NodeIDType.OPENFLOW, nodeId);
        }
        catch (ConstructionException e)
        {
            LOG.error("ConstructionException thrown while trying to create a new node, detail={}", e.toString());
        }

        newSwitch = new Switch(node);

        LOG.info("createSwitch for nodeId={} returning newSwitch={}", nodeId, newSwitch);
        return newSwitch;
    }

    private Long createNewTopologyId()
    {
        Date now = new Date();
        Long nowAsLong = new Long(now.getTime());

        LOG.info("createNewTopologyId returning nowAsLong={}", nowAsLong);
        return nowAsLong;
    }

    @Override
    public Future<RpcResult<LoadInventoryOutput>> loadInventory(LoadInventoryInput input) {
        // Get the expected input parameters - not used for anything at the moment
        Long numberOfSwitches = input.getNumberOfSwitches();
        Long numberOfHosts = input.getNumberOfHosts();

        LOG.info("***** loadInventory invoked with numberOfSwitches={} numberOfHosts={}", numberOfSwitches, numberOfHosts);

        // Variables used to write the response back
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // Get references to relevant odl services
        ITopologyManager topologyManager = (ITopologyManager) ServiceHelper.getInstance(ITopologyManager.class, "default", this);
        ISwitchManager switchManager = (ISwitchManager) ServiceHelper.getInstance(ISwitchManager.class, "default", this);
        IPluginOutTopologyService pluginOutTopologyService = (IPluginOutTopologyService) ServiceHelper.getInstance(IPluginOutTopologyService.class, "default", this);
        DataBroker dataBroker = (DataBroker) ServiceHelper.getInstance(DataBroker.class, "default", this);

        // Create some switches
        Status status = null;
        try {
            Switch newSwitch = createSwitch(createNewTopologyId());
            //Node node = Node.fromString(Node.NodeIDType.OPENFLOW, "1");
            Node node = new Node(Node.NodeIDType.OPENFLOW, new Long(1L));

            // 1. Try doing update node config
//            Map<String, Property> nodeProperties = new HashMap<String, Property>();
//            nodeProperties.put(Description.propertyName, new Description("OF|99:99:99:99:99:99:99:99"));
//            SwitchConfig switchConfig = new SwitchConfig(node.toString(), nodeProperties);
//            LOG.info("New switch created");
//            switchManager.addNode(node, UpdateType.ADDED, (Set<Property>)(new HashSet()));
//            LOG.info("New switch updated with status={}", status);

            // 2. Try doing update node config
//            Map<String, Property> nodeProperties = new HashMap<String, Property>();
//            nodeProperties.put(Description.propertyName, new Description("OF|99:99:99:99:99:99:99:99"));
//            SwitchConfig switchConfig = new SwitchConfig(node.toString(), nodeProperties);
//            LOG.info("New switch created");
//            status = switchManager.updateNodeConfig(switchConfig);
//            LOG.info("New switch updated with status={}", status);

//            // 3. Try doing set node prop
//            Tier tier = new Tier(1);
//            switchManager.setNodeProp(node, tier);
//            LOG.info("New switch created");

            // 4. Try using IPluginOutTopologyService
/*
            short state_1 = State.EDGE_UP, state_2 = State.EDGE_DOWN;
            long bw_1 = Bandwidth.BW10Gbps, bw_2 = Bandwidth.BW100Mbps;
            long lat_1 = Latency.LATENCY100ns, lat_2 = Latency.LATENCY1ms;
//            String nodeType = "STUB";
//            String nodeConnType = "STUB";
            String nodeType = NodeConnector.NodeConnectorIDType.PRODUCTION;
            String nodeConnType = NodeConnector.NodeConnectorIDType.PRODUCTION;

//            int headNC1_nodeId = 1, headNC1_nodeConnId = 11;
//            int tailNC1_nodeId = 2, tailNC1_nodeConnId = 22;
//            int headNC2_nodeId = 2, headNC2_nodeConnId = 21;
//            int tailNC2_nodeId = 1, tailNC2_nodeConnId = 12;
            String headNC1_nodeId = "1", headNC1_nodeConnId = "11";
            String tailNC1_nodeId = "2", tailNC1_nodeConnId = "22";
            String headNC2_nodeId = "2", headNC2_nodeConnId = "21";
            String tailNC2_nodeId = "1", tailNC2_nodeConnId = "12";

            List<TopoEdgeUpdate> topoEdgeUpdateList = new ArrayList<TopoEdgeUpdate>();
            LOG.info("1");
            NodeConnector headnc1 = new NodeConnector(nodeConnType, headNC1_nodeConnId, new Node(nodeType, headNC1_nodeId));
            LOG.info("2");
            NodeConnector tailnc1 = new NodeConnector(nodeConnType, tailNC1_nodeConnId, new Node(nodeType, tailNC1_nodeId));
            LOG.info("3");
            Edge e1 = new Edge(tailnc1, headnc1);
            LOG.info("4");
            Set<Property> props_1 = new HashSet<Property>();
            LOG.info("5");
            props_1.add(new State(state_1));
            LOG.info("6");
            props_1.add(new Bandwidth(bw_1));
            LOG.info("7");
            props_1.add(new Latency(lat_1));
            LOG.info("8");
            TopoEdgeUpdate teu1 = new TopoEdgeUpdate(e1, props_1, UpdateType.ADDED);
            LOG.info("9");
            topoEdgeUpdateList.add(teu1);
            LOG.info("10");
            pluginOutTopologyService.edgeUpdate(topoEdgeUpdateList);
            LOG.info("New switch created");
*/
            // 5. Try data broker
            WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
            InstanceIdentifier<SitbDevice> ciscoSwitchId = InstanceIdentifier.builder(SitbDevice.class).build();
            tx.put( LogicalDatastoreType.OPERATIONAL,
                    ciscoSwitchId,
                    buildDevice( "Cisco Switch" ) );
            tx.submit();
            LOG.info("Cisco Switch created with ciscoSwitchId={}", ciscoSwitchId);
        }
        catch  (Exception e) {
            LOG.error("Error updating node information status {} error detail {}", status + e.getMessage());
        }

/*
        Map<Node, Set<Edge>> nodeEdges = topologyManager.getNodeEdges();
        writer.println("nodeEdges");
        for (Map.Entry<Node, Set<Edge>> nodeEdge : nodeEdges.entrySet()) {
            writer.print("node=" + ReflectionToStringBuilder.toString(nodeEdge.getKey()));
            for (Edge edge : nodeEdge.getValue()) {
                writer.println("edge=" + ReflectionToStringBuilder.toString(edge));
            }
        }
        Map<Node, Set<NodeConnector>> hostEdges = topologyManager.getNodesWithNodeConnectorHost();
        writer.println("hostEdges");
        for (Map.Entry<Node, Set<NodeConnector>> hostEdge : hostEdges.entrySet()) {
            writer.println("node=" + ReflectionToStringBuilder.toString(hostEdge.getKey()));
            for (NodeConnector nodeConnector : hostEdge.getValue()) {
                writer.println("nodeConnector=" + ReflectionToStringBuilder.toString(nodeConnector));
                writer.println("nodeConnector.node=" + ReflectionToStringBuilder.toString(nodeConnector.getNode()));
            }

        }

        writer.println("switchNodes");
        List<Switch> nodes = switchManager.getNetworkDevices();
        for (Switch sw : nodes) {
            writer.println("switch=" + ReflectionToStringBuilder.toString(sw));
            Node node = sw.getNode();
            writer.println("node=" + ReflectionToStringBuilder.toString(node));
            SwitchConfig config = switchManager.getSwitchConfig(node.toString());
            writer.println("config=" + ReflectionToStringBuilder.toString(config));
        }
*/
        // For now just push the input variables back so can be sure they're read correctly
        stringWriter.append("SUCCESS");

        LOG.info("***** loadInventory returning stringWriter={}", stringWriter);

        LoadInventoryOutput output = new LoadInventoryOutputBuilder().setResponse(stringWriter.toString()).build();
        return Futures.immediateFuture(RpcResultBuilder.<LoadInventoryOutput>success(output).build());
    }

    /**
     * RPC call implemented from the BtilService interface that attempts to make toast.
     */
    @Override
    public Future<RpcResult<Void>> makeToast(final MakeToastInput input) {
        LOG.info("makeToast: " + input);

        final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

        checkStatusAndMakeToast( input, futureResult, 2 );

        return futureResult;
    }

    private RpcError makeBtilOutOfBreadError() {
        return RpcResultBuilder.newError( ErrorType.APPLICATION, "resource-denied",
                "Btil is out of bread", "out-of-stock", null, null );
    }

    private RpcError makeBtilInUseError() {
        return RpcResultBuilder.newWarning( ErrorType.APPLICATION, "in-use",
                "Btil is busy", null, null, null );
    }

    private void checkStatusAndMakeToast( final MakeToastInput input,
                                          final SettableFuture<RpcResult<Void>> futureResult,
                                          final int tries ) {

        LOG.info ("checkStatusAndMakeToast input={}", input) ;
        // Read the BtilStatus and, if currently Up, try to write the status to Down.
        // If that succeeds, then we essentially have an exclusive lock and can proceed
        // to make toast.

        LOG.info ("Reading from id={}", TOASTER_IID) ;

        final ReadWriteTransaction tx = dataProvider.newReadWriteTransaction();
        ListenableFuture<Optional<SitbBtil>> readFuture =
                tx.read( LogicalDatastoreType.OPERATIONAL, TOASTER_IID );

        final ListenableFuture<Void> commitFuture =
                Futures.transform( readFuture, new AsyncFunction<Optional<SitbBtil>,Void>() {

                    @Override
                    public ListenableFuture<Void> apply(
                            final Optional<SitbBtil> btilData ) throws Exception {

                        SitbBtil.BtilStatus btilStatus = SitbBtil.BtilStatus.Up;
                        if( btilData.isPresent() ) {
                            btilStatus = btilData.get().getBtilStatus();
                        }

                        LOG.debug( "Read btil status: {}", btilStatus );

                        if( btilStatus == SitbBtil.BtilStatus.Up ) {

                            if( outOfBread() ) {
                                LOG.debug( "Btil is out of bread" );

                                return Futures.immediateFailedCheckedFuture(
                                        new TransactionCommitFailedException( "", makeBtilOutOfBreadError() ) );
                            }

                            LOG.debug( "Setting Btil status to Down" );

                            // We're not currently making toast - try to update the status to Down
                            // to indicate we're going to make toast. This acts as a lock to prevent
                            // concurrent toasting.
                            tx.put( LogicalDatastoreType.OPERATIONAL, TOASTER_IID,
                                    buildBtil( SitbBtil.BtilStatus.Down ) );
                            return tx.submit();
                        }

                        LOG.debug( "Oops - already making toast!" );

                        // Return an error since we are already making toast. This will get
                        // propagated to the commitFuture below which will interpret the null
                        // TransactionStatus in the RpcResult as an error condition.
                        return Futures.immediateFailedCheckedFuture(
                                new TransactionCommitFailedException( "", makeBtilInUseError() ) );
                    }
                } );

        Futures.addCallback( commitFuture, new FutureCallback<Void>() {
            @Override
            public void onSuccess( final Void result ) {
                // OK to make toast
                currentMakeToastTask.set( executor.submit( new MakeToastTask( input, futureResult ) ) );
            }

            @Override
            public void onFailure( final Throwable ex ) {
                if( ex instanceof OptimisticLockFailedException ) {

                    // Another thread is likely trying to make toast simultaneously and updated the
                    // status before us. Try reading the status again - if another make toast is
                    // now in progress, we should get BtilStatus.Down and fail.

                    if( ( tries - 1 ) > 0 ) {
                        LOG.debug( "Got OptimisticLockFailedException - trying again" );

                        checkStatusAndMakeToast( input, futureResult, tries - 1 );
                    }
                    else {
                        futureResult.set( RpcResultBuilder.<Void> failed()
                                .withError( ErrorType.APPLICATION, ex.getMessage() ).build() );
                    }

                } else {

                    LOG.debug( "Failed to commit Btil status", ex );

                    // Probably already making toast.
                    futureResult.set( RpcResultBuilder.<Void> failed()
                            .withRpcErrors( ((TransactionCommitFailedException)ex).getErrorList() )
                            .build() );
                }
            }
        } );
    }

    /**
     * RestConf RPC call implemented from the BtilService interface.
     * Restocks the bread for the btil, resets the toastsMade counter to 0, and sends a
     * BtilRestocked notification.
     */
    @Override
    public Future<RpcResult<java.lang.Void>> restockBtil(final RestockBtilInput input) {
        LOG.info( "restockBtil: {}", input );

        amountOfBreadInStock.set( input.getAmountOfBreadToStock() );

        if( amountOfBreadInStock.get() > 0 ) {
            BtilRestocked reStockedNotification = new BtilRestockedBuilder()
                    .setAmountOfBread( input.getAmountOfBreadToStock() ).build();
            notificationProvider.publish( reStockedNotification );
        }

        return Futures.immediateFuture( RpcResultBuilder.<Void> success().build() );
    }

    /**
     * JMX RPC call implemented from the BtilProviderRuntimeMXBean interface.
     */
    @Override
    public void clearToastsMade() {
        LOG.info( "clearToastsMade" );
        toastsMade.set( 0 );
    }

    /**
     * Accesssor method implemented from the BtilProviderRuntimeMXBean interface.
     */
    @Override
    public Long getToastsMade() {
        return toastsMade.get();
    }

    private void setBtilStatusUp( final Function<Boolean,Void> resultCallback ) {

        LOG.info ("Set btil status to up for id={}", TOASTER_IID) ;
        WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
        tx.put( LogicalDatastoreType.OPERATIONAL,TOASTER_IID, buildBtil( SitbBtil.BtilStatus.Up ) );

        Futures.addCallback( tx.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess( final Void result ) {
                notifyCallback( true );
            }

            @Override
            public void onFailure( final Throwable t ) {
                // We shouldn't get an OptimisticLockFailedException (or any ex) as no
                // other component should be updating the operational state.
                LOG.error( "Failed to update btil status", t );

                notifyCallback( false );
            }

            void notifyCallback( final boolean result ) {
                if( resultCallback != null ) {
                    resultCallback.apply( result );
                }
            }
        } );
    }

    private boolean outOfBread()
    {
        return amountOfBreadInStock.get() == 0;
    }

    private class MakeToastTask implements Callable<Void>
    {

        final MakeToastInput toastRequest;
        final SettableFuture<RpcResult<Void>> futureResult;

        public MakeToastTask( final MakeToastInput toastRequest,
                              final SettableFuture<RpcResult<Void>> futureResult ) {
            this.toastRequest = toastRequest;
            this.futureResult = futureResult;
        }

        @Override
        public Void call() {
            try
            {
                // make toast just sleeps for n seconds per doneness level.
                long darknessFactor = OpendaylightBtil.this.darknessFactor.get();
                Thread.sleep(darknessFactor * toastRequest.getBtilDoneness());

            }
            catch( InterruptedException e ) {
                LOG.info( "Interrupted while making the toast" );
            }

            toastsMade.incrementAndGet();

            amountOfBreadInStock.getAndDecrement();
            if( outOfBread() ) {
                LOG.info( "Btil is out of bread!" );

                notificationProvider.publish( new BtilOutOfBreadBuilder().build() );
            }

            // Set the Btil status back to up - this essentially releases the toasting lock.
            // We can't clear the current toast task nor set the Future result until the
            // update has been committed so we pass a callback to be notified on completion.

            setBtilStatusUp( new Function<Boolean,Void>() {
                @Override
                public Void apply( final Boolean result ) {

                    currentMakeToastTask.set( null );

                    LOG.debug("Toast done");

                    futureResult.set( RpcResultBuilder.<Void>success().build() );

                    return null;
                }
            } );

            return null;
        }
    }
}
