package br.com.eicombrasil.pedidoapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.eiconbrasil.pedidoapi.PedidoapiApplication;
import br.com.eiconbrasil.pedidoapi.model.dto.PedidoDto;

@SpringBootTest(classes = { PedidoapiApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PedidoApiIT {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void deveRetornarStatusCode200QuandoInserirLotePedido() {
		List<PedidoDto> pedidos = new ArrayList<>();
		for (long i = 1; i <= 10; i++) {
			PedidoDto dto = new PedidoDto();
			dto.setNumeroControle(i);
			dto.setCodigoCliente(i);
			dto.setNomeProduto("Produto "+ i);
			dto.setQuantidadeProduto(new BigDecimal(i));
			dto.setValorProduto(BigDecimal.TEN);
			
			pedidos.add(dto);
		}
		
		ResponseEntity<String> response = restTemplate.postForEntity("/pedidos", pedidos, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void deveRetornarStatusCode400QuandoInserirLotePedidoMaiorQue10() {
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
		
		ResponseEntity<String> response = restTemplate.postForEntity("/pedidos", pedidos, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

}
