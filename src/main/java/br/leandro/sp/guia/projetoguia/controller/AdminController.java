package br.leandro.sp.guia.projetoguia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.leandro.sp.guia.projetoguia.model.Administrador;
import br.leandro.sp.guia.projetoguia.repository.AdminRepository;

@Controller
public class AdminController {

	// variavel para persistencia dos dados
	@Autowired
	private AdminRepository  repository;
	
	@RequestMapping("cadadmin")
	public String cadAdmin() {
		return "cadAdmin";
	}
	
	@RequestMapping(value = "saveAdmin", method = RequestMethod.POST)
	public String saveAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		// verifica se ouveram erros na validação
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensageError", "Verifique os campos, ouveram erros...");
			// redireciona para o formulario
			return "redirect:cadadmin";
		}
		try {
			// salva na database a entidade 
			repository.save(admin);
			// add uma mensagem de sucesso
			attr.addFlashAttribute("sucessMensage", "Admin cadastrado com sucesso!!! ID:"+admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensageErro", "Ouve um erro ao cadastrar"+e.getMessage());
		}
		
		return  "redirect:cadadmin";
	}
}
