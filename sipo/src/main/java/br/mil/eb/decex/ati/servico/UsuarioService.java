package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;
import br.mil.eb.decex.ati.excecao.CPFDuplicadoException;
import br.mil.eb.decex.ati.modelo.Usuario;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface UsuarioService extends Service<Long, Usuario> {

	/**
	 * Insere ou atualiza um usuário no mecanismo de persistencia
	 * @param usuario Usuário
	 * @throws CPFDuplicadoException CPF duplicado para um usuário ativo
	 */		
	void saveOrUpdate(Usuario usuario) throws CPFDuplicadoException;

	/**
	 * Busca usuário pelo CPF cadastrado
	 * @param cpf CPF do usuário
	 * @return Usuário
	 */
	Usuario findUsuarioAtivo(String cpf); 
		
}