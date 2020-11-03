package br.com.eiconbrasil.pedidoapi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.eiconbrasil.pedidoapi.model.entity.PedidoEntity;
import br.com.eiconbrasil.pedidoapi.model.filter.PedidoFilter;

public class PedidoRepositoryQueryImpl implements PedidoRepositoryQuery {

	@PersistenceContext
	protected EntityManager em;
	
	@Override
	public Page<PedidoEntity> find(PedidoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PedidoEntity> criteriaQuery = builder.createQuery(PedidoEntity.class);
		Root<PedidoEntity> root = criteriaQuery.from(PedidoEntity.class);
		
		Predicate[] restrictions = criarRestricoes(filter, builder, root);
		criteriaQuery.where(restrictions);
		
		TypedQuery<PedidoEntity> typedQuery = em.createQuery(criteriaQuery);
		adicionarResticoesPaginacao(typedQuery, pageable);
		
		return new PageImpl<PedidoEntity>(typedQuery.getResultList(), pageable, total(restrictions));
	}
	
	protected Long total(Predicate[] restrictions) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<PedidoEntity> root = criteria.from(PedidoEntity.class);
		
		criteria.where(restrictions);
		
		criteria.select(builder.count(root));
		return em.createQuery(criteria).getSingleResult();
	}
	
	protected void adicionarResticoesPaginacao(TypedQuery<PedidoEntity> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistosPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalRegistosPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistosPagina);
	}
	
	protected Predicate[] criarRestricoes(PedidoFilter filter, CriteriaBuilder builder, Root<PedidoEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (Objects.nonNull(filter.getNumeroControle())) {
			predicates.add(builder.equal(root.get("numeroControle"), filter.getNumeroControle()));
		}
		
		if (Objects.nonNull(filter.getDataCadastro())) {
			predicates.add(builder.equal(root.get("dataCadastro"), filter.getDataCadastro()));
		}
		
		if (Objects.nonNull(filter.getCliente())) {
			predicates.add(builder.equal(root.get("codigoCliente"), filter.getCliente()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
