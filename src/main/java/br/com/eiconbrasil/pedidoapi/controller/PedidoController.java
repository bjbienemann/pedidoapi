package br.com.eiconbrasil.pedidoapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eiconbrasil.pedidoapi.model.dto.PedidoDto;
import br.com.eiconbrasil.pedidoapi.model.filter.PedidoFilter;
import br.com.eiconbrasil.pedidoapi.service.PedidoService;

/**
 * PedidoController
 */
@RestController
@RequestMapping(value = "pedidos", 
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping()
	public Page<PedidoDto> buscar(PedidoFilter filter, Pageable pageable) {
		return pedidoService.buscar(filter, pageable);
	}

	@PostMapping()
	public ResponseEntity<PedidoDto> inserirPedidos(@RequestBody List<PedidoDto> pedidos) {
		pedidoService.salvar(pedidos);

		return ResponseEntity.ok().build();
	}
}