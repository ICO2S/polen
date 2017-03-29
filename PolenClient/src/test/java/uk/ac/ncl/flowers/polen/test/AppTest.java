package uk.ac.ncl.flowers.polen.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import uk.ac.ncl.flowers.polen.Message;
import uk.ac.ncl.flowers.polen.MessagingException;
import uk.ac.ncl.flowers.polen.MessagingSystem;
import uk.ac.ncl.flowers.polen.util.MessageSerializer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	private String polenUri="https://w3id.org/synbio/polen";
	//private String polenUri="http://w3id.org/synbio/polen";
	//private String polenUri = "http://synbio.ncl.ac.uk:8083/notification";
	
	
	private Message makeTestMessage() {
		Message message = new Message();

		message.setTopic("TestTopic");
		message.setName("spo0A_part");
		message.setDescription("spo0A promoter part");
		message.setResourceUri("http://www.virtualparts.org/part/spo0A/sbol");
		message.setPublisher("http://flowers.partsregistry.org");
		message.addProperty("type", "Promoter");

		return message;
	}

	public void testMessageToJson() throws Exception {
		Message message = makeTestMessage();
		message.addProperty("testproperty1", "value1");
		message.addProperty("testproperty2", "value2");
		MessageSerializer serializer = new MessageSerializer();
		String json = serializer.messageToJson(message);
		System.out.println("Message:" + json);
	}

	public void testMessagesToJson() throws Exception {
		List<Message> messages = new ArrayList<Message>();
		messages.add(makeTestMessage());
		messages.add(makeTestMessage());

		MessageSerializer serializer = new MessageSerializer();
		String json = serializer.messageToJson(messages);
		System.out.println("Messages:" + json);
	}

	/*public void testPost() throws MessagingException {
		Message message = makeTestMessage();
		String serviceUri = "http://synbio.ncl.ac.uk:8083/notification";
		MessagingSystem messagingSystem = new MessagingSystem.Microbase(
				serviceUri);
		messagingSystem.publish(message);
	}*/

	public void testGet() throws MessagingException {
		//String serviceUri = "http://synbio.ncl.ac.uk:8083/notification";
		
		MessagingSystem messagingSystem = new MessagingSystem.Microbase(
				this.polenUri);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Date date=null;
		try
		{
			date=sdf.parse("2014-02-25");		
		}
		catch(Exception ex){}
		
		List<Message> messages = messagingSystem.poll("Model", date, 1);
		System.out.println("------------------Retrieving model messages:------------------");
		System.out.println(messages);
		System.out.println("------------------------------------------------------");		
		System.out.println();		

	}
	
	public void testNewModelMessage() throws MessagingException {
		Message message=new Message();
		message.setTopic("Model");
		message.setName("SlrR SBML model");
		message.setDescription("An SBML model for the SlrR part");
		message.setResourceUri("http://www.virtualparts.org/part/BO_28778/sbml");
		message.setPublisher("http://www.virtualparts.org");
		message.addProperty("type", "SBML");		
		MessageSerializer serializer = new MessageSerializer();
		String json = serializer.messageToJson(message);
		System.out.println("------------------New Model Message:------------------");
		System.out.println(json);
		System.out.println("------------------------------------------------------");				
	}
	
	public void testNewDatasheetMessage() throws MessagingException {
		Message message=new Message();
		message.setTopic("Datasheet");
		message.setName("pNlpD");
		message.setDescription("Genetic description of the pNlpD promoter");
		message.setResourceUri("http://synbis.bg.ic.ac.uk/webapi/rest/datasheet/sbol/28");
		message.setPublisher("http://synbis.bg.ic.ac.uk");
		MessageSerializer serializer = new MessageSerializer();
		String json = serializer.messageToJson(message);
		System.out.println("------------------New Datasheet Message:------------------");
		System.out.println(json);
		System.out.println("------------------------------------------------------");				
	}
	
	
	
	private Message makeSynBioHubRepositoryMessage(String topic) throws MessagingException {
		Message message=new Message();
		message.setTopic(topic);
		message.setName("SynBioHub");
		message.setDescription("A repository for biological designs");
		message.setResourceUri("http://synbiohub.org");
		message.setPublisher("http://synbiohub.org");
		message.addProperty("type", "SynBioHub");
		return message;					
	}
		
	
	public void testSynBioHubRepositoryMessage() throws MessagingException {
		Message message=makeSynBioHubRepositoryMessage("Repository");		
		MessageSerializer serializer = new MessageSerializer();
		String json = serializer.messageToJson(message);
		System.out.println("------------------New Repository Message:------------------");
		System.out.println(json);
		System.out.println("------------------------------------------------------");				
	}
	
	public void testICERepositoryMessage() throws MessagingException {
		Message message=new Message();
		message.setTopic("Repository");
		message.setName("SynBioHub");
		message.setDescription("A repository for biological designs");
		message.setResourceUri("http://synbiohub.org");
		message.setPublisher("http://synbiohub.org");
		MessageSerializer serializer = new MessageSerializer();
		String json = serializer.messageToJson(message);
		System.out.println("------------------New Repository Message:------------------");
		System.out.println(json);
		System.out.println("------------------------------------------------------");				
	}
	
	
	public void testPublishRepositoryMessages_TestTopic() throws MessagingException {
		String topic="TestTopic";
		Message message=makeSynBioHubRepositoryMessage(topic);
		MessagingSystem messagingSystem = new MessagingSystem.Microbase(this.polenUri);
		messagingSystem.publish(message);
		
		Message ICEPublic=new Message();
		ICEPublic.setTopic(topic);
		ICEPublic.setName("JBEI - Public Registry");
		ICEPublic.setDescription("The Joint BioEnergy Institute - Public Registry");
		ICEPublic.setResourceUri("https://public-registry.jbei.org");
		ICEPublic.setPublisher("https://public-registry.jbei.org");
		ICEPublic.addProperty("type", "ICE");
		messagingSystem.publish(ICEPublic);
		
		Message ICE_ACS=new Message();
		ICE_ACS.setTopic(topic);
		ICE_ACS.setName("JBEI - ACS Synthetic Biology");
		ICE_ACS.setDescription("The Joint BioEnergy Institute - ACS Synthetic Biology Registry");
		ICE_ACS.setResourceUri("https://acs-registry.jbei.org");
		ICE_ACS.setPublisher("https://acs-registry.jbei.org");
		ICE_ACS.addProperty("type", "ICE");
		messagingSystem.publish(ICE_ACS);								
	}
	
	
}
