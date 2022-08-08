package com.se.ntlmclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {

	private static final Logger LOGGER = LoggerFactory.getLogger(Request.class);

	public String executeRequest(RequestConfiguration requestConfiguration) {

		CredentialsProvider credentialsProvider = this.getNTLMCredentials(requestConfiguration);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
		HttpPost post = new HttpPost(requestConfiguration.getRequestURL());

		this.setPostBody(requestConfiguration, post);
		this.setHeaders(requestConfiguration, post);

		String response = this.getResponse(client, post);
		return response;

	}

	private CredentialsProvider getNTLMCredentials(RequestConfiguration requestConfiguration) {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		NTCredentials ntCredentials = new NTCredentials(requestConfiguration.getUser(),
				requestConfiguration.getPassword(), requestConfiguration.getHost(), requestConfiguration.getDomain());
		credentialsProvider.setCredentials(AuthScope.ANY, ntCredentials);
		return credentialsProvider;
	}

	private void setPostBody(RequestConfiguration requestConfiguration, HttpPost post) {
		try {
			StringEntity input = this.getStringEntity(requestConfiguration);
			input.setContentType(requestConfiguration.getInputContentType());
			post.setEntity(input);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Erro ao codificar a msg com o charset", e);
		}
	}
	
	private StringEntity getStringEntity(RequestConfiguration requestConfiguration) throws UnsupportedEncodingException{
		if(requestConfiguration.getBodyEncodingCharset()!=null && !requestConfiguration.getBodyEncodingCharset().isEmpty()){
			return new StringEntity(requestConfiguration.getBodyAsString(), requestConfiguration.getBodyEncodingCharset());
		}else{
			return new StringEntity(requestConfiguration.getBodyAsString());
		}
	}

	private HttpPost setHeaders(RequestConfiguration requestConfiguration, HttpPost post) {
		Map<String, String> headers = requestConfiguration.getHeaders();
		for (Entry<String, String> header : headers.entrySet()) {
			post.setHeader(header.getKey(), header.getValue());
		}
		return post;
	}

	private String getResponse(HttpClient client, HttpPost post) {
		String responseAsString = "";
		try {
			HttpResponse response = client.execute(post);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				responseAsString = EntityUtils.toString(responseEntity);
			}
		} catch (ClientProtocolException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return responseAsString;

	}

}
