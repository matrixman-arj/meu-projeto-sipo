package br.mil.eb.decex.ati.seguranca;

import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.servico.UsuarioService;

/**
 * Implementação de autenticador utilizando seam security.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateful
@Named
public class AutenticadorUsuario extends BaseAuthenticator implements Authenticator {

	@Inject
	private UsuarioService service;
	
	@Inject
	private Credentials credentials;
	
	@Inject
	@Autenticado
	private Event<Usuario> loginEventSrc;
	
	@Inject
	private Identity identity;
	
	@Override
	public void authenticate() {
	
		String message = null;
		
		try {
			
			Usuario usuario = service.findUsuarioAtivo(credentials.getUsername());

			if(usuario == null)
				throw new NoResultException();
				
			if(usuario.equalsSenha(((PasswordCredential) credentials.getCredential()).getValue())){
				
				adicionarPerfis(usuario);
				loginEventSrc.fire(usuario);
				setStatus(AuthenticationStatus.SUCCESS);
				setUser(new SimpleUser(usuario.getCpf()));
				return;
			}
			
		} catch(NoResultException e) {
			message = "Este login não está autorizado a acessar o sistema";
		} catch(NoSuchAlgorithmException e) {
			message = "A senha está incorreta";
		}
		
		setStatus(AuthenticationStatus.FAILURE);
		
		if(message != null)
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "Confirmação"));			
		
	}
	
	private void adicionarPerfis(Usuario usuario) {
		
		String perfil = new String(usuario
				.getOrganizacaoMilitar().getTipo().toString());
		
		String login = new String(usuario.getNomeGuerra());

		identity.addRole(perfil, login, "USER");
	}
		
}