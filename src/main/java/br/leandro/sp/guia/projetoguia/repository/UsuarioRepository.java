package br.leandro.sp.guia.projetoguia.repository;



import org.springframework.data.repository.PagingAndSortingRepository;


import br.leandro.sp.guia.projetoguia.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	
}
