package br.com.gerenciamento.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Cliente;
import br.com.gerenciamento.entidade.Pessoa;

public class ClienteDAOTest {
   
	@Test
	@Ignore
	public void salvar() throws ParseException{
	   ClienteDAO clienteDAO = new ClienteDAO();
	   Cliente cliente = new Cliente();
	   PessoaDAO pessoaDAO = new PessoaDAO();
	   Pessoa pessoa = pessoaDAO.buscar(1L);
	   
	   cliente.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("08/07/2017"));
	   cliente.setLiberado(true);
	   cliente.setPessoa(pessoa); 
	   clienteDAO.salvar(cliente);
	   
	   
	   
	   
   }
}
