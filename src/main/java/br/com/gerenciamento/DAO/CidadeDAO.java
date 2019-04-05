package br.com.gerenciamento.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.gerenciamento.entidade.Cidade;
import br.com.gerenciamento.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade> {
  @SuppressWarnings("unchecked")
public List<Cidade> buscarPorEstado(Long estadoCodigo){
	  Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));
			consulta.addOrder(Order.asc("nome"));
			List<Cidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
  }
}
