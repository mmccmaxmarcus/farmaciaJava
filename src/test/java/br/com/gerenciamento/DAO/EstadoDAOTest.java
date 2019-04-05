package br.com.gerenciamento.DAO;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Estado;
import br.com.gerenciamento.entidade.Fabricante;

public class EstadoDAOTest {

	@Test
	public void salvarEstado(){
		Estado estado = new Estado();
		estado.setNome("Piauí");
		estado.setSigla("PI");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
	}
	
	@Test
	@Ignore
	public void listarEstado(){
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resuldado = estadoDAO.listar();
		for (Estado estado : resuldado) {
			System.out.println(estado.getSigla());
			System.out.println(estado.getNome());
		}
		
		}
	
	@Test
	@Ignore
	public void salvarFabricante(){
		Fabricante fabricante = new Fabricante();
		fabricante.setDescricao("Coca-Cola");
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.salvar(fabricante);
	}
	
	@Test
	@Ignore
	public void listaFabricante(){
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> resultado = fabricanteDAO.listar();
		for (Fabricante fabricante : resultado) {
			System.out.println(fabricante.getDescricao());
		}
		
	}
    @Test
    @Ignore
	public void buscar(){
		Long codigo = 2l;
		EstadoDAO dao = new EstadoDAO();
		Estado estado = dao.buscar(codigo);
		System.out.println(estado.getCodigo());
		System.out.println(estado.getNome());
		System.out.println(estado.getSigla());
		
	}
    
    @Test
    @Ignore
    public void excluir(){
    	Long codigo = 2l;
    	EstadoDAO dao = new EstadoDAO();
    	Estado estado = dao.buscar(codigo);
    	dao.excluir(estado);
    	System.out.println(estado.getCodigo());
    	System.out.println(estado.getNome());
    	System.out.println(estado.getSigla());
    	System.out.println("Foram excluidos");
    }
    
    @Test
    @Ignore
    public void editar(){
    	Long codigo = 1l;
    	EstadoDAO dao = new EstadoDAO();
    	Estado estado = new Estado();
    	estado.setNome("Joana");
    	estado.setSigla("PA");
    	estado.setCodigo(codigo);
    	dao.editar(estado);
    	System.out.println(estado.getNome());
    	System.out.println(estado.getSigla());
    	System.out.println(estado.getCodigo());
    	System.out.println("Editado");
    }
	

}
