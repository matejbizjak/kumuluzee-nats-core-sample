package si.matejbizjak.natscore.sample.api.listener;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.nats.core.annotations.NatsListener;
import com.kumuluz.ee.nats.core.annotations.Subject;
import si.matejbizjak.natscore.sample.api.dto.Product;

import java.util.List;
import java.util.Map;

/**
 * @author Matej Bizjak
 */

@NatsListener
public class ProductListener {

    private static final Logger LOG = LogManager.getLogger(ProductListener.class.getName());

    @Subject(value = "product1")
    public void receive(Product product) {
        LOG.info(String.format("Method receive received a product with the name %s in subject product1.", product.getName()));
    }

    @Subject(value = "product2")
    public String receiveAndReturnString(Product product) {
        LOG.info(String.format("Method receiveAndReturnString received a product with the name %s in subject product2.", product.getName()));
        return product.getName().toLowerCase();
    }

    @Subject(value = "product3", connection = "secure")
    public Product receiveAndReturnProduct(Product product) {
        LOG.info(String.format("Method receiveAndReturnProduct received a product with the name %s in subject product3.", product.getName()));
        product.setName(product.getName().toUpperCase());
        return product;
    }

    @Subject(value = "productMap", connection = "secure")
    public Map<String, List<Product>> receiveAndReturnProductsMap(Map<String, List<Product>> productsMap) {
        LOG.info("Method receiveAndReturnProductsMap received a map of products.");
        Product product10 = productsMap.get("1").get(0);
        product10.setName(product10.getName().toUpperCase());
        return productsMap;
    }
}
