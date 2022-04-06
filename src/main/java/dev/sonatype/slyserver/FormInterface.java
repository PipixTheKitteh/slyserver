package dev.sonatype.slyserver;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("/fetch")
@RegisterRestClient
public interface FormInterface {

    @GET
    SerialisedForm getById(@QueryParam("id") String id);

}
