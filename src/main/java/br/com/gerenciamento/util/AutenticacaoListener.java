package br.com.gerenciamento.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.gerenciamento.Bean.AutenticacaoBean;
import br.com.gerenciamento.entidade.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		// System.out.println("Depois da fase: " + event.getPhaseId());
		String paginaAtual = Faces.getViewId();
		boolean paginaDeAutenticacao = paginaAtual.contains("autenticar.xhtml");
		if (!paginaDeAutenticacao) {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			if (autenticacaoBean == null) {
				Faces.navigate("/paginas/autenticar.xhtml");
				return;
			}
			Usuario usuario = autenticacaoBean.getLogado();
			if (usuario == null) {
				Faces.navigate("/paginas/autenticar.xhtml");
                return;
			}

		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// System.out.println("Antes da fase: " + event.getPhaseId());

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
