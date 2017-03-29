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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
		String output=null;		
		CloseableHttpResponse response=null;
		try {			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(URI);
			response = httpclient.execute(httpGet);			
		    HttpEntity entity = response.getEntity();
		    output=convertStreamToString(entity.getContent());
		} 
		catch (IOException exception) {
			throw new IOException("Could not read the stream from " + URI,
					exception);
		}
		finally {
			if (response!=null)
			{			
				response.close();
			}		    
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
		CloseableHttpResponse response=null;
		
		String output=null;
		try
		{
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Entry<String, String> parameter : parameters.entrySet()) {
				String parameterName = parameter.getKey();
				String parameterValue = parameter.getValue();
				nvps.add(new BasicNameValuePair(parameterName, parameterValue));				
			}
			httpPost.setEntity(new  UrlEncodedFormEntity(nvps));
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			//HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
			response = httpClient.execute(httpPost);
			
			int status=response.getStatusLine().getStatusCode();
			if (status!=200)
			{
				Header locationHeader = response.getFirstHeader("location");
		        if (locationHeader != null) {
		            String redirectLocation = locationHeader.getValue();
		            return post(redirectLocation,parameters);
		        } 
			}
		    //System.out.println(response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    output=convertStreamToString(entity.getContent());		
		} 
		catch (IOException exception) {
			throw new IOException("Could not post to " + uri,
					exception);
		}
		finally {
		    response.close();
		}
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