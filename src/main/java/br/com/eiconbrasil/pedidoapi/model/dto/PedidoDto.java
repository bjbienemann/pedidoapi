package br.com.eiconbrasil.pedidoapi.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.sun.istack.NotNull;

/**
 * PedidoDto
 */
@JacksonXmlRootElement(localName = "pedido")
public class PedidoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@JacksonXmlProperty(isAttribute = true)
	private Long numeroControle;

	@JacksonXmlProperty
	private LocalDate dataCadastro;

	@NotBlank
	@JacksonXmlProperty
	private String nomeProduto;

	@JacksonXmlProperty
	private BigDecimal quantidadeProduto;

	@NotNull
	@JacksonXmlProperty
	private BigDecimal valorProduto;

	@JacksonXmlProperty
	private BigDecimal valorTotal;

	@NotNull
	@JacksonXmlProperty
	private Long codigoCliente;

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

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(BigDecimal quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public BigDecimal getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

}