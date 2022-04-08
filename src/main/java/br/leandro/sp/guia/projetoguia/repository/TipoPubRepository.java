package br.leandro.sp.guia.projetoguia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.leandro.sp.guia.projetoguia.model.TipoPub;


public interface TipoPubRepository extends PagingAndSortingRepository<TipoPub, Long>{
	
	@Query("SELECT t FROM TipoPub t WHERE t.nome LIKE %:p% OR t.descricao LIKE %:p% OR t.palavraChave LIKE %:p%  ORDER BY t.palavraChave")
	public List<TipoPub> buscarChave(@Param("p") String param);
	
	public List<TipoPub> findAllByOrderByNomeAsc(); 

}
