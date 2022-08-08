package com.se.ntlmclient;

import java.util.HashMap;
import java.util.Map;

public class RequestConfiguration {

	private String host;
	private String user;
	private String password;
	private String domain;
	private String bodyAsString;
	private String requestURL;
	private String inputContentType = "text/xml; charset=utf-8";
	private String bodyEncodingCharset;
	private Map<String, String> headers = new HashMap<String, String>();

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBodyAsString() {
		return bodyAsString;
	}

	public void setBodyAsString(String bodyAsString) {
		this.bodyAsString = bodyAsString;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getInputContentType() {
		return inputContentType;
	}

	public void setInputContentType(String inputContentType) {
		this.inputContentType = inputContentType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String header, String value){
		this.headers.put(header, value);
	}

	public String getBodyEncodingCharset() {
		return bodyEncodingCharset;
	}

	public void setBodyEncodingCharset(String bodyEncodingCharset) {
		this.bodyEncodingCharset = bodyEncodingCharset;
	}

}
