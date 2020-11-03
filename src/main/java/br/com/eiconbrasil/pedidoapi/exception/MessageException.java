package br.com.eiconbrasil.pedidoapi.exception;

public class MessageException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String key;
	private String[] args;

	public MessageException(String key, String... args) {
		this.key = key;
		this.args = args;
	}

	public MessageException(Throwable throwable, String key, String... args) {
		super(throwable);

		this.key = key;
		this.args = args;
	}

	public String getKey() {
		return key;
	}

	public String[] getArgs() {
		return args;
	}
	
}
