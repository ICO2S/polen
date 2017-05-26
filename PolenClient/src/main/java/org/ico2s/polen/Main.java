package org.ico2s.polen;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import java.util.List;
import org.sbolstandard.core2.*;

public class Main {

    static UUID publisherId = UUID.nameUUIDFromBytes("test3".getBytes(StandardCharsets.UTF_8));
    static UUID subscriberId = UUID.nameUUIDFromBytes("subscriberTest3".getBytes(StandardCharsets.UTF_8));
    static UUID secretKey = UUID.nameUUIDFromBytes("secret3".getBytes(StandardCharsets.UTF_8));

    public static void main(String[] args) throws Exception
    {
        testPublish();
        testConsume();
    }

    public static void testPublish() throws Exception {

        PolenPublisher publisher = new PolenPublisher("http://localhost:5001/", publisherId, secretKey);

    //    SBOLDocument doc = new SBOLDocument();
      //  doc.setDefaultURIprefix("http://ico2s.org/");
        //doc.createComponentDefinition("foo", ComponentDefinition.DNA);

        publisher.publishFASTA(">test\nMVKTVVTVVTKAKKTASQUR", "testChannel", "testTopic");
    }

    public static void testConsume() throws Exception {

        PolenSubscriber subscriber = new PolenSubscriber("http://localhost:5000/", subscriberId, secretKey);

        SBOLSubscription subscription =  subscriber.subscribeToSBOL("testChannel", "testTopic");
        List<SBOLDocument> sbol = subscription.consume(2000);

        for(SBOLDocument doc : sbol)
        {
            SBOLWriter.write(doc, System.out);
        }
    }
}

