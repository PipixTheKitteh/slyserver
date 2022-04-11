package dev.sonatype.slyserver;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/door")
public class LDAPFrontDoor {


    @Inject
    @RestClient
    FormInterface extensionsService;

}

