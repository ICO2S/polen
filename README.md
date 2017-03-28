#The POLEN Infrastructure
POLEN (PrOtocol For Linking External Nodes) is a cloud-based messaging infrastructure that allows publishing and retrieving messages. Using this system, data repositories for different types of data can be notified about newly available data. For example, when there is a new datasheet available for a biological part, it would be desirable to create a model for the part. However, repositories are often disconnected and it may not be straightforward to define a communication process between these repositories. POLEN can be used to link different repositories using a message-based communication system.

The system uses REST-based Web services to publish and query the published messages. Each data type is handled by a different endpoint. These data types include “Part”, “Model”, “Datasheet” and “Repository”, and can be extended. Data are also exposed using RSS feeds for each type. These feeds can be used by external tools to retrieve published messages.

In this messaging system, messages are simple, light-weight objects and do not contain the actual content of resources such as datasheets or models. Each message includes properties such as name, description, topic and resourceUri. The topic is used to specify the type of a message and resourceUri is used to point to where to get the actual resource from. Each message can also be customised using message properties, which are in the form of key/value pairs.

POLEN is currently being used to integrate Flowers Consortium resources using messages. For example, model repositories are informed about newly available datasheets to create new dynamic models for biological parts; relevant repositories are informed about these models; and part repositories can listen for new part messages to integrate more information about newly available biological parts and to provide provenance information.

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
