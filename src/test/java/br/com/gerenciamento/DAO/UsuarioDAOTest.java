package br.com.gerenciamento.DAO;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.gerenciamento.entidade.Pessoa;
import br.com.gerenciamento.entidade.Usuario;

public class UsuarioDAOTest {
   @Test
	public void salvar(){
	   PessoaDAO pessoaDAO = new PessoaDAO();
	   Pessoa pessoa = pessoaDAO.buscar(1L);
	   
	   Usuario usuario = new Usuario();
	   usuario.setPessoa(pessoa);
	   usuario.setAtivo(true);
	   usuario.setTipo('A');
	   usuario.setSenhaSemCriptografia("max123");
	   SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
	   usuario.setSenha(hash.toHex());
	   UsuarioDAO usuarioDAO = new UsuarioDAO();
	   usuarioDAO.salvar(usuario);
			   
   }
   
   @Test
   @Ignore
   public void listar(){
	   UsuarioDAO usuarioDAO = new UsuarioDAO();
	   List<Usuario> usuarios = usuarioDAO.listar();
	   for (Usuario resultado : usuarios) {
		System.out.println(resultado.getCodigo());
		System.out.println(resultado.getSenha());
		System.out.println(resultado.getAtivo());
		System.out.println(resultado.getTipo());
		System.out.println(resultado.getPessoa().getNome());
	}
   }
   
   @Ignore
   @Test
   public void autenticar(){
	   UsuarioDAO usuarioDAO = new UsuarioDAO();
	   Usuario usuario = usuarioDAO.autenticar("044.254.403-07", "max123");
	   System.out.println("Usuario: " + usuario);
   }
}
