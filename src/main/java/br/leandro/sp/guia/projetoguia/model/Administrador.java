package br.leandro.sp.guia.projetoguia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.leandro.sp.guia.projetoguia.util.HashUtil;
import lombok.Data;

// criando get e sets com lombok
@Data
// mapeia a entidade para o jpa
@Entity
public class Administrador {
	// chave primaria auto-incremento 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	// define a coluna email como um indice único
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	
	// método set que aplica o hash na senha
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	
	// metodo que "seta" o hash na senha
	public void setSenhaComHash(String hash) {
		this.senha = hash;
	}

}
