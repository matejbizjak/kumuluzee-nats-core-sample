package si.matejbizjak.natscore.sample.api.client;

import com.kumuluz.ee.nats.core.annotations.RegisterNatsClient;
import com.kumuluz.ee.nats.core.annotations.Subject;
import si.matejbizjak.natscore.sample.api.entity.Demo;

/**
 * @author Matej Bizjak
 */

@RegisterNatsClient
public interface ComplexClient {

    @Subject(value = "complex1")
    void sendComplex(Demo value);

    @Subject(value = "complex2")
    String sendComplexResponse(Demo value);

    @Subject(value = "complex3", connection = "secure")
    Demo sendSuperComplexResponse(Demo value);
}
