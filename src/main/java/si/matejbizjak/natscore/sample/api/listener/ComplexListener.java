package si.matejbizjak.natscore.sample.api.listener;

import com.kumuluz.ee.nats.core.annotations.NatsListener;
import com.kumuluz.ee.nats.core.annotations.Subject;
import si.matejbizjak.natscore.sample.api.entity.Demo;

/**
 * @author Matej Bizjak
 */

@NatsListener
public class ComplexListener {

    @Subject(value = "complex1")
    public void receive(Demo value) {
        System.out.println(value.getName());
    }

    @Subject(value = "complex2")
    public String receiveAndReturnString(Demo value) {
        System.out.println(value.getName());
        return value.getName().toUpperCase();
    }

    @Subject(value = "complex3", connection = "secure")
    public Demo receiveAndReturnDemo(Demo value) {
        System.out.println(value.getName());
        value.setName(value.getName().toUpperCase());
        return value;
    }
}
