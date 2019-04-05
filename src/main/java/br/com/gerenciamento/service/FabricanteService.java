package br.com.gerenciamento.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.gerenciamento.DAO.FabricanteDAO;
import br.com.gerenciamento.entidade.Fabricante;

@Path("fabricante")
public class FabricanteService {

	// http:\\localhost:8080/Gerenciamento/rest/fabricante
	@GET
	public String listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> fabricantes = fabricanteDAO.listarOrdenado("descricao");
		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);
		return json;
	}

	// http:\\localhost:8080/Gerenciamento/rest/fabricante/1
	@Path("{codigo}")
	@GET
	public String buscar(@PathParam("codigo") Long codigo) {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.buscar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(fabricante);
		return json;

	}

	// http:\\localhost:8080/Gerenciamento/rest/fabricante
	@POST
	public String salvar(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.salvar(fabricante);
		String retornoJson = gson.toJson(fabricante);
		return retornoJson;
	}

	// http:\\localhost:8080/Gerenciamento/rest/fabricante
	@PUT
	public String editar(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.editar(fabricante);
		String fabricanteEditado = gson.toJson(fabricante);
		return fabricanteEditado;

	}
	// http:\\localhost:8080/Gerenciamento/rest/fabricante

	@DELETE
	public String excluir(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricante = fabricanteDAO.buscar(fabricante.getCodigo());
		fabricanteDAO.excluir(fabricante);
		String fabricanteExcluido = gson.toJson(fabricante);
		return fabricanteExcluido;

	}
}
