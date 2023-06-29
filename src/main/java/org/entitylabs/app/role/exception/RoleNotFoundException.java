package org.entitylabs.app.role.exception;

public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(String cause) {

		super(cause);
	}
}