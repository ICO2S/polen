package org.ico2s.polen;

import com.microbasecloud.halogen.StringMessage;
import com.microbasecloud.halogen.subscriber.HalSubscriber;
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


public class SBOLSubscription {

    SBOLSubscriber sbolSubscriber;
    Subscription subscription;

    public SBOLSubscription(SBOLSubscriber sbolSubscriber, Subscription subscription)
    {
        this.sbolSubscriber = sbolSubscriber;
        this.subscription = subscription;
    }

    public List<SBOLDocument> consume(int n) throws ConsumeException {

        HalSubscriber<StringMessage> subscriber = sbolSubscriber.subscriber;

        try {
            return messagesToSbol(
                    subscriber.consume(sbolSubscriber.subscriberId, sbolSubscriber.secretKey, subscription, n)
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

            SBOLReader.setURIPrefix("http://ico2s.github.io/polen/");
            sbol.add(SBOLReader.read(stream));
        }

        return sbol;
    }
}
