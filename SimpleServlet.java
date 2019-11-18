package com.foo.htl.core.servlets;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
////
//import org.apache.commons.lang3.CharEncoding;
//import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.simple.JSONObject;

import com.adobe.granite.ui.components.State.Item;

@SlingServlet(paths = "/bin/htlSearchServlet", methods = "POST", metatype = true)
public class SimpleServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {
	private static final long serialVersionUID = 2598426539166789515L;

	@Reference
	private SlingRepository repository;

	public void bindRepository(SlingRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServerException, IOException {

		try {
			// Get the submitted form data that is sent from the
			// CQ web page a
			String id = UUID.randomUUID().toString();
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			// Encode the submitted form data to JSON
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("username", username);
			obj.put("password", "*****");

			String entity = callHttpCLient(username, password);
			obj.put("google response", entity);

			// Get the JSON formatted data
			String jsonData = obj.toJSONString();

			// Return the JSON formatted data
			response.getWriter().write(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String callHttpCLient(String username, String password) throws ClientProtocolException, IOException {
		
		final CredentialsProvider credentialProvider = new BasicCredentialsProvider();
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		credentialProvider.setCredentials(AuthScope.ANY, credentials);
		String host= "http://dummy.restapiexample.com/api/v1/employee/1";

		final HttpHost targetHost = HttpHost.create(host);

		final AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credentialProvider);
		context.setAuthCache(authCache);

		final HttpClient client = HttpClientBuilder.create()
		                                           .build();
		final HttpResponse httpResponse = client.execute(new HttpGet(host), context);
		final int statusCode = httpResponse.getStatusLine()
		                                   .getStatusCode();
		String entity=null;

		List<Item> listItems = Collections.emptyList();
		if (statusCode == HttpStatus.SC_OK) {
			 entity = EntityUtils.toString(httpResponse.getEntity());
		    //final InputStream content = httpResponse.getEntity()
		    //                                        .getContent();
		    //final Item[] items = new ObjectMapper().readValue(content, Item[].class);
		    //listItems = Arrays.asList(items);
		}
		
        return entity;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		System.out.println("Started--------------");
		final CredentialsProvider credentialProvider = new BasicCredentialsProvider();
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("test", "test");
		credentialProvider.setCredentials(AuthScope.ANY, credentials);
		String host= "http://dummy.restapiexample.com/api/v1/employee/1";

		final HttpHost targetHost = HttpHost.create(host);

		final AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credentialProvider);
		context.setAuthCache(authCache);

		final HttpClient client = HttpClientBuilder.create()
		                                           .build();
		//final HttpResponse httpResponse = client.execute(new HttpGet(host + "/list-items"), context);
		final HttpResponse httpResponse = client.execute(new HttpGet(host), context);
		final int statusCode = httpResponse.getStatusLine()
		                                   .getStatusCode();
		System.out.println("Response status code ==  "+statusCode);

		List<Item> listItems = Collections.emptyList();
		
		if (statusCode == HttpStatus.SC_OK) {
			String entity = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("Entity----"+entity);
			
			System.out.println("_______done_______");
		    //final InputStream content = httpResponse.getEntity()
		    //                                        .getContent();
		    //final Item[] items = new ObjectMapper().readValue(content, Item[].class);
		    //listItems = Arrays.asList(items);
		}
		
		
	}

}