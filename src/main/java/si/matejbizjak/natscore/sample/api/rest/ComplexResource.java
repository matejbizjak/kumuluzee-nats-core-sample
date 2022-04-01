package si.matejbizjak.natscore.sample.api.rest;


import com.kumuluz.ee.nats.annotations.NatsClient;
import si.matejbizjak.natscore.sample.api.client.ComplexClient;
import si.matejbizjak.natscore.sample.api.entity.Demo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * @author Matej Bizjak
 */

@Path("/complex/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ComplexResource {

    @Inject
    @NatsClient
    private ComplexClient complexClient;

    private final Demo demo = new Demo("matej", 12.4, LocalDateTime.now(), 34
            , new Demo.InnerDemo("bizjak", 23.12f));

    @GET
    @Path("/")
    public Response getComplex() {
        complexClient.sendComplex(demo);
        return Response.ok("A complex message should have been sent.").build();
    }

    @GET
    @Path("/withResponse")
    public Response getComplexResponse() {
        String msgResponse = complexClient.sendComplexResponse(demo);
        return Response.ok(String.format("Even more, I also received a complex response. It says': '%s'"
                , msgResponse)).build();
    }

    @GET
    @Path("/withSuperResponse")
    public Response getSuperComplexResponse() {
        Demo demoResponse = complexClient.sendSuperComplexResponse(demo);
        return Response.ok(String.format("Even more, I also received a super complex response. It's name is': '%s'"
                , demoResponse.getName())).build();
    }
}
