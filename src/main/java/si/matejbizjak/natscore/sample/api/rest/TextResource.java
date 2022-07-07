package si.matejbizjak.natscore.sample.api.rest;

import com.kumuluz.ee.nats.core.annotations.NatsClient;
import si.matejbizjak.natscore.sample.api.client.TextClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
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

    @POST
    public Response postText() {
        textClient.sendText("simple string");
        return Response.ok("A simple message was sent.").build();
    }

    @POST
    @Path("/withResponse")
    public Response postTextResponse() {
        String msgResponse = textClient.sendTextResponse("another simple string");
        return Response.ok(String.format("A simple message was sent. Even more, I also received a response: '%s'"
                , msgResponse)).build();
    }

    @POST
    @Path("/withResponseDynamicSubject")
    public Response postTextDynamicSubject() {
        String msgResponse = textClient.sendTextDynamicSubjectResponse("dynamic", "simple string with dynamic subject");
        return Response.ok(String
                .format("A simple message was sent to a dynamic subject. Even more, I also received a response: '%s'"
                        , msgResponse)).build();
    }
}
