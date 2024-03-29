package si.matejbizjak.natscore.sample.api.client;

import com.kumuluz.ee.nats.core.annotations.RegisterNatsClient;
import com.kumuluz.ee.nats.core.annotations.Subject;

import java.util.concurrent.CompletableFuture;

/**
 * @author Matej Bizjak
 */

@RegisterNatsClient(connection = "default")
public interface TextClient {

    @Subject(value = "text1")
    void sendText(String value);

    @Subject(value = "text2")
    String sendTextResponse(String value);

    @Subject(value = "text3")
    CompletableFuture<String> sendTextResponseAsync(String value);

    @Subject(connection = "default")
    String sendTextDynamicSubjectResponse(@Subject String subject, String value);

}
