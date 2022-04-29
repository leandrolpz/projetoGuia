package br.leandro.sp.guia.projetoguia.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.leandro.sp.guia.projetoguia.annotation.Privado;
import br.leandro.sp.guia.projetoguia.annotation.Publico;
import br.leandro.sp.guia.projetoguia.model.Erro;
import br.leandro.sp.guia.projetoguia.model.Usuario;
import br.leandro.sp.guia.projetoguia.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario user){
		try {
		// inseri o usuario no banco de dados
		repository.save(user);
		// retorna um codigo HTTP 201, informa como acessar o recurso inserção e acrescenta no corpo da resposta o objeto inserido
		return ResponseEntity.created(URI.create("/api/usuario/"+user.getId())).body(user);
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Registro Duplicado", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> getUser(@PathVariable("id") Long id) {
		// tenta buscar o pub no repository
		Optional<Usuario> optional = repository.findById(id);
		// se o pub existir
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateUser(@RequestBody Usuario user, @PathVariable("id") Long id) {
		// validação do ID
		if(id != user.getId()) {
			throw new RuntimeException("ID invalido");
		}
		repository.save(user);
		return ResponseEntity.ok().build();
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
