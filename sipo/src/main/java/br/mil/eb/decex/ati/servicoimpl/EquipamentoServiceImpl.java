package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.Equipamento;
import br.mil.eb.decex.ati.servico.EquipamentoService;

/**
 * Implementação JPA para operação em banco de dados de equipamentos  
 * e execução de regras de negócio.
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author <b>Vanilton</b> Gomes dos Santos - 3º Sgt QE
 * @version 1.0
 */
@Stateless
public class EquipamentoServiceImpl 
	extends ServiceImpl<Long, Equipamento> 
	implements EquipamentoService {

	@Override
	public void saveOrUpdate(Equipamento equipamento) {
		
		if(equipamento != null) {
			if(equipamento.getId() == null) {
				persist(equipamento);
				 
			} else {
				merger(equipamento);
			}
		}		
	}		
}