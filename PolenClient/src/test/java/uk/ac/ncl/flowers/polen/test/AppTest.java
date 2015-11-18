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

	private Message makeTestMessage() {
		Message message = new Message();

		message.setTopic("Part");
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
		String serviceUri = "http://synbio.ncl.ac.uk:8083/notification";
		MessagingSystem messagingSystem = new MessagingSystem.Microbase(
				serviceUri);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Date date=null;
		try
		{
			date=sdf.parse("2014-02-25");		
		}
		catch(Exception ex){}
		
		List<Message> messages = messagingSystem.poll("Model", date, 1);

		System.out.println(messages);
	}
	
	
	
}
