package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.gerenciamento.DAO.CaixaDAO;
import br.com.gerenciamento.DAO.FuncionarioDAO;
import br.com.gerenciamento.entidade.Caixa;
import br.com.gerenciamento.entidade.Funcionario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CaixaBean implements Serializable {
	private ScheduleModel enventModel;
	private Caixa caixa;
	private List<Caixa> caixas;
	private List<Funcionario> funcionarios;


	public ScheduleModel getenventModel() {
		return enventModel;
	}

	public void setenventModel(ScheduleModel enventModel) {
		this.enventModel = enventModel;
	}

	public List<Caixa> getCaixas() {
		return caixas;
	}

	public void setCaixas(List<Caixa> caixas) {
		this.caixas = caixas;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	private FuncionarioDAO getFuncionarioDAO() {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		return funcionarioDAO;
	}

	private CaixaDAO getCaixaDAO() {
		CaixaDAO caixaDAO = new CaixaDAO();
		return caixaDAO;
	}

	@PostConstruct
	public void inicializar() {
		this.enventModel = new DefaultScheduleModel();
		
        
		try {
			this.caixas = getCaixaDAO().listar();
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao inicializar abertura de caixa");
		}

		for (Caixa cx : caixas) {
			DefaultScheduleEvent evento = new DefaultScheduleEvent();
			evento.setEndDate(cx.getDataFechamento());
			evento.setStartDate(cx.getDataAbertura());
			evento.setTitle(cx.getFuncionario().getPessoa().getNome());
			evento.setData(cx.getCodigo());
			evento.setDescription(cx.getDescricao());
			evento.setAllDay(true);
			evento.setEditable(true);

			this.enventModel.addEvent(evento);
			
			System.out.println(evento.getStartDate());
			System.out.println(evento.getEndDate());
		
		

		}

	}

	public void selecionado(SelectEvent selectEvent) {
		this.funcionarios = getFuncionarioDAO().listar();
		ScheduleEvent evento = (ScheduleEvent) selectEvent.getObject();

		for (Caixa cx : caixas) {
			if (cx.getCodigo() == (Long) evento.getData()) {
				this.caixa = cx;
				break;
			}
		}

		// caixa.setDataAbertura((Date) evento.getObject());
		// this.funcionarios = getFuncionarioDAO().listar();
	}

	public void novo(SelectEvent selectEvent) {
		this.caixa = new Caixa();
		ScheduleEvent evento = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
		this.caixa.setDataAbertura(new java.sql.Date(evento.getStartDate().getTime()));
		this.caixa.setDataFechamento(new java.sql.Date(evento.getEndDate().getTime()));
		this.funcionarios = getFuncionarioDAO().listar();
	}
	

	public void salvar() {
		
		
		System.out.println(caixa.getDataAbertura().toString());
		System.out.println(caixa.getDataFechamento().toString());
        
		if (caixa.getCodigo() == null) {
			if (caixa.getDataAbertura().getTime() <= caixa.getDataFechamento().getTime()) {
				try {
					getCaixaDAO().salvar(caixa);
					this.inicializar();
					Messages.addGlobalInfo("Caixa salvo com sucesso");
				} catch (RuntimeException e) {
					Messages.addGlobalError("Erro ao salvar no caixa");
					e.printStackTrace();
				}
				this.caixa = new Caixa();
				this.funcionarios = getFuncionarioDAO().listar();
			} else {
				Messages.addGlobalError("Data de fechamento não pode ser menor que a data de abertura");
			}
		} else {
			try {
				this.funcionarios = getFuncionarioDAO().listar();
				getCaixaDAO().editar(caixa);
				this.inicializar();
			} catch (Exception e) {
				Messages.addGlobalError("Erro ao editar abertura e fechamento de caixa");
			}
		}

	}
}
