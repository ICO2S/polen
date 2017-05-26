# The POLEN Infrastructure
POLEN (PrOtocol For Linking External Nodes) is a cloud-based messaging infrastructure that allows publishing and retrieving messages. Using this system, data repositories for different types of data can be notified about newly available data. For example, when there is a new datasheet available for a biological part, it would be desirable to create a model for the part. However, repositories are often disconnected and it may not be straightforward to define a communication process between these repositories. POLEN can be used to link different repositories using a message-based communication system.

The system uses REST-based Web services to publish and query the published messages. Each data type is handled by a different endpoint. These data types include `sbol` for a [Synthetic Biology Open Language (SBOL)](http://sbolstandard.org) document and `resource` for a resource such as a datasheet or repository, and any further user defined data type is also accepted by the system.  POLEN can be used for both the transmission of actual data, such as SBOL documents, or for exchanging lightweight pointers describing where a resource can be retrieved.  Messages can be published on different channels and with different topics to make sure they are only broadcast to interested subscribers.

POLEN is currently being used to integrate Flowers Consortium resources using messages. For example, model repositories are informed about newly available datasheets to create new dynamic models for biological parts; relevant repositories are informed about these models; and part repositories can listen for new part messages to integrate more information about newly available biological parts and to provide provenance information.

# POLEN Messaging System Client
The entry points for this API are the `PolenPublisher` and `PolenSubscriber` classes, which allow you to publish new messages to the network and to register your interest in receiving published messages.  For example, to publish an SBOL document as a resource, the publishSBOL method can be used:

    // Create our example part
    //
    SBOLDocument doc = new SBOLDocument();
    doc.setDefaultUriprefix("http://synbiuk.org/");
    ComponentDefinition pTet = doc.createComponentDefinition("pTet", ComponentDefinition.DNA);
    Sequence seq = doc.createSequence("pTetSeq", Sequence.IUPAC_DNA);
    seq.setElements("tccctatcagtgatagagattgacatccctatcagtgatagagatactgagcac");
    pTet.addSequence(seq);
    
    // Publish to POLEN
    //
    PolenPublisher publisher = new PolenPublisher(MY_PUBLISHER_ID, SECRET_KEY);
    publisher.publishSBOL(doc, "FLOWERS Consortium", "parts");

The POLEN client API also supports FASTA and GenBank.  FASTA and GenBank can be published as-is to the POLEN network, and the client library will automatically convert them to SBOL for subscribers.

    PolenPublisher publisher = new PolenPublisher(MY_PUBLISHER_ID, SECRET_KEY);
    publisher.publishFASTA(">pLac\naattgtgagcggataacaattgacattgtgagcggataacaagatactgagcaca", "FLOWERS Consortium", "parts");

    PolenSubscriber subscriber = new PolenSubscriber(MY_PUBLISHER_ID, SECRET_KEY);
    SBOLSubscription subscription = subscriber.subscribeToSBOL("FLOWERS Consortium", "parts");
    List<SBOLDocument> parts = subscription.consume(2);

    for(SBOLDocument doc : parts) {
        /* ... */
    }   

## Example Messages
The example below shows message created for the SBML model of a biological part. Its URI points to the Virtual Parts Repository.

    Resource resource = new ResourceMessage();
    resource.name = "SlrR SBML model"
    resource.resourceUri = "http://www.virtualparts.org/part/BO_28778/sbml";
    resource.description = "An SBML model for the SlrR part"

    PolenPublisher publisher = new PolenPublisher(MY_PUBLISHER_ID, SECRET_KEY);
    publisher.publishResource("FLOWERS Consortium", "models", resource);

Here, a new message is created for a newly characterised promoter part. Its URI points to the SynBIS repository.

    Resource resource = new ResourceMessage();
    resource.name = "SlrR SBML model"
    resource.resourceUri = "http://synbis.bg.ic.ac.uk/webapi/rest/datasheet/sbol/28";
    resource.description = "Genetic description of the pNlpD promoter";

    PolenPublisher publisher = new PolenPublisher(MY_PUBLISHER_ID, SECRET_KEY);
    publisher.publishResource("FLOWERS Consortium", "datasheets", resource);

Data repositories can also be registered with POLEN. This registry can then be queried by tools to access various information from different repositories. In this example, a new repository message for the the SynBioHub repository is created.

    Resource resource = new ResourceMessage();
    resource.name = "SynBioHub"
    resource.resourceUri = "http://synbiohub.org";
    resource.description = "A repository for biological designs";

    PolenPublisher publisher = new PolenPublisher(MY_PUBLISHER_ID, SECRET_KEY);
    publisher.publishResource("FLOWERS Consortium", "repositories", resource);
 

