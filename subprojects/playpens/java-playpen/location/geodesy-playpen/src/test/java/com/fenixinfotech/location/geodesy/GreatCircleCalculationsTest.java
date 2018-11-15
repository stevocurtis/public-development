package com.fenixinfotech.location.geodesy;

import org.junit.Test;

import static org.junit.Assert.*;

public class GreatCircleCalculationsTest {

    private static final double BERLIN_LATITUDE = 52.507950;
    private static final double BERLIN_LONGITUDE = 13.408941;
    private static final double BRASILIA_LATITUDE = -15.786499;
    private static final double BRASILIA_LONGITUDE = -47.870515;
    private static final double DUBLIN_LATITUDE = 53.341545;
    private static final double DUBLIN_LONGITUDE = -6.254362;
    private static final double GREENWICH_LATITUDE = 51.482211;
    private static final double GREENWICH_LONGITUDE = 0;
    private static final double GULF_OF_GUINEA_LATITUDE = 0;
    private static final double GULF_OF_GUINEA_LONGITUDE = 0;
    private static final double NORTH_POLE_LATITUDE = 90;
    private static final double NORTH_POLE_LONGITUDE = 0;
    private static final double SOUTH_POLE_LATITUDE = -90;
    private static final double SOUTH_POLE_LONGITUDE = 0;
    private static final double WASHINGTON_LATITUDE = 38.900688;
    private static final double WASHINGTON_LONGITUDE = -77.033640;

    private static final double DEFAULT_DISTANCE_METRES = 500;
    @Test
    public void getHorizontalLatitudeRange() {
        // BERLIN
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, BERLIN_LATITUDE);

        // BRASILIA
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, BRASILIA_LATITUDE);

        // DUBLIN
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, DUBLIN_LATITUDE);

        // GREENWICH
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, GREENWICH_LATITUDE);

        // GULF_OF_GUINEA
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, GULF_OF_GUINEA_LATITUDE);

        // NORTH_POLE
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, NORTH_POLE_LATITUDE);

        // SOUTH_POLE
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, SOUTH_POLE_LATITUDE);

        // WASHINGTON
        GreatCircleCalculations.getLatLongOffsets(DEFAULT_DISTANCE_METRES, WASHINGTON_LATITUDE);
    }
}