package com.fenixinfotech.location.geodesy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.PI;

public class GreatCircleCalculations
{
    private static final Logger logger = LoggerFactory.getLogger(GreatCircleCalculations.class);
    public static final int EARTH_RADIUS_IN_METRES = 6366710;

    /**
     * Convenience method to wrap lat and long calcs in one call
     *
     * @param distanceMetres
     * @param latitudeDegrees
     * @return Array of offsets, first is lat and second on long
     */
    public static double[] getLatLongOffsets(double distanceMetres, double latitudeDegrees) {
        double[] latLongOffsets = new double[2];
        latLongOffsets[0] = getLatitudeOffset(distanceMetres, latitudeDegrees);
        latLongOffsets[1] = getLongitudeOffset(distanceMetres, latitudeDegrees);

        logger.info("found latLongOffsets {} for distanceMetres {} from latitudeDegrees {}", latLongOffsets, distanceMetres, latitudeDegrees);
        return latLongOffsets;
    }

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
        double latitudeOffset = (distanceMetres / EARTH_RADIUS_IN_METRES) * (180 / PI);
        logger.trace("found latitudeOffset {} for distanceMetres {} from latitudeDegrees {}", latitudeOffset, distanceMetres, latitudeDegrees);
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
     * @return Latitude Offset in Degrees
     */
    public static double getLongitudeOffset(double distanceMetres, double latitudeDegrees) {
        double absoluteLatitudeDegrees = Math.abs(latitudeDegrees);

        double longitudeOffset;
        if (absoluteLatitudeDegrees >= 90) {
            // hit polar caps
            longitudeOffset = 0;
        }
        else {
            longitudeOffset = (distanceMetres / EARTH_RADIUS_IN_METRES) * ((180 / PI) / Math.cos(Math.toRadians(absoluteLatitudeDegrees)));
        }
        logger.trace("found longitudeOffset {} for distanceMetres {} from latitudeDegrees {}", longitudeOffset, distanceMetres, latitudeDegrees);
        return longitudeOffset;
    }
}