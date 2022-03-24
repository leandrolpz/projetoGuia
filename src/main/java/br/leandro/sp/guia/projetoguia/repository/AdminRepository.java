package br.leandro.sp.guia.projetoguia.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.leandro.sp.guia.projetoguia.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	
}
