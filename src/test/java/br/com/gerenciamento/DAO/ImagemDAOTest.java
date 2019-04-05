package br.com.gerenciamento.DAO;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Imagem;

public class ImagemDAOTest {
	@Test
	@Ignore
     public void listarImagem(){
    	 ImagemDAO imagemDAO = new ImagemDAO();
    	 List<Imagem> imagem = imagemDAO.listarImagemProdutos(1l);
    	 for (Imagem imagens : imagem) {
			System.out.println(imagens.getDescricao());
			System.out.println(imagens.getCodigo());
			System.out.println(imagens.getImagem());
			System.out.println(imagens.getProduto().getFabricante());
		}
     }
}
