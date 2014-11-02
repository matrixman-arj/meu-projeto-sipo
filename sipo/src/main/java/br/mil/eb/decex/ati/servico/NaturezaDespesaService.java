package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.NaturezaDespesa;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface NaturezaDespesaService extends Service<Long, NaturezaDespesa> {
	
	/**
	 * Insere ou atualiza uma natureza de despesa no mecanismo de persistencia
	 * @param naturezaDespesa natureza de despesa
	 */	
	void saveOrUpdate(NaturezaDespesa naturezaDespesa);
	
}