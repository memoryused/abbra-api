package com.sit.abbra.abbraapi.util.rest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.sit.abbra.abbraapi.util.rest.secure.SecureRestClientTrustManager;

import sit.util.http.domain.HTTPConfig;
import sit.util.http.secure.SecureHostnameVerifier;

public class RestClientUtil {

	private static final String SECURE_PROTOCOL = "TLSv1.2";
	
	private static final String SOCKS_HOST_PROPERTY = "socksProxyHost";
	private static final String SOCKS_PORT_PROPERTY = "socksProxyPort";
	private static final String SOCKS_VERSION_PROPERTY = "socksProxyVersion";
	private static final String SOCKS_NON_HOST_PROPERTY = "socksNonProxyHosts";
	
	private RestClientUtil() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static Object callServicePost(String url, String path, boolean isSecure, Integer timeout
			, String strJsonReq, Class<?> clazz, Map<String, String> headerMap, int retry) throws KeyManagementException, NoSuchAlgorithmException {
		Client client = null;
		Object response = null;
		try {
			
			client = getClient(isSecure);

			// set timeout
			client.property(ClientProperties.CONNECT_TIMEOUT, timeout);
			client.property(ClientProperties.READ_TIMEOUT, timeout);

			Builder builder = client.target(url)
				.path(path)
				.request(MediaType.APPLICATION_JSON);
			
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					builder.header(entry.getKey(), entry.getValue());
				}
			}
			
			for(int i=0; i<retry; i++) {
				try {
					response = builder.post(Entity.entity( strJsonReq , MediaType.APPLICATION_JSON) , clazz);
					break; //สำเร็จ
				} catch (Exception e) {
					if(i == retry-1) {
						throw e;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						Thread.currentThread().interrupt();
					}
				}
			}
			
		} finally {
			closeClient(client);
		}

		return response;
	}
	
	private static Client getClient(boolean isSecure) throws NoSuchAlgorithmException, KeyManagementException {
		Client client = null;
		
		if (isSecure) {
			SSLContext sc = SSLContext.getInstance(SECURE_PROTOCOL);
			
			TrustManager[] trustAllCerts = { new SecureRestClientTrustManager() };    
			sc.init(null, trustAllCerts, null);

			// Install the all-trusting trust manager
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(new SecureHostnameVerifier(new HTTPConfig()));
			
			client = ClientBuilder.newBuilder().withConfig(getClientConfig()).sslContext(sc).build();
		} else {
			client = ClientBuilder.newBuilder().withConfig(getClientConfig()).build();
		}

		return client;
	}
	
	private static ClientConfig getClientConfig() {
		ClientConfig config = new ClientConfig();
		config.register(JacksonFeature.class);
		return config;
	}

	private static void closeClient(Client client) {
		if (client != null) {
			client.close();
		}
	}
	
	public static void setSocksProxy(String sockHost, int sockPort, int sockVersion, String noneProxyHost) {
		System.setProperty(SOCKS_HOST_PROPERTY, sockHost);
		System.setProperty(SOCKS_PORT_PROPERTY, String.valueOf(sockPort));

		if (sockVersion > 0) {
			System.setProperty(SOCKS_VERSION_PROPERTY, Integer.toString(sockVersion));
		}
		
		System.setProperty(SOCKS_NON_HOST_PROPERTY, noneProxyHost);
	}

}
