package br.com.eiconbrasil.pedidoapi.exception;

public class LimiteMaximoPedidoException extends MessageException {

	private static final long serialVersionUID = 1L;
	
	public LimiteMaximoPedidoException(Integer limiteMaximoPedido) {
		super("mensagem.limite-maximo-predido", limiteMaximoPedido.toString());
	}

}
