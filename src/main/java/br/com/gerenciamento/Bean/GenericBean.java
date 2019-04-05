package br.com.gerenciamento.Bean;

import javax.faces.event.ActionEvent;


public interface GenericBean {
    public void novo();
    public void salvar();
    public void listar();
    public void excluir(ActionEvent evento);
    public void editar(ActionEvent evento);
    
}
