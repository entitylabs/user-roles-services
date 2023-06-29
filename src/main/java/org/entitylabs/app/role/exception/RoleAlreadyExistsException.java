package org.entitylabs.app.role.exception;

public class RoleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleAlreadyExistsException(String cause) {

		super(cause);
	}
}
