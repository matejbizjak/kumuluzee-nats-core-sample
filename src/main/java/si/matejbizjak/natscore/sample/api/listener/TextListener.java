package si.matejbizjak.natscore.sample.api.listener;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.nats.core.annotations.NatsListener;
import com.kumuluz.ee.nats.core.annotations.Subject;

/**
 * @author Matej Bizjak
 */

@NatsListener(connection = "default")
public class TextListener {

    private static final Logger LOG = LogManager.getLogger(TextListener.class.getName());

    @Subject(value = "text1")
    public void receive(String value) {
        LOG.info(String.format("Method receive received message %s in subject text1.", value));
    }

    @Subject(value = "text2", queue = "group1")
    public String receiveAndReturn1(String value) {
        LOG.info(String.format("Method receiveAndReturn1 received message %s in subject text2.", value));
        return value.toUpperCase();
    }

    @Subject(value = "text2", queue = "group1")
    public String receiveAndReturn2(String value) {
        LOG.info(String.format("Method receiveAndReturn2 received message %s in subject text2.", value));
        return value.toLowerCase();
    }

    @Subject(value = "text3")
    public String receiveAndReturn3(String value) {
        LOG.info(String.format("Method receiveAndReturn3 received message %s in subject text3.", value));
        return "async: " + value;
    }

    @Subject(value = "dynamic")
    public String receiveDynamicSubject(String value) {
        LOG.info(String.format("Method receiveDynamicSubject received message %s in subject dynamic.", value));
        return value.toUpperCase();
    }
}
