package uk.ac.ncl.flowers.polen.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import uk.ac.ncl.flowers.polen.Message;

/**
 * Utility class to serialise {@code Message} objects as JSON data.
 * 
 * <p>
 * Using this class an instance of a {@code Message} or a list of messages ({@code List<Message>}) can be serialised as JSON data.
 * </p>
 * @author Goksel Misirli
 *
 */
public final class MessageSerializer {
	
	/**
	 * Converts a given {@code message} to JSON format.
	 * @param message
	 * @return {@link String} containing the JSON representation of the {@code message}
	 */
	public String messageToJson(Message message)
	{
		JSONObject jsonObject=messageToJsonObject(message);
		String json= jsonObject.toJSONString();
		return json;	
	}

	private JSONObject messageToJsonObject(Message message)
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name", message.getName());
		jsonObject.put("description", message.getDescription());
		jsonObject.put("uri", message.getResourceUri());
		jsonObject.put("publisher", message.getPublisher());
		jsonObject.put("topic", message.getTopic());
		jsonObject.put("properties", message.getProperties());				
		return jsonObject;
	}
	
	/**
	 * Converts given {@code messages} to JSON format.
	 * @param messages
	 * @return {@link String} containing the JSON representation of the {@code messages}.
	 */
	public String messageToJson(List<Message> messages)
	{
		JSONObject jsonObject=new JSONObject();
		List  list = new LinkedList<>();
		for (Message message:messages)
		{
			JSONObject messageJson=messageToJsonObject(message);
			list.add(messageJson);
		}					
		String json= JSONValue.toJSONString(list);
		
		return json;	
	}
	
	/**
	 * Converts a given message into JSON format. However this method writes the properties also at the top level along with other properties name, description and uri.
	 * The topic and the publisher is excluded.
	 * @param message
	 * @return @link String} containing the JSON representation of the {@code messages}.
	 * @throws IOException
	 */
	public String messagePropertiesToJson(Message message) throws IOException
	{
		 StringWriter out = new StringWriter();
		 Map<String,String> properties=new HashMap<>();
		 properties.putAll(message.getProperties());
		 properties.put("name",message.getName());
		 properties.put("description",message.getDescription());
		 properties.put("uri",message.getResourceUri());
		 
		 JSONValue.writeJSONString(properties, out);
		 String json = out.toString();
		 return json;	
	}
	
}

