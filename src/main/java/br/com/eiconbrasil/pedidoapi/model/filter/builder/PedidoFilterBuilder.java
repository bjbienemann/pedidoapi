package br.com.eiconbrasil.pedidoapi.model.filter.builder;

import java.time.LocalDate;

import br.com.eiconbrasil.pedidoapi.model.filter.PedidoFilter;

public class PedidoFilterBuilder {
	
	private PedidoFilter instance;

	public PedidoFilterBuilder() {
		this.instance = new PedidoFilter();
	}
	
	public PedidoFilterBuilder porNumeroControle(Long numeroControle) {
		instance.setNumeroControle(numeroControle);
		
		return this;
	}
	
	public PedidoFilterBuilder porDataCadastro(int ano, int mes, int dia) {
		instance.setDataCadastro(LocalDate.of(ano, mes, dia));
		
		return this;
	}
	
	public PedidoFilterBuilder porDataCadastro(LocalDate dataCadastro) {
		instance.setDataCadastro(dataCadastro);
		
		return this;
	}
	
	public PedidoFilterBuilder porCodigoCliente(Long codigoCliente) {
		instance.setCliente(codigoCliente);
		
		return this;
	}
	
	public PedidoFilter build() {
		return instance;
	}
}
