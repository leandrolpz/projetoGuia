package br.leandro.sp.guia.projetoguia.repository;


import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;


import br.leandro.sp.guia.projetoguia.model.Pub;

public interface PubRepository extends PagingAndSortingRepository<Pub, Long> {
	
	public List<Pub> findByTipoId(Long id);

}
