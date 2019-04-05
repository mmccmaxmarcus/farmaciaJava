package br.com.gerenciamento.DAO;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.gerenciamento.entidade.Caixa;
import br.com.gerenciamento.util.HibernateUtil;

public class CaixaDAO extends GenericDAO<Caixa> {
	public Caixa buscar(Date dataAbertura) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Caixa.class);
			consulta.add(Restrictions.eq("dataAbertura", dataAbertura));
			Caixa resultado = (Caixa) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

}
