package si.matejbizjak.natscore.sample.api.listener;

import com.kumuluz.ee.nats.core.annotations.NatsListener;
import com.kumuluz.ee.nats.core.annotations.Subject;

/**
 * @author Matej Bizjak
 */

@NatsListener(connection = "default")
public class SimpleListener {

    @Subject(value = "simple1")
    public void receive(String value) {
        System.out.println(value);
    }

    @Subject(value = "simple2", queue = "group1")
    public String receiveAndReturn1(String value) {
        System.out.println(value);
        return value.toUpperCase();
    }

    @Subject(value = "simple2", queue = "group1")
    public String receiveAndReturn2(String value) {
        System.out.println(value);
        return value.toLowerCase();
    }

    @Subject(value = "dynamic")
    public String receiveDynamicSubject(String value) {
        System.out.println(value);
        return value.toUpperCase() + "_DYNAMIC_SUBJECT";
    }

    @Subject(value = "simple_async")
    public String receiveAndReturnAsync(String value) {
        System.out.println(value);
        return value.toUpperCase() + "_ASYNC";
    }

    @Subject(value = "empty")
    public String receiveEmpty(String value) {
        System.out.println(value);
        return value != null ? value.toUpperCase() + "_EMPTY_SUBJECT" : null;
//        return value.toUpperCase();
    }
}
