package uk.ac.ncl.flowers.polen;

import java.util.HashMap;
import java.util.Map;

/**
 * A message describing an event for some POLEN resource.
 * <p>
 * Messages include properties for the name, description and publisher of a resource which is specified with the resourceUri property.
 * Messages are published using the topic property to indicate the type of a resource. Examples include "Part","Datasheet" and "Model".
 * Additional properties specific for a domain can be added using the properties Map.
 * </p>
 * @author Goksel Misirli
 * @author Matthew Pocock
 */
public final class Message {

	private String name;
	private String description;
	private String topic;
	private String resourceUri;
	private String publisher;
	private Map<String, String> properties = new HashMap<>();
	
	/**
	 * Creates an empty Message entity 
	 */
	public Message() {
	}	

	/**
	 * Creates a Message entity with the given properties
	 * @param name			name of the message
	 * @param description	description of the message
	 * @param topic			topic of the message. E.g.: "Part", "Datasheet", "Model"
	 * @param resourceUri	uri indicating where to get the resource from. E.g.: A uri pointing to a datasheet or to an SBML model.
	 * @param publisher		publisher information for the message
	 * @param properties	map that can be used to store additional information for the message 
	 */
	public Message(String name, String description, String topic,
			String resourceUri, String publisher, Map<String, String> properties) {
		super();
		this.name = name;
		this.description = description;
		this.topic = topic;
		this.resourceUri = resourceUri;
		this.publisher = publisher;
		this.properties = properties;
	}


	/**
	 * Gets the name of the message
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the message
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description of the message
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the message
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the topic of the message
	 * @return
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Sets the topic of the message
	 * @param topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Gets the uri for the resource
	 * @return
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * Sets the uri for the resource
	 * @param resourceUri
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	/**
	 * Gets the publisher
	 * @return
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Sets the publisher
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * Gets the properties Map
	 * @return A map of name-value pairs which are both {@link String}
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties Map
	 * @param properties A map of name-value pairs which are both {@link String}
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	/**
	 * Adds a given name-value property to the message
	 * @param propertyName 	name of the property
	 * @param propertyValue	value of the property
	 */
	public void addProperty(String propertyName, String propertyValue) {
		this.properties.put(propertyName, propertyValue);
	}


	/**
	 * Gets the {@link String} representation of the message
	 */
	@Override
	public String toString() {
		return "Message [name=" + name + ", description=" + description
				+ ", topic=" + topic + ", resourceUri=" + resourceUri
				+ ", publisher=" + publisher + ", properties=" + properties
				+ "]";
	}	
}
