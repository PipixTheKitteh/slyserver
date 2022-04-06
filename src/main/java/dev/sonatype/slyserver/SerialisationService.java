package dev.sonatype.slyserver;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class SerialisationService {

    private static final Logger log = Logger.getLogger(SerialisationService.class);

@Path("fetch")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public SerialisedForm fetch(@QueryParam("id") String id) {

    log.infof("asking for %s",id);

    SerialisedForm sf= new SerialisedForm();
    sf.dn="hello";
    sf.cn="b";

    return sf;
    }
}
