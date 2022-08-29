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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @POST
    @Path("/withResponseProductsMap")
    public Response postProductsMapResponse() {
        Map<String, List<Product>> customerProductsMap = new HashMap<>();

        List<Product> products1 = new ArrayList<>();
        products1.add(new Product(1, "name1", "", null, 1, null, Instant.now()));
        products1.add(new Product(2, "name2", "", null, 2, null, Instant.now()));
        customerProductsMap.put("1", products1);

        List<Product> products2 = new ArrayList<>();
        products2.add(new Product(3, "name3", "", null, 3, null, Instant.now()));
        products2.add(new Product(4, "name4", "", null, 4, null, Instant.now()));
        customerProductsMap.put("2", products2);

        Map<String, List<Product>> productsMapResponse = productClient.sendProductsMapResponse(customerProductsMap);
//        return Response.ok(String.format("The map of products was sent. Even more, I also received a map of products as response. Its name is %s"
//                , productsMapResponse)).build();
        return Response.status(Response.Status.OK).entity(productsMapResponse).build();
    }
}
