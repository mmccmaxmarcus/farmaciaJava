package br.com.gerenciamento.teste;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.util.HibernateUtil;

public class HibernateUtilTest {
     @Test
     @Ignore
	public static void main(String[] args) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		sessao.close();
		HibernateUtil.getSessionFactory().close();

	}

}
