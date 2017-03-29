# The POLEN Infrastructure
POLEN (PrOtocol For Linking External Nodes) is a cloud-based messaging infrastructure that allows publishing and retrieving messages. Using this system, data repositories for different types of data can be notified about newly available data. For example, when there is a new datasheet available for a biological part, it would be desirable to create a model for the part. However, repositories are often disconnected and it may not be straightforward to define a communication process between these repositories. POLEN can be used to link different repositories using a message-based communication system.

The system uses REST-based Web services to publish and query the published messages. Each data type is handled by a different endpoint. These data types include “Part”, “Model”, “Datasheet” and “Repository”, and can be extended. Data are also exposed using RSS feeds for each type. These feeds can be used by external tools to retrieve published messages.

In this messaging system, messages are simple, light-weight objects and do not contain the actual content of resources such as datasheets or models. Each message includes properties such as name, description, topic and resourceUri. The topic is used to specify the type of a message and resourceUri is used to point to where to get the actual resource from. Each message can also be customised using message properties, which are in the form of key/value pairs.

POLEN is currently being used to integrate Flowers Consortium resources using messages. For example, model repositories are informed about newly available datasheets to create new dynamic models for biological parts; relevant repositories are informed about these models; and part repositories can listen for new part messages to integrate more information about newly available biological parts and to provide provenance information.

# POLEN Messaging System Client
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
    
## Example Messages
The example below shows message created for the SBML model of a biological part. Its publisher is set to the Virtual Parts Repository.

    Message message=new Message();
	message.setTopic("Model");
	message.setName("SlrR SBML model");
	message.setDescription("An SBML model for the SlrR part");
	message.setResourceUri("http://www.virtualparts.org/part/BO_28778/sbml");
	message.setPublisher("http://www.virtualparts.org");
	message.addProperty("type", "SBML");		

Here, a new Datasheet message is created for a newly characterised promoter part. The publisher property is set to the SynBIS repository.

    Message message=new Message();
	message.setTopic("Datasheet");
	message.setName("pNlpD");
	message.setDescription("Genetic description of the pNlpD promoter");
	message.setResourceUri("http://synbis.bg.ic.ac.uk/webapi/rest/datasheet/sbol/28");
	message.setPublisher("http://synbis.bg.ic.ac.uk");

Data repositories can also be registered with POLEN. This registry can then be queried by tools to access various information from different repositories. In this example, a new Repository message for the the SynBioHub repository created.

	Message message=new Message();
    message.setTopic("Repository");
	message.setName("SynBioHub");
	message.setDescription("A repository for biological designs");
	message.setResourceUri("http://synbiohub.org");
	message.setPublisher("http://synbiohub.org");
	message.addProperty("type", "SBOL");

 
## Download the Client API
[Click here](https://github.com/ICO2S/polen/releases/download/version1.1/polen-client-1.1-withDependencies.jar) to download the JAR file.

[Click here](https://github.com/ICO2S/polen/releases/download/version1.1/polen-client-1.1-javadoc.jar) to download the Javadoc file.

# The REST-based Web service interface
Although the Client library is the advised method of access for messages, POLEN also comes with a REST-based Web interface. Using this Web service, messages can be submitted and retrieved as JSON objects.
## REST-based HTTP-POST interface
The following URL is used to submit POLEN messages.
```
   https://w3id.org/synbio/polen/publish/
```
This URL expects three different POST parameters:
- topic: The topic of the message. E.g. "Part" or "Model"
- publisher: Publisher of the message
- content: a JSON object with details as below:
  * name : The name of the message
  * description: The description of the message
  * uri : The uri pointing to where the actual data are
  * A JSON Map can also be included as part of the content property to provide applciation more custom information.
  

## REST-based HTTP-GET interface
The GET URLs has the following format:
```
https://w3id.org/synbio/polen/publish/messagesByTopic/{TOPIC}/{TIMESTAMP}/
{NUMBER_OF_MESSAGES}
```
The TOPIC, TIMESTAMP and NUMBER_OF_MESSAGES parameters can be used to create URLs to query the POLEN infrastructure. For example, the following URL returns the first two messages that are published since 1st Jan 2014 for the "Part" topic.

https://w3id.org/synbio/polen/messagesByTopic/Part/1388538000/2

## Retrieving messages using RSS feeds
Recent messages can also be retrieved using RSS feeds. These feeds have the following URL format:
```
https://w3id.org/synbio/polen/{TOPIC}.rss
```
For example, https://w3id.org/synbio/polen/Part.rss feed can be used to receive he recentlt published messages for the "Part" topic.




