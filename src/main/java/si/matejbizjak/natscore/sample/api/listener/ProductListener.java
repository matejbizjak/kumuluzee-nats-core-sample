package si.matejbizjak.natscore.sample.api.listener;

import com.kumuluz.ee.nats.core.annotations.NatsListener;
import com.kumuluz.ee.nats.core.annotations.Subject;
import si.matejbizjak.natscore.sample.api.entity.Product;

/**
 * @author Matej Bizjak
 */

@NatsListener
public class ProductListener {

    @Subject(value = "product1")
    public void receive(Product value) {
        System.out.println(value.getName());
    }

    @Subject(value = "product2")
    public String receiveAndReturnString(Product value) {
        System.out.println(value.getName());
        return value.getName().toLowerCase();
    }

    @Subject(value = "product3", connection = "secure")
    public Product receiveAndReturnProduct(Product value) {
        System.out.println(value.getName());
        value.setName(value.getName().toUpperCase());
        return value;
    }
}
