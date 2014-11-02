package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;

import br.mil.eb.decex.ati.modelo.Equipamento;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
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