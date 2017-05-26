package org.ico2s.polen;

import com.google.gson.Gson;
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

public class PolenPublisher {

    HalPublisher<StringMessage> publisher;
    UUID publisherId;
    UUID secretKey;

    public PolenPublisher(HalPublisher<StringMessage> publisher, UUID publisherId, UUID secretKey) {

        this.publisher = publisher;
        this.publisherId = publisherId;
        this.secretKey = secretKey;
    }

    public PolenPublisher(String backendUrl, UUID publisherId, UUID secretKey) {

        this(new HalPublisherWsClient(backendUrl), publisherId, secretKey);

    }

    public PolenPublisher(UUID subscriberId, UUID secretKey) {

        this("http://polen.ico2s.org:5001/", subscriberId, secretKey);

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

    public void publishGenBank(String gbf, String channel, String topic) throws PublishException {

        StringMessage msg = new StringMessage();

        msg.getHeader().setType("genbank");
        msg.getHeader().setChannel(channel);
        msg.getHeader().setTopic(topic);
        msg.setContent(gbf);

        publisher.publish(publisherId, secretKey, msg);

    }

    public void publishFASTA(String fasta, String channel, String topic) throws PublishException {

        StringMessage msg = new StringMessage();

        msg.getHeader().setType("fasta");
        msg.getHeader().setChannel(channel);
        msg.getHeader().setTopic(topic);
        msg.setContent(fasta);

        publisher.publish(publisherId, secretKey, msg);

    }

    String sbolToString(SBOLDocument doc) throws UnsupportedEncodingException, SBOLConversionException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SBOLWriter.write(doc, byteArrayOutputStream);
        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    public void publishResource(String channel, String topic, Resource resource) {

        StringMessage msg = new StringMessage();

        Gson gson = new Gson();

        msg.getHeader().setType("resource");
        msg.getHeader().setChannel(channel);
        msg.getHeader().setTopic(topic);
        msg.setContent(gson.toJson(resource));

        publisher.publish(publisherId, secretKey, msg);

    }

    public void publishResource(String channel, String topic, String resourceUri, String name, String description) {

        Resource resource = new Resource();
        resource.resourceUri = resourceUri;
        resource.name = name;
        resource.description = description;

        publishResource(channel, topic, resource);

    }



}
