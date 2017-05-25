package org.ico2s.polen;

import com.microbasecloud.halogen.StringMessage;
import com.microbasecloud.halogen.subscriber.HalSubscriber;
import com.microbasecloud.halogen.subscriber.HalSubscriberWsClient;
import com.microbasecloud.halogen.subscriber.Subscription;
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

    public SBOLSubscriber(UUID subscriberId, UUID secretKey) {

        this("http://polen.ico2s.org:5000/", subscriberId, secretKey);

    }

    public SBOLSubscription subscribe(String channel, String topic) {

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(Subscription.Type.CHANNEL_TOPIC);
        subscription.setChannel(channel);
        subscription.setTopic(topic);

        return new SBOLSubscription(this, subscription);

    }

    public SBOLSubscription subscribe(String channel, String topic, String type) {

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(Subscription.Type.CHANNEL_TOPIC_TYPE);
        subscription.setChannel(channel);
        subscription.setTopic(topic);
        subscription.setType(type);

        return new SBOLSubscription(this, subscription);

    }

    public SBOLSubscription subscribe(String channel) {

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(Subscription.Type.CHANNEL);
        subscription.setChannel(channel);

        return new SBOLSubscription(this, subscription);

    }

    public void reset() {

        subscriber.reset(subscriberId, secretKey);

    }
}
