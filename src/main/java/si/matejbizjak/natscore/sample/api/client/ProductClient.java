package si.matejbizjak.natscore.sample.api.client;

import com.kumuluz.ee.nats.core.annotations.RegisterNatsClient;
import com.kumuluz.ee.nats.core.annotations.Subject;
import si.matejbizjak.natscore.sample.api.dto.Product;

import java.util.List;
import java.util.Map;

/**
 * @author Matej Bizjak
 */

@RegisterNatsClient
public interface ProductClient {

    @Subject(value = "product1")
    void sendProduct(Product product);

    @Subject(value = "product2")
    String sendProductResponse(Product product);

    @Subject(value = "product3", connection = "secure")
    Product sendProductResponseProduct(Product product);

    @Subject(value = "productMap", connection = "secure")
    Map<String, List<Product>> sendProductsMapResponse(Map<String, List<Product>> productsMap);
}
