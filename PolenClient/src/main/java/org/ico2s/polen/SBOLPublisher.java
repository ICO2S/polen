package org.ico2s.polen;

import com.microbasecloud.halogen.StringMessage;
import com.microbasecloud.halogen.publisher.HalPublisher;
import com.microbasecloud.halogen.publisher.HalPublisherWsClient;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.sbolstandard.core2.SBOLConversionException;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLWriter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class SBOLPublisher {

    HalPublisher<StringMessage> publisher;
    UUID publisherId;
    UUID secretKey;

    public SBOLPublisher(HalPublisher<StringMessage> publisher, UUID publisherId, UUID secretKey) {

        this.publisher = publisher;
        this.publisherId = publisherId;
        this.secretKey = secretKey;
    }

    public SBOLPublisher(String backendUrl, UUID publisherId, UUID secretKey) {

        this(new HalPublisherWsClient(backendUrl), publisherId, secretKey);

    }


    public void publishSBOL(SBOLDocument doc, String channel, String topic) throws PublishException {

        StringMessage msg = new StringMessage();

        msg.getHeader().setType("sbol");
        msg.getHeader().setChannel(channel);
        msg.getHeader().setTopic(topic);

        try {
            msg.setContent(sbolToString(doc));
        } catch (UnsupportedEncodingException e) {
            throw new PublishException(e);
        } catch (SBOLConversionException e) {
            throw new PublishException(e);
        }

        publisher.publish(publisherId, secretKey, msg);

    }

    String sbolToString(SBOLDocument doc) throws UnsupportedEncodingException, SBOLConversionException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SBOLWriter.write(doc, byteArrayOutputStream);
        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }



}
