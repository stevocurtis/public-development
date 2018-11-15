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
    private static final double WASHINGTON_LATITUDE = 38.900688;
    private static final double WASHINGTON_LONGITUDE = -77.033640;

    private static final double DEFAULT_DISTANCE_METRES = 500;
    @Test
    public void getHorizontalLatitudeRange() {
        // BERLIN
        GreatCircleCalculations.getLatitudeOffset(DEFAULT_DISTANCE_METRES, BERLIN_LATITUDE);
        GreatCircleCalculations.getLongitudeOffset(DEFAULT_DISTANCE_METRES, BERLIN_LATITUDE, BERLIN_LONGITUDE);

        // BRASILIA
        GreatCircleCalculations.getLatitudeOffset(DEFAULT_DISTANCE_METRES, BRASILIA_LATITUDE);
        GreatCircleCalculations.getLongitudeOffset(DEFAULT_DISTANCE_METRES, BRASILIA_LATITUDE, BRASILIA_LONGITUDE);

        // DUBLIN
        GreatCircleCalculations.getLatitudeOffset(DEFAULT_DISTANCE_METRES, DUBLIN_LATITUDE);
        GreatCircleCalculations.getLongitudeOffset(DEFAULT_DISTANCE_METRES, DUBLIN_LATITUDE, DUBLIN_LONGITUDE);

        // GULF_OF_GUINEA
        GreatCircleCalculations.getLatitudeOffset(DEFAULT_DISTANCE_METRES, GULF_OF_GUINEA_LATITUDE);
        GreatCircleCalculations.getLongitudeOffset(DEFAULT_DISTANCE_METRES, GULF_OF_GUINEA_LATITUDE, GULF_OF_GUINEA_LONGITUDE);

        // GREENWICH
        GreatCircleCalculations.getLatitudeOffset(DEFAULT_DISTANCE_METRES, GREENWICH_LATITUDE);
        GreatCircleCalculations.getLongitudeOffset(DEFAULT_DISTANCE_METRES, GREENWICH_LATITUDE, GREENWICH_LONGITUDE);

        // WASHINGTON
        GreatCircleCalculations.getLatitudeOffset(DEFAULT_DISTANCE_METRES, WASHINGTON_LATITUDE);
        GreatCircleCalculations.getLongitudeOffset(DEFAULT_DISTANCE_METRES, WASHINGTON_LATITUDE, WASHINGTON_LONGITUDE);
    }
}