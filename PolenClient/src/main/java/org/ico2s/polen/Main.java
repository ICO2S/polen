package org.ico2s.polen;

import java.util.UUID;

import java.util.List;
import org.sbolstandard.core2.*;

/**
 * Created by james on 5/25/17.
 */
public class Main {

    static UUID publisherId = UUID.randomUUID();
    static UUID subscriberId = UUID.randomUUID();
    static UUID secretKey = UUID.randomUUID();

    public static void main(String[] args) throws Exception
    {
        testPublish();
        testConsume();
    }

    public static void testPublish() throws Exception {

        SBOLPublisher publisher = new SBOLPublisher("http://localhost:5001/", publisherId, secretKey);

        SBOLDocument doc = new SBOLDocument();
        doc.setDefaultURIprefix("http://ico2s.org/");
        doc.createComponentDefinition("foo", ComponentDefinition.DNA);

        publisher.publishSBOL(doc, "testChannel", "testTopic");
    }

    public static void testConsume() throws Exception {

        SBOLSubscriber subscriber = new SBOLSubscriber("http://localhost:5000/", subscriberId, secretKey);

        List<SBOLDocument> sbol = subscriber.consume("testChannel", "testTopic", 1);

        for(SBOLDocument doc : sbol)
        {
            SBOLWriter.write(doc, System.out);
        }
    }
}

