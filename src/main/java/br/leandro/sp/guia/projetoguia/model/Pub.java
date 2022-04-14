package br.leandro.sp.guia.projetoguia.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.Data;

@Entity
@Data
public class Pub {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String atracoes;
	private String formaPagamento;
	private String horario;
	private String site;
	private String telefone;
	private boolean delivery;
	private boolean acessibilidade;
	private boolean estacionamento;
	@ManyToOne
	private TipoPub tipo;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private int preco;
	
	// retorna as fotos na forma de vetor de String
	public String[] verFotos() {
		return fotos.split(";");
	}
	
	

}
