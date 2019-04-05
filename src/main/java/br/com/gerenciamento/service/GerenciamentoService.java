package br.com.gerenciamento.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("gerenciamento")
public class GerenciamentoService {
    @GET
	public String exibir () {
    	return "Max Marcus";
    }
}
