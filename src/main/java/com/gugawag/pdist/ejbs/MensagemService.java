package com.gugawag.pdist.ejbs;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.gugawag.pdist.model.Mensagem;

@Stateless(name = "mensagemService")
@Remote
public class MensagemService {
	
	@EJB
	private MensagemDAO mensagemDao;
	private static final List<String> PALAVROES = Arrays.asList("palavrao", "censurada","proibida");

	public List<Mensagem> listar() {
		return mensagemDao.listar();
	}
	
	public void inserir(long id, String textoMensagem) {
		if (temPalavrao(textoMensagem)) {
			throw new IllegalArgumentException("Palavra imprópria utilizada!");
		}
		
		Mensagem mensagem = new Mensagem(id, textoMensagem);
		mensagemDao.inserir(mensagem);
	}
		
	private boolean temPalavrao(String texto) {
		return PALAVROES.stream()
			.anyMatch(palavrao -> texto.toLowerCase().contains(palavrao.toLowerCase()));	
	}
}