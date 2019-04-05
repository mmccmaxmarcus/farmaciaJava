package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.gerenciamento.DAO.FabricanteDAO;
import br.com.gerenciamento.entidade.Fabricante;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FabricanteBean implements GenericBean,Serializable {
	private Fabricante fabricante;
	private List<Fabricante> fabricantes;

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	@Override
	public void novo() {
		this.fabricante = new Fabricante();

	}

	@Override
	public void salvar() {
		try {
			//FabricanteDAO fabricanteDAO = new FabricanteDAO();
			//fabricanteDAO.merge(fabricante);
			//this.novo();
			//this.fabricantes=fabricanteDAO.listar();
			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://localhost:8080/Gerenciamento/rest/fabricante");
		    Gson gson = new Gson();
		    String json = gson.toJson(fabricante);
		    
			caminho.request().post(Entity.json(json));
			
			this.fabricante = new Fabricante();
			
			json = caminho.request().get(String.class);
			Fabricante [] vetor = gson.fromJson(json, Fabricante[].class);
			this.fabricantes = Arrays.asList(vetor);
			Messages.addGlobalInfo("Fabricante salvo com sucesso");
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel salvar o fabricante");
			e.printStackTrace();
		}

	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			// FabricanteDAO fabricanteDAO = new FabricanteDAO();
			// fabricantes = fabricanteDAO.listar();
			Client cliente =  ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://localhost:8080/Gerenciamento/rest/fabricante");
			String json = caminho.request().get(String.class);
			Gson gson = new Gson();
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);
			//Transforma vetor em um array list
			this.fabricantes = Arrays.asList(vetor);
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar fabricante");
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			this.fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.excluir(fabricante);
			this.fabricantes = fabricanteDAO.listar();
			Messages.addGlobalInfo("Fabricante excluido com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Fabricante não pode ser excluído");
			e.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			this.fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.editar(fabricante);
			this.fabricantes = fabricanteDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Fabricante não pode ser editado");
			e.printStackTrace();
		}

	}

}
