package br.com.gerenciamento.DAO;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Cidade;
import br.com.gerenciamento.entidade.Estado;

public class CidadeDAOTest {

	@Test
	public void salvar(){
		EstadoDAO dao = new EstadoDAO();
		Estado estado = dao.buscar(1L);
		Cidade cidade = new Cidade();
		cidade.setNome("Campo Maior");
		cidade.setEstado(estado);
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		cidadeDAO.salvar(cidade);
		
		System.out.println(cidade.getNome());
		System.out.println(cidade.getEstado().getNome());
		System.out.println(cidade.getEstado().getSigla());
		System.out.println("Adicionados");
		
	}
	
	@Test
	@Ignore
	public void listar(){
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> cidade = cidadeDAO.listar();
		for (Cidade resultado : cidade) {
			System.out.println(resultado.getCodigo());
			System.out.println(resultado.getNome());
			System.out.println(resultado.getEstado().getCodigo());
			System.out.println(resultado.getEstado().getNome());
			System.out.println(resultado.getEstado().getSigla());
		}
	}
	
	@Test
	@Ignore
	public void buscar(){
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(1l);	
	
			System.out.println(cidade.getCodigo());
			System.out.println(cidade.getNome());
			System.out.println(cidade.getEstado().getCodigo());
			System.out.println(cidade.getEstado().getNome());
			System.out.println(cidade.getEstado().getSigla());
		
	}
	
	@Test
	@Ignore
	public void excluir(){
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(1l);
		cidadeDAO.excluir(cidade);
	}
	
	@Test
	@Ignore
	public void buscarEstado(){
		Long estadoCodigo = 1L;
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> cidade = cidadeDAO.buscarPorEstado(estadoCodigo);
		for (Cidade resultado : cidade) {
			System.out.println(resultado.getCodigo());
			System.out.println(resultado.getNome());
			System.out.println(resultado.getEstado().getCodigo());
			System.out.println(resultado.getEstado().getNome());
			System.out.println(resultado.getEstado().getSigla());
		}
	}
	
	

}
