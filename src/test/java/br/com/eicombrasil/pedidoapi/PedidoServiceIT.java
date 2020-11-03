package br.com.eicombrasil.pedidoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.com.eiconbrasil.pedidoapi.PedidoapiApplication;
import br.com.eiconbrasil.pedidoapi.exception.LimiteMaximoPedidoException;
import br.com.eiconbrasil.pedidoapi.exception.PedidoCadastradoException;
import br.com.eiconbrasil.pedidoapi.model.dto.PedidoDto;
import br.com.eiconbrasil.pedidoapi.service.PedidoService;

@SpringBootTest(classes = { PedidoapiApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PedidoServiceIT {

	@Autowired
	private PedidoService pedidoService;
	
	@Test
	public void salvarComQuantidadeMaiorQueCinco() {
		PedidoDto dto = new PedidoDto();
		dto.setNumeroControle(50L);
		dto.setCodigoCliente(1L);
		dto.setNomeProduto("Produto 5%");
		dto.setQuantidadeProduto(new BigDecimal(7));
		dto.setValorProduto(new BigDecimal(50L));
		
		PedidoDto pedidoSalvo = pedidoService.salvar(dto);
		
		assertEquals(new BigDecimal(332.50).setScale(2, RoundingMode.UP), pedidoSalvo.getValorTotal());
	}
	
	@Test
	public void salvarComQuantidadeMaiorQueDez() {
		PedidoDto dto = new PedidoDto();
		dto.setNumeroControle(100L);
		dto.setCodigoCliente(1L);
		dto.setNomeProduto("Produto 10%");
		dto.setQuantidadeProduto(new BigDecimal(14));
		dto.setValorProduto(new BigDecimal(100));
		
		PedidoDto pedidoSalvo = pedidoService.salvar(dto);
		
		assertEquals(new BigDecimal(1260).setScale(2, RoundingMode.UP), pedidoSalvo.getValorTotal());
	}
	
	@Test
	public void inserirLoteComMaisDe10Registros() {
		List<PedidoDto> pedidos = new ArrayList<>();
		for (long i = 1; i <= 12; i++) {
			PedidoDto dto = new PedidoDto();
			dto.setNumeroControle(i);
			dto.setCodigoCliente(i);
			dto.setNomeProduto("Produto "+ i);
			dto.setQuantidadeProduto(new BigDecimal(i));
			dto.setValorProduto(BigDecimal.TEN);
			
			pedidos.add(dto);
		}
		
		assertThrows(LimiteMaximoPedidoException.class, () -> pedidoService.salvar(pedidos));
	}
	
	@Test
	public void inserirLoteComNumeroControleRepetido() {
		List<PedidoDto> pedidos = new ArrayList<>();
		for (long i = 1; i <= 2; i++) {
			PedidoDto dto = new PedidoDto();
			dto.setNumeroControle(1L);
			dto.setCodigoCliente(i);
			dto.setNomeProduto("Produto "+ i);
			dto.setQuantidadeProduto(new BigDecimal(i));
			dto.setValorProduto(BigDecimal.TEN);
			
			pedidos.add(dto);
		}
		
		assertThrows(PedidoCadastradoException.class, () -> pedidoService.salvar(pedidos));
	}
}
