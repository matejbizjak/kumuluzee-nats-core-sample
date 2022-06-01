package si.matejbizjak.natscore.sample.api.rest;

import com.kumuluz.ee.nats.core.annotations.NatsClient;
import si.matejbizjak.natscore.sample.api.client.SimpleClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Matej Bizjak
 */

@Path("/simple/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class SimpleResource {

    @Inject
    @NatsClient
    private SimpleClient simpleClient;

    @GET
    @Path("/")
    public Response getSimple() {
        simpleClient.sendSimple("simple string");
        return Response.ok("A simple message should have been sent.").build();
    }

    @GET
    @Path("/dynamicSubject")
    public Response getSimpleDynamicSubject() {
        String msgResponse = simpleClient.sendSimpleDynamicSubjectResponse("dynamic", "simple string with dynamic subject");
        return Response.ok(String.format("Even more, I also received a response from a dynamic subject. It says: '%s'", msgResponse)).build();
    }

    @GET
    @Path("/withResponse")
    public Response getSimpleResponse() {
        String msgResponse = simpleClient.sendSimpleResponse("another simple string");
        return Response.ok(String.format("Even more, I also received a response. It says: '%s'", msgResponse)).build();
    }

    @GET
    @Path("/withResponseAsync")
    public Response getSimpleResponseAsync() {
        Future<String> future = simpleClient.sendSimpleResponseAsync("another simple string");
        try {
            String msgResponse = future.get();
            return Response.ok(String.format("Even more, I also received a response asynchronously. It says: '%s'", msgResponse)).build();
        } catch (ExecutionException | InterruptedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/emptyPayload")
    public Response getEmptySubjectResponse() {
        String msgResponse = simpleClient.sendEmptyPayload(null);
        return Response.ok(String.format("Sending empty payload. The response was: '%s'", msgResponse)).build();
    }
}
