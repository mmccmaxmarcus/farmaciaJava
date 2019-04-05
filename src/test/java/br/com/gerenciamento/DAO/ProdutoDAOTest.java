package br.com.gerenciamento.DAO;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.gerenciamento.entidade.Fabricante;
import br.com.gerenciamento.entidade.Produto;

public class ProdutoDAOTest {
  @Test
  public void salvar(){
	  FabricanteDAO fabricanteDAO = new FabricanteDAO();
	  Fabricante fabricante = fabricanteDAO.buscar(1l);
	  
	  ProdutoDAO produtoDAO = new ProdutoDAO();
	  Produto produto = new Produto();
	  produto.setDescricao("1GB de VRAM");
	  produto.setQuantidade((short) 10);
	  produto.setPreco(new BigDecimal(100.56));
	  produto.setFabricante(fabricante);
	  produtoDAO.salvar(produto);
  }
}
