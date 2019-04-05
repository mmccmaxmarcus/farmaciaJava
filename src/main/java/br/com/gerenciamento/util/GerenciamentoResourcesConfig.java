package br.com.gerenciamento.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class GerenciamentoResourcesConfig extends ResourceConfig {
    public GerenciamentoResourcesConfig () {
    	packages("br.com.gerenciamento.service");
    }
}
