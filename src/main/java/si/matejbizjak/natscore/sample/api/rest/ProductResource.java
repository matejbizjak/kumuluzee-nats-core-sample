package si.matejbizjak.natscore.sample.api.rest;

import com.kumuluz.ee.nats.core.annotations.NatsClient;
import si.matejbizjak.natscore.sample.api.client.ProductClient;
import si.matejbizjak.natscore.sample.api.entity.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * @author Matej Bizjak
 */

@Path("/product/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProductResource {

    @Inject
    @NatsClient
    private ProductClient productClient;

    private final Product corn = new Product(1, "Corn", "Corn for popcorn - 1 kg", 3.2f, 12, null, LocalDateTime.now());

    @POST
    public Response postProduct() {
        productClient.sendProduct(corn);
        return Response.ok("The product was sent.").build();
    }

    @POST
    @Path("/withResponse")
    public Response postProductResponse() {
        String msgResponse = productClient.sendProductResponse(corn);
        return Response.ok(String.format("The product was sent. Even more, I also received a String as response: '%s'"
                , msgResponse)).build();
    }

    @POST
    @Path("/withResponseProduct")
    public Response postProductResponseProduct() {
        Product demoResponse = productClient.sendProductResponseProduct(corn);
        return Response.ok(String.format("The product was sent. Even more, I also received a product as response: '%s'"
                , demoResponse.getName())).build();
    }
}
