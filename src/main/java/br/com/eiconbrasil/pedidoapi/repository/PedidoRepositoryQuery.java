package br.com.eiconbrasil.pedidoapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.eiconbrasil.pedidoapi.model.entity.PedidoEntity;
import br.com.eiconbrasil.pedidoapi.model.filter.PedidoFilter;

public interface PedidoRepositoryQuery {
	
	Page<PedidoEntity> find(PedidoFilter filter, Pageable pageable);
}
