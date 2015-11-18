package uk.ac.ncl.flowers.polen.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides methods to get data from and post data to web resources given with
 * HTTP URIs.
 * 
 * <p>
 * The {@link #get(String)} and {@link #post(String, HashMap)} methods are used respectively to perform
 * HTTP GET and POST operations.
 * </p>
 * 
 * @author Goksel Misirli
 */
public final class HttpHandler {

	/**
	 * Gets the web content from a given URI.
	 * 
	 * @param URI	URI to access to the repository.
	 * @return 		{@link String} representation of the web content in UTF-8 format.
	 * @throws 		IOException if the web resource with the given URI cannot be accessed or its cannot be read.
	 */
	public static String get(String URI) throws IOException {
		// URI=URLEncoder.encode(URI, "UTF-8");
		URI = URI.replace(" ", "%20");
		URI = URI.replace(">", "%3E");
		URL url = new URL(URI);
		int tried = 0;
		InputStream stream = null;
		while (tried < 5) {
			try {
				stream = url.openStream();
				tried = 5;
			}
			catch (IOException exception) {
				tried++;
				System.out.print("Could not access to the uri after attempt "
						+ tried + ". ");
				try {
					Thread.sleep(3000);// Sleep for 3 seconds
				}
				catch (Exception threadexception) {

				}
				if (tried == 5) {
					tried = 0;
					throw exception;
				}
				else {
					System.out.println("Connection will be tried in 3 seconds");
				}
			}
		}
		String output = "";
		try {
			output = convertStreamToString(stream);
		}
		catch (IOException exception) {
			throw new IOException("Could not read the stream from " + URI,
					exception);
		}
		return output;
	}	

	/**
	 * Performs an HTTP POST action using the given URI and parameters.
	 * @param uri			web resource address. E.g. A URI for a REST-based web service interface.
	 * @param parameters	parameters to post to. Keys and values from the map are used to create HTTP parameter names and values respectively.
	 * @return 				the response from the web resource given with the uri.
	 * @throws IOException	If the POST operation fails.
	 */
	public static String post(String uri,
			HashMap<String, String> parameters) throws IOException {

		URL url;
		URLConnection urlConn;
		DataOutputStream printout;
		DataInputStream input;
		// URL of CGI-Bin script.
		url = new URL(uri);
		// URL connection channel.
		urlConn = url.openConnection();
		// Let the run-time system (RTS) know that we want input.
		urlConn.setDoInput(true);
		// Let the RTS know that we want to do output.
		urlConn.setDoOutput(true);
		// No caching, we want the real thing.
		urlConn.setUseCaches(false);
		// Specify the content type.
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// Send POST output.
		printout = new DataOutputStream(urlConn.getOutputStream());

		String content = "";
		for (Entry<String, String> parameter : parameters.entrySet()) {
			String parameterName = parameter.getKey();
			String parameterValue = parameter.getValue();
			if (content.length() > 0) {
				content = content + "&";
			}
			content = content + parameterName + "="
					+ URLEncoder.encode(parameterValue);
		}
		Logger.getLogger(HttpHandler.class.getName()).log(Level.FINEST, "Data being posted:" + content);
		printout.writeBytes(content);
		printout.flush();
		printout.close();
		// Get  the response data.
		input = new DataInputStream(urlConn.getInputStream());
		String str;
		String output = "";
		while (null != ((str = input.readLine()))) {
			output = output + str;
		}
		input.close();
		return output;
	}

	private static String convertStreamToString(InputStream stream)
			throws IOException {
		if (stream != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(
						stream, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			}
			finally {
				stream.close();
			}
			return writer.toString();

		}
		else {
			return "";
		}
	}

}