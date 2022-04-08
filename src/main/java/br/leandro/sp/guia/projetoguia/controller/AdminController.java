package br.leandro.sp.guia.projetoguia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.leandro.sp.guia.projetoguia.model.Administrador;
import br.leandro.sp.guia.projetoguia.repository.AdminRepository;
import br.leandro.sp.guia.projetoguia.util.HashUtil;

@Controller
public class AdminController {

	// variavel para persistencia dos dados
	@Autowired
	private AdminRepository repository;

	@RequestMapping("cadadmin")
	public String cadAdmin() {
		return "admin/cadAdmin";
	}

	@RequestMapping(value = "saveAdmin", method = RequestMethod.POST)
	public String saveAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		// verifica se ouveram erros na validação
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensageError", "Verifique os campos, ouveram erros...");
			// redireciona para o formulario
			return "redirect:cadadmin";
		}
		// variavel para descobrir alteração ou inserção
		boolean alter = admin.getId() != null ? true : false;
		// verifica se a senha está vazia
		if (admin.getSenha().equals(HashUtil.hash(""))) {
			if (!alter) {

				// retira a parte antes do @ no e-mail
				String part = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// "setar" a parte na senha do admin
				admin.setSenha(part);
			} else {
				// busca a senha atual no banco
				String hash = repository.findById(admin.getId()).get().getSenha();
				// "setar" o hash na senha
				admin.setSenhaComHash(hash);
			}
		}

		try {
			// salva na database a entidade
			repository.save(admin);
			// add uma mensagem de sucesso
			attr.addFlashAttribute("sucessMensage", "Admin cadastrado com sucesso!!! ID:" + admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensageErro", "Ouve um erro ao cadastrar" + e.getMessage());
		}

		return "redirect:cadadmin";
	}

	// requestMapping para lista de administradores
	@RequestMapping("listAdmin/{page}")
	public String listAdmin(Model model, @PathVariable("page") int page) {
		// cria um pageable informando os parametros da pagina
		PageRequest pr = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));

		// cria um page de admin atraves dos parametros passados ao repository
		Page<Administrador> pagina = repository.findAll(pr);

		// adiciona a model, a lista com os admins
		model.addAttribute("admins", pagina.getContent());

		// variavel para o total de paginas
		int totalPages = pagina.getTotalPages();

		// cria uma list de inteiros para armazenar os numeros das paginas
		List<Integer> numPag = new ArrayList<Integer>();

		// preencher o list com as paginas
		for (int i = 1; i <= totalPages; i++) {
			// adiciona a página ao list
			numPag.add(i);
		}
		// adiciona os valores a model
		model.addAttribute("numPag", numPag);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);

		// retorna para o html da lista
		return "admin/listaAdmin";
	}

	@RequestMapping("alteradmin")
	public String alterAdmin(Long id, Model model) {
		Administrador adm = repository.findById(id).get();
		model.addAttribute("adm", adm);
		return "forward:cadadmin";
	}

	@RequestMapping("deleteadmin")
	public String deleteAdmin(Long id) {
		repository.deleteById(id);
		return "redirect:listAdmin/1";
	}

}
