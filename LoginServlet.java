package com.mcafee.sso.core.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.jcr.Repository;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

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
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.State.Item;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

@Component(immediate=true,service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=SSO Login servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.resourceTypes=" + "weretail/components/structure/page",
        "sling.servlet.selectors=" + "pqr",
        ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/repodetails"
})
public class LoginServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

    private static final long serialVersionUID = -3960692666512058118L;

    @Reference

    private Repository repository;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override

    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        logger.info("inside servlet");

        response.setHeader("Content-Type", "application/json");

        // response.getWriter().print("{\"coming\" : \"soon\"}");

        String[] keys = repository.getDescriptorKeys();

        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < keys.length; i++) {

            try {

                jsonObject.addProperty(keys[i], repository.getDescriptor(keys[i]));

            }

            catch (JsonIOException e) {

                logger.error("JsonIOException " + e);
            } catch (JsonSyntaxException e) {

                logger.error("JsonSyntaxException " + e);
            } catch (JsonParseException e) {

                logger.error("JsonParseException " + e);
            }

        }

        String entity = callHttpCLient("test","test");

        response.getWriter().print(entity);

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


}
