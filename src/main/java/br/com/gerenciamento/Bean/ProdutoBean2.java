package br.com.gerenciamento.Bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.FabricanteDAO;
import br.com.gerenciamento.DAO.ProdutoDAO;
import br.com.gerenciamento.entidade.Fabricante;
import br.com.gerenciamento.entidade.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean2 implements Serializable {
	private Produto produto;
	private List<Produto> produtos;
	private ProdutoDAO produtoDAO;
	private List<Fabricante> fabricantes;
	private FabricanteDAO fabricanteDAO;
	private Long codigoProduto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	@PostConstruct
	public void iniciar() {
		produtoDAO = new ProdutoDAO();
		fabricanteDAO = new FabricanteDAO();
	}

	public void listar() {
		try {
			this.produtos = produtoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addFlashGlobalInfo("Erro ao listar produtos");
		}
	}

	public void carregarEdicao() {
		try {
			this.produto = produtoDAO.buscar(codigoProduto);
			
			this.fabricantes = fabricanteDAO.listarOrdenado("descricao");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Erro ao carregar edição");
		}
	}
	
	public void editar() throws IOException{
		try {
			 produtoDAO.editar(produto); 
			 Faces.redirect("./paginas/produtosListagem.xhtml");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Erro ao editar produto");
		}
	}
}
