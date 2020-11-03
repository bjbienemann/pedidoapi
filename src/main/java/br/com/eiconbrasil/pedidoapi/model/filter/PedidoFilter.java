package br.com.eiconbrasil.pedidoapi.model.filter;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class PedidoFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long numeroControle;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataCadastro;
	
	private Long cliente;

	public Long getNumeroControle() {
		return numeroControle;
	}

	public void setNumeroControle(Long numeroControle) {
		this.numeroControle = numeroControle;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}
}
