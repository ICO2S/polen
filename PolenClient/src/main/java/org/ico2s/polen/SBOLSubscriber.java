package org.ico2s.polen;

import com.microbasecloud.halogen.StringMessage;
import com.microbasecloud.halogen.subscriber.HalSubscriber;
import com.microbasecloud.halogen.subscriber.HalSubscriberWsClient;
import com.microbasecloud.halogen.subscriber.Subscription;
import org.sbolstandard.core2.SBOLConversionException;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLReader;
import org.sbolstandard.core2.SBOLValidationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SBOLSubscriber {

    HalSubscriber<StringMessage> subscriber;
    UUID subscriberId;
    UUID secretKey;

    public SBOLSubscriber(HalSubscriber<StringMessage> subscriber, UUID subscriberId, UUID secretKey) {

        this.subscriber = subscriber;
        this.subscriberId = subscriberId;
        this.secretKey = secretKey;

    }

    public SBOLSubscriber(String backendUrl, UUID subscriberId, UUID secretKey) {

        this(new HalSubscriberWsClient(backendUrl), subscriberId, secretKey);

    }
    public List<SBOLDocument> consume(String channel, String topic, int n) throws ConsumeException {

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(Subscription.Type.CHANNEL_TOPIC);
        subscription.setChannel(channel);
        subscription.setTopic(topic);

        try {
            return messagesToSbol(
                    subscriber.consume(subscriberId, secretKey, subscription, n)
            );
        } catch (SBOLValidationException e) {
            throw new ConsumeException(e);
        } catch (SBOLConversionException e) {
            throw new ConsumeException(e);
        } catch (IOException e) {
            throw new ConsumeException(e);
        }

    }

    public List<SBOLDocument> consume(String channel, int n) throws ConsumeException {

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(Subscription.Type.CHANNEL);
        subscription.setChannel(channel);

        try {
            return messagesToSbol(
                    subscriber.consume(subscriberId, secretKey, subscription, n)
            );
        } catch (SBOLValidationException e) {
            throw new ConsumeException(e);
        } catch (SBOLConversionException e) {
            throw new ConsumeException(e);
        } catch (IOException e) {
            throw new ConsumeException(e);
        }
    }

    List<SBOLDocument> messagesToSbol(List<StringMessage> messages) throws SBOLValidationException, SBOLConversionException, IOException {

        List<SBOLDocument> sbol = new ArrayList<SBOLDocument>();

        for(StringMessage message : messages) {

            ByteArrayInputStream stream = new ByteArrayInputStream(message.getContent().getBytes(StandardCharsets.UTF_8));

            sbol.add(SBOLReader.read(stream));
        }

        return sbol;
    }

}
