package br.com.gerenciamento.DAO;

import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Cidade;
import br.com.gerenciamento.entidade.Pessoa;

public class PessoaDAOTest {
   @Test
	public void salvar(){
	   Pessoa pessoa = new Pessoa();
	   PessoaDAO pessoaDAO = new PessoaDAO();
	   CidadeDAO cidadeDAO = new CidadeDAO();
	   Cidade cidade = cidadeDAO.buscar(1l);
	   
	   pessoa.setCep("64010-260");
	   pessoa.setComplemento("Qualquer lugar");
	   pessoa.setCpf("044.254.403-07");
	   pessoa.setEmail("Deads2323@gmail.com");
	   pessoa.setNome("Maria");
	   pessoa.setNumero((short) 222);
	   pessoa.setRg("2780545");
	   pessoa.setRua("Quadra 03");
	   pessoa.setTelefone("9999999-99");
	   pessoa.setCelular("99978-8794");
	   pessoa.setCidade(cidade);
	   pessoaDAO.salvar(pessoa);
   }
   
   @Test
   @Ignore
   public void mergeSalvar(){
	   Pessoa pessoa = new Pessoa();
	   PessoaDAO pessoaDAO = new PessoaDAO();
	   CidadeDAO cidadeDAO = new CidadeDAO();
	   Cidade cidade = cidadeDAO.buscar(1l);
	   
	   pessoa.setCelular("99968-78887");
	   pessoa.setCep("64010-261");
	   pessoa.setComplemento("Qualquer lugar");
	   pessoa.setCpf("044.253.403-07");
	   pessoa.setEmail("Deads2328@gmail.com");
	   pessoa.setNome("Mario");
	   pessoa.setNumero((short) 223);
	   pessoa.setRg("2780547");
	   pessoa.setRua("Quadra 09");
	   pessoa.setTelefone("9999989-99");
	   pessoa.setCidade(cidade);
	   pessoaDAO.merge(pessoa);
   }
}
