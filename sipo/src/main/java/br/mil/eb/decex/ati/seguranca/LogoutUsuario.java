package br.mil.eb.decex.ati.seguranca;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.jboss.seam.security.events.PostLoggedOutEvent;

@Named
@RequestScoped
public class LogoutUsuario {

	@Inject
	private HttpServletRequest request;
	
	public void handlePostLoggedOutEvent(@Observes PostLoggedOutEvent event){
		request.getSession().removeAttribute("usuarioLogado");
		request.getSession().invalidate();
	}	
	
}