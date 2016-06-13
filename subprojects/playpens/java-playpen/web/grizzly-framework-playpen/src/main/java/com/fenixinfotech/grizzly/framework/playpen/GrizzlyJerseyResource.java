package com.fenixinfotech.grizzly.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Path("jerseyresource")
public class GrizzlyJerseyResource
{
    private static final Logger logger = LoggerFactory.getLogger(GrizzlyJerseyResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt()
    {
        return updateResponse();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response postIt()
    {
        return updateResponse();
    }

    private Response updateResponse()
    {
        long startTime = new Date().getTime();
        final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.UK);
        final String date = format.format(new Date(System.currentTimeMillis()));

        Response response = Response.ok(date).build();

        logger.info("processed request in {} ms", new Date().getTime() - startTime);
        return response;
    }
}