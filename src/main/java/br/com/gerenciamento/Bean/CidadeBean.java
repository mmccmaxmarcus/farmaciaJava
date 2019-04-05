package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.CidadeDAO;
import br.com.gerenciamento.DAO.EstadoDAO;
import br.com.gerenciamento.entidade.Cidade;
import br.com.gerenciamento.entidade.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {
	private Cidade cidade;
	private List<Cidade> cidades;
	private List<Estado> estados;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void novo() {
		try {
		this.cidade = new Cidade();
		EstadoDAO estadoDAO = new EstadoDAO();
		this.estados = estadoDAO.listar();
		}catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao gerar uma nova cidade");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);
			this.cidade = new Cidade();
			EstadoDAO estadoDAO = new EstadoDAO();
			this.estados = estadoDAO.listar();
			this.cidades = cidadeDAO.listar();
			Messages.addFlashGlobalInfo("Cidade salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Não foi possivel salvar cidade");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			this.cidades = cidadeDAO.listar();
		} catch (Exception e) {
			Messages.addFlashGlobalError("Erro ao tentar listar");
			e.printStackTrace();
		}

	}
	
	public void excluir(ActionEvent evento) {
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.excluir(cidade);
			cidades = cidadeDAO.listar();
			Messages.addFlashGlobalInfo("Cidade excluida com sucesso");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Não foi possivel excluir a cidade");

		}
	}
	
	public void editar(ActionEvent evento) {
    	try {
			cidade =(Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			EstadoDAO estadoDAO = new EstadoDAO();
			this.estados = estadoDAO.listar();
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.editar(cidade);
			cidades = cidadeDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel editar a cidade");
			e.printStackTrace();
		}
    }

}
