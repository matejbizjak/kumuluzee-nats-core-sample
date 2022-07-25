package si.matejbizjak.natscore.sample.api.rest;

import com.kumuluz.ee.nats.core.annotations.NatsClient;
import si.matejbizjak.natscore.sample.api.client.ProductClient;
import si.matejbizjak.natscore.sample.api.dto.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.Instant;
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

    @POST
    public Response postProduct(Product product) {
        productClient.sendProduct(product);
        return Response.ok("The product was sent.").build();
    }

    @POST
    @Path("/withResponse")
    public Response postProductResponse(Product product) {
        String msgResponse = productClient.sendProductResponse(product);
        return Response.ok(String.format("The product was sent. Even more, I also received a String as response: %s"
                , msgResponse)).build();
    }

    @POST
    @Path("/withResponseProduct")
    public Response postProductResponseProduct(Product product) {
        Product demoResponse = productClient.sendProductResponseProduct(product);
        return Response.ok(String.format("The product was sent. Even more, I also received a product as response. Its name is %s"
                , demoResponse.getName())).build();
    }
}
