package si.matejbizjak.natscore.sample.api.rest;

import com.kumuluz.ee.nats.core.annotations.NatsClient;
import si.matejbizjak.natscore.sample.api.client.TextClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Matej Bizjak
 */

@Path("/text/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TextResource {

    @Inject
    @NatsClient
    private TextClient textClient;

    @GET
    public Response getText() {
        textClient.sendText("simple string");
        return Response.ok("A simple message was sent.").build();
    }

    @GET
    @Path("/withResponse")
    public Response getTextResponse() {
        String msgResponse = textClient.sendTextResponse("another simple string");
        return Response.ok(String.format("A simple message was sent. Even more, I also received a response: '%s'"
                , msgResponse)).build();
    }

    @GET
    @Path("/withResponseDynamicSubject")
    public Response getTextDynamicSubject() {
        String msgResponse = textClient.sendTextDynamicSubjectResponse("dynamic", "simple string with dynamic subject");
        return Response.ok(String
                .format("A simple message was sent to a dynamic subject. Even more, I also received a response: '%s'"
                        , msgResponse)).build();
    }
}
