package com.fenixinfotech.simple.framework.playpen;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("jerseyresource")
public class JerseyResource
{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt()
    {
        return FrameworkServerBase.defaultResponseBody;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String postIt()
    {
        return FrameworkServerBase.defaultResponseBody;
    }
}
