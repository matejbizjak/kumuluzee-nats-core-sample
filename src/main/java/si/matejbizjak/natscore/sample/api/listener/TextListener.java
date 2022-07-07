package si.matejbizjak.natscore.sample.api.listener;

import com.kumuluz.ee.nats.core.annotations.NatsListener;
import com.kumuluz.ee.nats.core.annotations.Subject;

/**
 * @author Matej Bizjak
 */

@NatsListener(connection = "default")
public class TextListener {

    @Subject(value = "text1")
    public void receive(String value) {
        System.out.println(value);
    }

    @Subject(value = "text2", queue = "group1")
    public String receiveAndReturn1(String value) {
        System.out.println(value);
        return value.toUpperCase();
    }

    @Subject(value = "text2", queue = "group1")
    public String receiveAndReturn2(String value) {
        System.out.println(value);
        return value.toLowerCase();
    }

    @Subject(value = "dynamic")
    public String receiveDynamicSubject(String value) {
        System.out.println(value);
        return value.toUpperCase();
    }
}
