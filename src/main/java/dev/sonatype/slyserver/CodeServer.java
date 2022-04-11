package dev.sonatype.slyserver;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;

@Path("/code")

public class CodeServer {

    private static final Logger log = Logger.getLogger(CodeServer.class);

    @GET
    @Path("{id  : .+}")
    public InputStream getCode(@Context UriInfo uriInfo, @PathParam("id") String id)  {

        String req=uriInfo.getPath().substring(5);
        log.infof("requesting code for %s",req);
        req=id;
        InputStream in= CodeServer.class.getResourceAsStream("/"+req);
        log.infof("code stream %s",in);

        return in;


    }


}
