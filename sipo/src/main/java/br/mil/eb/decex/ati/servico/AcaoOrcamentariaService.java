package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.AcaoOrcamentaria;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface AcaoOrcamentariaService extends Service<Long, AcaoOrcamentaria> {

	/**
	 * Insere ou atualiza uma acao orçamentária no mecanismo de persistencia
	 * @param acaoOrcamentaria ação orçamentária
	 */		
	void saveOrUpdate(AcaoOrcamentaria acaoOrcamentaria);
	
}