package br.jus.jfce.projects.service;

import org.springframework.ldap.core.AuthenticationErrorCallback;

public class CollectingAuthenticationErrorCallback implements AuthenticationErrorCallback {
	private Exception error;

	public void execute(Exception e) {
		this.error = e;
	}

	public Exception getError() {
		return error;
	}
}