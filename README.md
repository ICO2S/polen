#POLEN Messaging System Client
The entry point for this api is MessagingSystem, which allows you to manage messages. The uk.ac.ncl.flowers.polen.util package contains support code which in normal usage you should not need to use.

In this messaging system, messages are simple, light-weight objects and do not contain the actual content of resources such as datasheets or models. Each message includes properties such as name, description, topic and resourceUri. The topic is used to specify the type of a message and resourceUri is used to point to where to get the actual resource from.

    message.setTopic("Part");
    message.setName("spo0A_part");
    message.setDescription("spo0A promoter part");
    message.setResourceUri("http://www.virtualparts.org/part/spo0A/sbol");
    message.setPublisher("http://flowers.partsregistry.org");
 

The messaging system is identified with a URI which is used to publish messages and to query the messaging system to get messages. Once a MessagingSystem object is initialised with the URI of the messaging system, the publish method can be used to send messages

    String serviceUri = "http://synbio.ncl.ac.uk:8083/notification";
    MessagingSystem messagingSystem = new MessagingSystem.Microbase(serviceUri);
    messagingSystem.publish(message);
 

Similarly the messaging system can be queried for types of messages with additional querying parameters. The example code below is used to poll messages that are for Datasheets, and are published on or after 01/12/2013. The maximum number of messages to poll is specified as 2.

    List messages = messagingSystem.poll("Datasheet", new Date(2013, 12, 1), 2);
 
##Download the Client API
[Click here](https://bitbucket.org/ncl-intbio/polen/downloads/polen-client-1.0-withDependencies.jar) to download the JAR file.

[Click here](https://bitbucket.org/ncl-intbio/polen/downloads/polen-client-1.0-javadoc.jar) to download the Javadoc file.
