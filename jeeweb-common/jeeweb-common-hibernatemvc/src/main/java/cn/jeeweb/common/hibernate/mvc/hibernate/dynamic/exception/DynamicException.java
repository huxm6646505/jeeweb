package cn.jeeweb.common.hibernate.mvc.hibernate.dynamic.exception;

@SuppressWarnings("serial")
public class DynamicException extends RuntimeException {

	public DynamicException() {
		super();
	}

	public DynamicException(String msg) {
		super(msg);
	}

	public DynamicException(Exception exception) {
		 super(exception);
	}
}
