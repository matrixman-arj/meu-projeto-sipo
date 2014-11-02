package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.AcaoOrcamentaria;
import br.mil.eb.decex.ati.servico.AcaoOrcamentariaService;

/**
 * Implementação JPA para operação em banco de dados de ações orçamentárias 
 * e execução de regras de negócio.
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateless
public class AcaoOrcamentariaServiceImpl 
	extends ServiceImpl<Long, AcaoOrcamentaria> 
	implements AcaoOrcamentariaService {

	@Override
	public void saveOrUpdate(AcaoOrcamentaria acaoOrcamentaria) {

		if(acaoOrcamentaria != null) 						
			if(acaoOrcamentaria.getId() == null)
				persist(acaoOrcamentaria);
			else
				merger(acaoOrcamentaria);
			
	}	
}