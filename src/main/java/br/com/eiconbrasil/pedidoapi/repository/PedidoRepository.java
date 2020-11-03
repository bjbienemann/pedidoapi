package br.com.eiconbrasil.pedidoapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.eiconbrasil.pedidoapi.model.entity.PedidoEntity;

public interface PedidoRepository extends PagingAndSortingRepository<PedidoEntity, Long>, PedidoRepositoryQuery {

}
