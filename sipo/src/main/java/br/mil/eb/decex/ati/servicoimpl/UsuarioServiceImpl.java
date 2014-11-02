package br.mil.eb.decex.ati.servicoimpl;

import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import br.mil.eb.decex.ati.enumerado.SituacaoUsuario;
import br.mil.eb.decex.ati.excecao.CPFDuplicadoException;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.servico.UsuarioService;

/**
 * Implementação JPA para operação em banco de dados de usuários  
 * e execução de regras de negócio.
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateless
public class UsuarioServiceImpl 
	extends ServiceImpl<Long, Usuario> 
	implements UsuarioService {

	@Override
	public Usuario findUsuarioAtivo(String cpf) {

		TypedQuery<Usuario> query = getEntityManager()
				.createQuery("SELECT u FROM Usuario u WHERE u.situacao != :situacao "
						+ "AND u.cpf = :cpf", Usuario.class);
			
		query.setParameter("situacao", SituacaoUsuario.ATIVO);
		query.setParameter("cpf", cpf);

		Usuario usuario = null;	
		
		try {
			usuario = query.getSingleResult();
		} catch(NoResultException e) {
			usuario = null;
		}
		
		return usuario;			
	}	
	
	@Override
	public void saveOrUpdate(Usuario usuario) 
		throws CPFDuplicadoException {
		
		if(usuario != null) {
			try {
							
				usuario.convertSenhaToMD5();
				
				if(usuario.getId() == null){
					
					if(findUsuarioAtivo(usuario.getCpf()) != null) {
						throw new CPFDuplicadoException("Já existe um " 
							+ "usuário ativo para este CPF.");
					} else {
						usuario.setSituacao(SituacaoUsuario.PENDENTE);
						persist(usuario);
					}
				} else {
					merger(usuario);
				}
								
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}		
	}		
}