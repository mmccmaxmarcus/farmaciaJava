package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import br.com.gerenciamento.DAO.EstadoDAO;
import br.com.gerenciamento.entidade.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {
	private Estado estado;
	private List<Estado> estados;

	public void novo() {
		estado = new Estado();
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);
			this.novo();
			estados = estadoDAO.listar();
			Messages.addGlobalInfo("Estado salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel salvar o estado");
			e.printStackTrace();
		}
	}
	
    @PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			this.estados = estadoDAO.listar();
		} catch (Exception e) {
			Messages.addGlobalError("Erro ao tentar listar estado");
			e.printStackTrace();
		}
	}
    
    public void excluir(ActionEvent evento) {
    	
    	try {
    		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);
			estados = estadoDAO.listar();
			Messages.addGlobalInfo("Estado excluido com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel excluir o estado");
			e.printStackTrace();
		}
    }
    
    public void editar(ActionEvent evento) {
    	try {
			estado =(Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.editar(estado);
			estados = estadoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel editar o estado");
			e.printStackTrace();
		}
    }

}
