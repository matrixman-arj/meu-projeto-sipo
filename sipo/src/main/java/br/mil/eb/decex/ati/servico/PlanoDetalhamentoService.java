package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.PlanoDetalhamento;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface PlanoDetalhamentoService extends Service<Long, PlanoDetalhamento> {

	/**
	 * Insere ou atualiza um plano de detalhamento no mecanismo de persistencia
	 * @param planoDetalhamento plano de detalhamento
	 */		
	void saveOrUpdate(PlanoDetalhamento planoDetalhamento);
	
}