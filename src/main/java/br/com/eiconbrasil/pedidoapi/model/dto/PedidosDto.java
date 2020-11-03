package br.com.eiconbrasil.pedidoapi.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "pedidos")
public class PedidosDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JacksonXmlProperty(localName = "pedido")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<PedidoDto> pedidos = new ArrayList<>();

	public List<PedidoDto> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoDto> pedidos) {
		this.pedidos = pedidos;
	}
	
}
