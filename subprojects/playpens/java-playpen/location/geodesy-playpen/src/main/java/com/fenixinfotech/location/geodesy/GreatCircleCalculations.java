package com.fenixinfotech.location.geodesy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.PI;

public class GreatCircleCalculations
{
    private static final Logger logger = LoggerFactory.getLogger(GreatCircleCalculations.class);
    public static final int EARTH_RADIUS_IN_METRES = 6366710;

    /**
     * The number of kilometers per degree of latitude is approximately the same at all locations, approx
     *
     * (2*pi/360) * r_earth = 111 km / degree
     *
     * @param distanceMetres
     * @param latitudeDegrees
     * @return Longitude Offset in Degrees
     */
    public static double getLatitudeOffset(double distanceMetres, double latitudeDegrees) {
        double latitudeOffset = Math.abs(latitudeDegrees  + (distanceMetres / EARTH_RADIUS_IN_METRES) * (180 / PI));
        logger.debug("found latitudeOffset {} for distanceMetres {} from latitudeDegrees {}", latitudeOffset, distanceMetres, latitudeDegrees);
        return latitudeOffset;
    }

    /**
     * The number of kilometers per degree of longitude is approximately
     *
     * (pi/180) * r_earth * cos(theta)
     *
     * Where theta is the latitude in degrees.
     *
     * @param distanceMetres
     * @param latitudeDegrees
     * @param longitudeDegrees
     * @return Latitude Offset in Degrees
     */
    public static double getLongitudeOffset(double distanceMetres, double latitudeDegrees, double longitudeDegrees) {
        double longitudeOffset = Math.abs(longitudeDegrees + (distanceMetres / EARTH_RADIUS_IN_METRES) * (180 / PI) / Math.cos(Math.toRadians(latitudeDegrees)));
        logger.debug("found longitudeOffset {} for distanceMetres {} from latitudeDegrees {} longitudeDegrees {}", longitudeOffset, distanceMetres, latitudeDegrees, longitudeDegrees);
        return longitudeOffset;
    }
}