package br.com.eiconbrasil.pedidoapi.model.mapper;

import org.mapstruct.Mapper;

import br.com.eiconbrasil.pedidoapi.model.dto.PedidoDto;
import br.com.eiconbrasil.pedidoapi.model.entity.PedidoEntity;

@Mapper(componentModel="spring")
public interface PedidoMapper {
	
	PedidoDto toDto(PedidoEntity entity);
	PedidoEntity toEntity(PedidoDto dto);
	
}
