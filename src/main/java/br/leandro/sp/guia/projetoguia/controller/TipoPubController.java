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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.leandro.sp.guia.projetoguia.model.TipoPub;
import br.leandro.sp.guia.projetoguia.repository.TipoPubRepository;

@Controller
public class TipoPubController {

	@Autowired
	private TipoPubRepository repository;

	// Cadastro do tipo do pub
	@RequestMapping("cadtipopub")
	public String cadPub() {
		return "tipopub/cadTipoPub";
	}

	// salvar o tipo do pub
	@RequestMapping(value = "saveTipoPub", method = RequestMethod.POST)
	public String saveTypePub(@Valid TipoPub tPub, RedirectAttributes attr) {

		try {
			// salva na database o pub(entidade)
			repository.save(tPub);
			// mensagem de sucesso
			attr.addFlashAttribute("sucessMensage", "O tipo do Pub foi cadastrado com sucesso !!! ID:" + tPub.getId());
		} catch (Exception e) {
			// mensagem de erro
			attr.addFlashAttribute("mensageErro", "Ouve um erro oo cadastrar o tipo do Pub" + e.getMessage());
		}

		return "redirect:cadtipopub";
	}

	// lista do tipo do pub
	@RequestMapping("listtipopub/{page}")
	public String listaTipoPub(Model model, @PathVariable("page") int page) {
		PageRequest pr = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));

		Page<TipoPub> pagina = repository.findAll(pr);

		model.addAttribute("tpub", pagina.getContent());

		int totalPages = pagina.getTotalPages();

		List<Integer> numPag = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPag.add(i);
		}
		model.addAttribute("numPag", numPag);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);

		return "tipopub/listaTipoPub";
	}

	// alteração do pub
	@RequestMapping("altertipopub")
	public String alterTipoPub(Long id, Model model) {
		TipoPub tPub = repository.findById(id).get();
		model.addAttribute("tpub", tPub);
		return "forward:cadtipopub";

	}

	// deletar o pub
	@RequestMapping("deletetipopub")
	public String deleteTipoPub(Long id) {
		repository.deleteById(id);
		return "redirect:listtipopub/1";
	}

	// Pesquisar por palavra chave
	@RequestMapping("chave")
	public String pesquisaChave(String chave, Model model) {
		System.out.println(repository.buscarChave(chave));
		model.addAttribute("tpub", repository.buscarChave(chave));
		return "tipopub/listaTipoPub";
	}
}
