package com.sit.abbra.abbraapi.util.rest.secure;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class SecureRestClientTrustManager implements X509TrustManager {


	private boolean isCheckServerExpire = true;
	private boolean isCheckClientExpire = false;

	/**
	 * {@inheritDoc}
	 */
	public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
		if(isCheckClientExpire) {
			for (int i = 0; i < chain.length; i++) {
				chain[i].checkValidity();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
		if(isCheckServerExpire) {
			for (int i = 0; i < chain.length; i++) {
				chain[i].checkValidity();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

}
