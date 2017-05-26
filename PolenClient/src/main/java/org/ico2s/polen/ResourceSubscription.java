package org.ico2s.polen;

import com.google.gson.Gson;
import com.microbasecloud.halogen.StringMessage;
import com.microbasecloud.halogen.subscriber.HalSubscriber;
import com.microbasecloud.halogen.subscriber.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 26/05/17.
 */
public class ResourceSubscription {


    PolenSubscriber polenSubscriber;
    Subscription subscription;

    public ResourceSubscription(PolenSubscriber polenSubscriber, Subscription subscription)
    {
        this.polenSubscriber = polenSubscriber;
        this.subscription = subscription;
    }


    public List<Resource> consume(int n) throws ConsumeException {

        HalSubscriber<StringMessage> subscriber = polenSubscriber.subscriber;

        return messagesToResources(
                subscriber.consume(polenSubscriber.subscriberId, polenSubscriber.secretKey, subscription, n)
        );

    }

    List<Resource> messagesToResources(List<StringMessage> messages) {

        List<Resource> resources = new ArrayList<Resource>();

        Gson gson = new Gson();

        for(StringMessage message : messages) {

            resources.add(gson.fromJson(message.getContent(), Resource.class));
        }

        return resources;
    }
}
