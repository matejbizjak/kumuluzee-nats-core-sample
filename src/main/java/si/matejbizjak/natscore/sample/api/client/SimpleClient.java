package si.matejbizjak.natscore.sample.api.client;

import com.kumuluz.ee.nats.core.annotations.RegisterNatsClient;
import com.kumuluz.ee.nats.core.annotations.Subject;

import java.util.concurrent.Future;

/**
 * @author Matej Bizjak
 */

@RegisterNatsClient(connection = "default")
public interface SimpleClient {

    @Subject(value = "simple1")
    void sendSimple(String value);

    String sendSimpleDynamicSubjectResponse(@Subject String subject, String value);

    @Subject(value = "simple2")
    String sendSimpleResponse(String value);

    @Subject(value = "simple_async")
    Future<String> sendSimpleResponseAsync(String value);

    @Subject("empty")
    String sendEmptyPayload(String value);
}
