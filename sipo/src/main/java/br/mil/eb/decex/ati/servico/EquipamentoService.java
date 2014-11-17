package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;

import br.mil.eb.decex.ati.modelo.Equipamento;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author <b>Vanilton</b> Gomes dos Santos - 3º Sgt QE
 * @version 1.0
 */
@Local
public interface EquipamentoService extends Service<Long, Equipamento> {

	/**
	 * Insere ou atualiza um usuário no mecanismo de persistencia
	 * @param equipamento Equipmento
	 */		
	void saveOrUpdate(Equipamento equipamento);

	
}