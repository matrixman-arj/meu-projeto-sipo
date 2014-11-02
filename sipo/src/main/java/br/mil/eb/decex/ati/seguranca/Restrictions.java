package br.mil.eb.decex.ati.seguranca;

import javax.inject.Inject;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import br.mil.eb.decex.ati.modelo.Usuario;

/**
 * Restringe acesso do usuário de acordo com seu perfil.
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
public class Restrictions {

	@Inject
	@Autenticado
	private Usuario user;
	
	@Secures
	@Departamento
	public boolean isDepartamento(Identity identity){
		return identity.hasRole("DEPARTAMENTO", user.getNomeGuerra(), "USER");
	}
	
	@Secures
	@Diretoria
	public boolean isDiretoria(Identity identity){
		return identity.hasRole("DIRETORIA", user.getNomeGuerra(), "USER");
	}
	
	@Secures
	@EstabelecimentoEnsino
	public boolean isEstabelecimentoEnsino(Identity identity){
		return identity.hasRole("ESTABELECIMENTO_ENSINO", user.getNomeGuerra(), "USER");
	}
	
}