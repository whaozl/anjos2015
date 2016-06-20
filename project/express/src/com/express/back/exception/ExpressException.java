package com.express.back.exception;

public class ExpressException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExpressException(){
		super();
	}
	
	public ExpressException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ExpressException(String message) {
		super(message);
	}

	public ExpressException(Throwable cause) {
		super(cause);
	}
	
	
}
