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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.leandro.sp.guia.projetoguia.model.Pub;
import br.leandro.sp.guia.projetoguia.repository.PubRepository;
import br.leandro.sp.guia.projetoguia.repository.TipoPubRepository;
import br.leandro.sp.guia.projetoguia.util.FirebaseUtil;

@Controller
public class PubController {
	// importa o repository
	@Autowired
	private TipoPubRepository repTipo;
	@Autowired
	private PubRepository repPub;
	@Autowired
	private FirebaseUtil fireUtil;
	
	// cadastro o Pub
	@RequestMapping("formPub")
	public String form(Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "pub/cadPub";
	}
	
	
	
	
	// salvar o tipo do pub
	@RequestMapping(value = "savePub", method = RequestMethod.POST)
	public String savePub(@Valid Pub pub, RedirectAttributes attr, @RequestParam("fileFotos") MultipartFile[] fileFotos) {	
		try {
			//string para armazenar as URLs
			String fotos = "";
			// percorre cada arquivo no vetor
			for(MultipartFile arquivo : fileFotos){
				// verifica se o arquivo existe
				if(arquivo.getOriginalFilename().isEmpty()) {
					// vai para o próximo arquivo
					continue;
				}
				fotos += fireUtil.upload(arquivo)+";";
			}
			
			pub.setFotos(fotos);
			repPub.save(pub);
			// mensagem de sucesso
			attr.addFlashAttribute("sucessMensage", "O Pub foi cadastrado com sucesso !!! ID:"+ pub.getId());
		} catch (Exception e) {
			//mensagem de erro
			attr.addFlashAttribute("mensageErro", "Ouve um erro ao cadastrar o Pub" + e.getMessage());
		}
		
		return "redirect:formPub";
	}
	
	
	
	
	//Listagem da lista dos Pubs/bares cadastrados 
	@RequestMapping("publist/{page}")
	public String listPub(Model model, @PathVariable("page") int page) {
		PageRequest pr = PageRequest.of(page - 1 , 6, Sort.by(Sort.Direction.ASC, "nome"));
		
		Page<Pub> pagina = repPub.findAll(pr);
		
		model.addAttribute("pubs", pagina.getContent());
		
		int totalPages = pagina.getTotalPages();
		
		List<Integer> numPag = new ArrayList<Integer>();
		
		for(int i = 1; i <= totalPages; i++) {
			numPag.add(i);
		}
		model.addAttribute("numPag", numPag);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		
		return "pub/listaPub";
	}
	
	@RequestMapping("alterpub")
	public String alterPub(Long id, Model model) {
		Pub pub = repPub.findById(id).get();
		model.addAttribute("pub", pub);
		return "forward:formPub";
		
	}
	
	@RequestMapping("deletepub")
	public String deletePub(Long id) {
		repPub.deleteById(id);
		return "redirect:publist/1";
	}
	
	@RequestMapping("excluirFotos")
	public String excluirFotos(Long id, int numFoto, Model model) {
		// busca pub no banoc
		Pub pub =  repPub.findById(id).get();
		// busca URL da foto
		String urlFoto = pub.verFotos()[numFoto];
		// deleta a foto
		fireUtil.delete(urlFoto);
		// remove a url do atributo foto
		pub.setFotos(pub.getFotos().replace(urlFoto+";", ""));
		//salva no BD
		repPub.save(pub);
		model.addAttribute("pub", pub);
		return "forward:formPub";
	}
	
	
	
	
	
}
