package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.HistoricoDAO;
import br.com.gerenciamento.DAO.ProdutoDAO;
import br.com.gerenciamento.entidade.Historico;
import br.com.gerenciamento.entidade.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {
	private Produto produto;
	private Boolean exibiPainelDados;
	private Historico historico;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Boolean getExibiPainelDados() {
		return exibiPainelDados;
	}

	public void setExibiPainelDados(Boolean exibiPainelDados) {
		this.exibiPainelDados = exibiPainelDados;
	}
	
	public Historico getHistorico() {
		return historico;
	}
	
	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	public HistoricoDAO getHistoricoDao(){
		HistoricoDAO historicoDAO = new HistoricoDAO();
		return historicoDAO;
	}

	@PostConstruct
	public void novo() {
		this.historico = new Historico();
		this.produto = new Produto();
		exibiPainelDados = false;
	}

	public void buscar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto resultado = produtoDAO.buscar(produto.getCodigo());
			if (resultado != null) {
				exibiPainelDados = true;
				this.produto = resultado;
			} else {
				exibiPainelDados = false;
				Messages.addGlobalWarn("Código buscado não encontrado");
			}
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Erro ao buscar produto");
			e.printStackTrace();
		}
	}
	
	public void salvar(){
		try {
			historico.setHorario(new Date());
			historico.setProduto(produto);
			getHistoricoDao().merge(historico);
			Messages.addFlashGlobalInfo("Histórico salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Erro ao salvar histórico");
		}
	}

}
