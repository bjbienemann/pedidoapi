package br.com.eicombrasil.pedidoapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import br.com.eiconbrasil.pedidoapi.PedidoapiApplication;
import br.com.eiconbrasil.pedidoapi.model.entity.PedidoEntity;
import br.com.eiconbrasil.pedidoapi.model.filter.PedidoFilter;
import br.com.eiconbrasil.pedidoapi.model.filter.builder.PedidoFilterBuilder;
import br.com.eiconbrasil.pedidoapi.repository.PedidoRepository;

@DataJpaTest
@ContextConfiguration(classes = { PedidoapiApplication.class })
public class PedidoRepositoryIT {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Test
	public void inserirUmPedido() {
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroControle(1L);
		entity.setDataCadastro(LocalDate.now());
		entity.setNomeProduto("Produto 1");
		entity.setQuantidadeProduto(BigDecimal.ONE);
		entity.setValorProduto(BigDecimal.TEN);
		entity.setValorTotal(BigDecimal.TEN);
		entity.setCodigoCliente(1L);

		pedidoRepository.save(entity);

		assertThat(entity.getNumeroControle()).isNotNull();
	}

	@Test
	public void atualizarUmPedido() {
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroControle(1L);
		entity.setDataCadastro(LocalDate.now());
		entity.setNomeProduto("Novo Produto");
		entity.setQuantidadeProduto(BigDecimal.ONE);
		entity.setValorProduto(BigDecimal.TEN);
		entity.setValorTotal(BigDecimal.TEN);
		entity.setCodigoCliente(1L);

		pedidoRepository.save(entity);

		entity.setNomeProduto("Produdo Atualizado");
		entity.setQuantidadeProduto(new BigDecimal(2));
		entity.setValorProduto(new BigDecimal(2.4));
		entity.setValorTotal(new BigDecimal(4.8));
		entity.setCodigoCliente(1L);

		entity = pedidoRepository.save(entity);
		
		assertThat(entity.getNomeProduto()).isEqualTo("Produdo Atualizado");
		assertThat(entity.getQuantidadeProduto()).isEqualTo(new BigDecimal(2));
		assertThat(entity.getValorProduto()).isEqualTo(new BigDecimal(2.4));
	}

	@Test
	public void deletarUmPedido() {
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroControle(1L);
		entity.setDataCadastro(LocalDate.now());
		entity.setNomeProduto("Produto 1");
		entity.setQuantidadeProduto(BigDecimal.ONE);
		entity.setValorProduto(BigDecimal.TEN);
		entity.setValorTotal(BigDecimal.TEN);
		entity.setCodigoCliente(1L);
		pedidoRepository.save(entity);

		pedidoRepository.delete(entity);

		Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(entity.getNumeroControle());
		assertThat(pedidoOptional).isNotPresent();
	}
	
	@Test
	public void filtrarPedidoPorNumeroControle() {
		Long numeroControle = 1L;
		
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroControle(numeroControle);
		entity.setDataCadastro(LocalDate.now());
		entity.setNomeProduto("Produto 1");
		entity.setQuantidadeProduto(BigDecimal.ONE);
		entity.setValorProduto(BigDecimal.TEN);
		entity.setValorTotal(BigDecimal.TEN);
		entity.setCodigoCliente(1L);
		pedidoRepository.save(entity);
				
		PedidoFilter filter = new PedidoFilterBuilder()
				.porNumeroControle(numeroControle)
				.build();
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<PedidoEntity> pedidoPage = pedidoRepository.find(filter, pageable);
		assertThat(pedidoPage)
			.isNotEmpty()
			.allMatch(e ->  e.getNumeroControle().equals(numeroControle));
	}
	
	@Test
	public void filtrarPedidosPorDataCadastro() {
		LocalDate dataCadastro = LocalDate.of(2020, 10, 31);
		
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroControle(1L);
		entity.setDataCadastro(dataCadastro);
		entity.setNomeProduto("Produto 1");
		entity.setQuantidadeProduto(BigDecimal.ONE);
		entity.setValorProduto(BigDecimal.TEN);
		entity.setValorTotal(BigDecimal.TEN);
		entity.setCodigoCliente(1L);
		pedidoRepository.save(entity);
		
		PedidoFilter filter = new PedidoFilterBuilder()
				.porDataCadastro(dataCadastro)
				.build();
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<PedidoEntity> pedidoPage = pedidoRepository.find(filter, pageable);
		assertThat(pedidoPage)
			.isNotEmpty()
			.allMatch(e -> e.getDataCadastro().equals(dataCadastro));
	}
	
	@Test
	public void filtrarPedidosPorDataCadastroVazio() {
		LocalDate dataCadastro = LocalDate.of(2020, 10, 31);
		
		PedidoFilter filter = new PedidoFilterBuilder()
				.porDataCadastro(dataCadastro)
				.build();
		
		Page<PedidoEntity> pedidoPage = pedidoRepository.find(filter, PageRequest.of(0, 10));
		assertThat(pedidoPage).isEmpty();
	}
	
	@Test
	public void filtrarPedidosPorCodigoCliente() {
		Long codigoCliente = 1L;
		
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroControle(1L);
		entity.setDataCadastro(LocalDate.now());
		entity.setNomeProduto("Produto 1");
		entity.setQuantidadeProduto(BigDecimal.ONE);
		entity.setValorProduto(BigDecimal.TEN);
		entity.setValorTotal(BigDecimal.TEN);
		entity.setCodigoCliente(codigoCliente);
		pedidoRepository.save(entity);
		
		PedidoFilter filter = new PedidoFilterBuilder()
				.porCodigoCliente(codigoCliente)
				.build();
		
		Page<PedidoEntity> pedidoPage = pedidoRepository.find(filter, PageRequest.of(0, 10));
		assertThat(pedidoPage)
			.isNotEmpty()
			.allMatch(e -> e.getCodigoCliente().equals(codigoCliente));
	}
	
	@Test
	public void filtrarPedidosPorCodigoClienteVazio() {
		Long codigoCliente = 1L;
		
		PedidoFilter filter = new PedidoFilterBuilder()
				.porCodigoCliente(codigoCliente)
				.build();
		
		Page<PedidoEntity> pedidoPage = pedidoRepository.find(filter, PageRequest.of(0, 10));
		assertThat(pedidoPage).isEmpty();
	}

}
