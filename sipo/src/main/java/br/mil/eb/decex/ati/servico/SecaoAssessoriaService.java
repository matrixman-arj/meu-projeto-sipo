package br.mil.eb.decex.ati.servico;

import java.util.List;
import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.SecaoAssessoria;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author <b>Vanilton</b> Gomes dos Santos - 3° Sgt QE
 * @version 1.0
 */
@Local
public interface SecaoAssessoriaService extends Service<Long, SecaoAssessoria> {

	/**
	 * Recupera seção assessoria passando sigla como filtro
	 * @param sigla da seção assessoria
	 * @return seção assessoria
	 */
	SecaoAssessoria findBySigla(String sigla);	
	
	/**
	 * Insere ou atualiza uma seção assessoria no mecanismo de persistencia
	 * @param secaoAssessoria seção assessoria
	 */
	void saveOrUpdate(SecaoAssessoria secaoAssessoria);
	
	/**
	 * Lista todas as seções assessorias Superiores, Assessorias e  
	 * Seções.
	 * @return lista de seção assessoria
	 */
	List<SecaoAssessoria> listBySecSuperiores();
	
	/**
	 * Lista todas as seções assessorias Superiores, Assessorias e  
	 * Seções de uma seção assessoria em particular.
	 * @param sigla sigla da seção assessoria
	 * @return lista de seções assessorias
	 */	
	List<SecaoAssessoria> listBySecSuperiores(String sigla);	
	
	/**
	 * Lista os subordinados de uma seção assessoria
	 * @param superior seção assessoria superior
	 * @return lista de seções assessorias subordinadas
	 */
	List<SecaoAssessoria> listBySecSubordinados(SecaoAssessoria superior);	
	
}