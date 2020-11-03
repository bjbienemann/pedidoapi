package br.com.eiconbrasil.pedidoapi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eiconbrasil.pedidoapi.exception.LimiteMaximoPedidoException;
import br.com.eiconbrasil.pedidoapi.exception.PedidoCadastradoException;
import br.com.eiconbrasil.pedidoapi.model.dto.PedidoDto;
import br.com.eiconbrasil.pedidoapi.model.dto.PedidosDto;
import br.com.eiconbrasil.pedidoapi.model.entity.PedidoEntity;
import br.com.eiconbrasil.pedidoapi.model.filter.PedidoFilter;
import br.com.eiconbrasil.pedidoapi.model.mapper.PedidoMapper;
import br.com.eiconbrasil.pedidoapi.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoMapper pedidoMapper;
	
	private static final int CASAS_DECIMAIS_VALOR = 2;

	public Page<PedidoDto> buscar(PedidoFilter filter, Pageable pageable) {
		Page<PedidoEntity> entities = pedidoRepository.find(filter, pageable);
		
		return entities.map(pedidoMapper::toDto);
	}
	
	public PedidosDto buscar(PedidoFilter filter) {
		PedidosDto pedidosDto = new PedidosDto();
		List<PedidoEntity> entities = (List<PedidoEntity>) pedidoRepository.findAll();
		
		entities.stream().map(pedidoMapper::toDto)
			.forEach(dto -> pedidosDto.getPedidos().add(dto));
		
		return pedidosDto;
	}
	
	@Transactional
	public List<PedidoDto> salvar(List<PedidoDto> pedidos) {
		Integer limiteMaximoPedido = 10;
		if (pedidos.size() > limiteMaximoPedido) {
			throw new LimiteMaximoPedidoException(limiteMaximoPedido);
		}
		
		return pedidos.stream().map(this::salvar).collect(Collectors.toList());
	}
	
	public PedidoDto salvar(PedidoDto dto) {
		Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(dto.getNumeroControle());
		
		if (pedidoOptional.isPresent()) {
			throw new PedidoCadastradoException(dto.getNumeroControle());
		}
		
		if (Objects.isNull(dto.getDataCadastro())) {
			dto.setDataCadastro(LocalDate.now());
		}
		
		if (Objects.isNull(dto.getQuantidadeProduto())) {
			dto.setQuantidadeProduto(BigDecimal.ONE);
		}
		
		dto.setValorTotal(calcularTotalPedido(dto));
		
		PedidoEntity entity = pedidoMapper.toEntity(dto);
		entity = pedidoRepository.save(entity);
		
		return pedidoMapper.toDto(entity);
	}
	
	private BigDecimal calcularTotalPedido(PedidoDto pedido) {
		BigDecimal valorTotal = pedido.getQuantidadeProduto()
				.multiply(pedido.getValorProduto());
		
		if (pedido.getQuantidadeProduto().compareTo(new BigDecimal(10)) > 0) {
			BigDecimal dezPorcento = new BigDecimal(0.10);
			return calcularValorDesconto(valorTotal, dezPorcento);
		}
		
		if (pedido.getQuantidadeProduto().compareTo(new BigDecimal(5)) > 0) {
			BigDecimal cincoPorcento = new BigDecimal(0.05);
			return calcularValorDesconto(valorTotal, cincoPorcento);
		}
		
		return valorTotal.setScale(CASAS_DECIMAIS_VALOR, RoundingMode.UP);
	}
	
	private BigDecimal calcularValorDesconto(BigDecimal valor, BigDecimal percentual) {
		BigDecimal valorDesconto =  valor.multiply(percentual);
		
		return valor.subtract(valorDesconto).setScale(CASAS_DECIMAIS_VALOR, RoundingMode.UP);
	}
	
}
