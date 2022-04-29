package br.leandro.sp.guia.projetoguia.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Erro {
	private HttpStatus stutus;
	private String mensagem;
	private String exception;
	
	public Erro(HttpStatus stt, String msg, String exc) {
		this.stutus = stt;
		this.mensagem = msg;
		this.exception = exc;
	}
	
}
