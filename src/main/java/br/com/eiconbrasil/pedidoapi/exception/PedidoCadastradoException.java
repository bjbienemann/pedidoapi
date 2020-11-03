package br.com.eiconbrasil.pedidoapi.exception;

public class PedidoCadastradoException extends MessageException {

	private static final long serialVersionUID = 1L;
	
	public PedidoCadastradoException(Long numeroControle) {
		super("mensagem.pedido-cadastrado", numeroControle.toString());
	}

}
