package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.ObjetivoEstrategico;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface ObjetivoEstrategicoService extends Service<Long, ObjetivoEstrategico> {
	
	/**
	 * Insere ou atualiza um objetivo estratégico no mecanismo de persistencia
	 * @param objetivoEstrategico objetivo estratégico
	 */	
	void saveOrUpdate(ObjetivoEstrategico objetivoEstrategico);
	
}