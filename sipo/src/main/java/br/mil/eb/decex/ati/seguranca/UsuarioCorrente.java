package br.mil.eb.decex.ati.seguranca;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import br.mil.eb.decex.ati.modelo.Usuario;

/**
 * Disponibiliza o usuário logado para o restante da  
 * aplicação.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@SessionScoped
@ManagedBean(name="usuarioCorrente")
public class UsuarioCorrente {

	private Usuario usuarioLogado;
	
	@Produces
	@Autenticado
	@Named("usuarioLogado")
	public Usuario getUsuarioLogado(){
		return usuarioLogado;
	}
	
	/**
	 * Listener de evento. Quando um usuário loga com sucesso este 
	 * listener disponibiliza o usuário em nível de aplicação e estabelece 
	 * tempo de expiração da sessão.
	 * @param user usuário autenticado
	 * @param request requisicao
	 */
	public void onLogin(@Observes @Autenticado Usuario user, HttpServletRequest request){
		usuarioLogado = user;
		request.getSession().setAttribute("usuarioLogado", user);
        request.getSession().setMaxInactiveInterval(3600);		
	}	
}