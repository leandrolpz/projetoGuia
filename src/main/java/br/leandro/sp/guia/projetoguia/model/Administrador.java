package br.leandro.sp.guia.projetoguia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
}
