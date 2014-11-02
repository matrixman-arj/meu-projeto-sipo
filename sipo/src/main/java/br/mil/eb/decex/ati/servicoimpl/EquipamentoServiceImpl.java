package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.Equipamento;
import br.mil.eb.decex.ati.servico.EquipamentoService;

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
public class EquipamentoServiceImpl 
	extends ServiceImpl<Long, Equipamento> 
	implements EquipamentoService {

	
	
	@Override
	public void saveOrUpdate(Equipamento equipamento) 
		{
		
		if(equipamento != null) {
			if(equipamento.getId() == null){
				 
			} else {
				merger(equipamento);
			}
		}		
	}		
}