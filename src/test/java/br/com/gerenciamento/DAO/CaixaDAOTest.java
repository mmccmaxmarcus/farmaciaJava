package br.com.gerenciamento.DAO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Caixa;

public class CaixaDAOTest {
	@Test
	@Ignore
    public void salvar() throws ParseException{
    	Caixa caixa = new Caixa();
    	caixa.setDataAbertura(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/18"));
    	caixa.setValor(new BigDecimal("500.00"));
    	CaixaDAO caixaDAO = new CaixaDAO();
    	caixaDAO.salvar(caixa);
    	
    }
	
	@Test
	@Ignore
	public void buscar() throws ParseException{
    	CaixaDAO caixaDAO = new CaixaDAO();
    	Caixa caixa = caixaDAO.buscar(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/18"));
    	Assert.assertNull("Esse valor é null", caixa);
    }
}
