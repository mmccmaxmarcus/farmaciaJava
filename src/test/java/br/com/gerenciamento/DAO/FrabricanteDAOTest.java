package br.com.gerenciamento.DAO;

import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Fabricante;

public class FrabricanteDAOTest {
    @Test
    @Ignore
    public void salvar(){
    	Fabricante fabricante = new Fabricante();
    	fabricante.setDescricao("Qualquer");
    	
    	FabricanteDAO fabricanteDAO = new FabricanteDAO();
    	fabricanteDAO.salvar(fabricante);
    }
    
    @Test
    public void merge() {
    	//Fabricante fabricante = new Fabricante();
    	//fabricante.setDescricao("Fabricante 01");
    	//FabricanteDAO fabricanteDAO = new FabricanteDAO();
    	//fabricanteDAO.merge(fabricante);
    	
    	FabricanteDAO fabricanteDAO =new FabricanteDAO();
    	Fabricante fabricante = fabricanteDAO.buscar(1l);
    	fabricante.setDescricao("Fabricante 112233");
    	fabricanteDAO.merge(fabricante);
    	
    	
    }
}
