package br.leandro.sp.guia.projetoguia.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.leandro.sp.guia.projetoguia.annotation.Publico;
import br.leandro.sp.guia.projetoguia.model.Pub;
import br.leandro.sp.guia.projetoguia.repository.PubRepository;


@RestController
@RequestMapping("/api/pub")
public class PubRestController {
	@Autowired
	private PubRepository repository;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Pub> getPubs() {
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pub> getPub(@PathVariable("id") Long idPub) {
		// tenta buscar o pub no repository
		Optional<Pub> optional = repository.findById(idPub);
		// se o pub existir
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Publico
	@RequestMapping(value = "/tipo/{id}", method = RequestMethod.GET)
	public Iterable<Pub> getTipoPub(@PathVariable("id") Long id) {
		return repository.findByTipoId(id);

	}
	

}
