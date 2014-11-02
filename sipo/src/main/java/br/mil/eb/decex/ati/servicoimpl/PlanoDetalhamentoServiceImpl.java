package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.PlanoDetalhamento;
import br.mil.eb.decex.ati.servico.PlanoDetalhamentoService;

/**
 * Implementação JPA para operação em banco de dados de planos de detalhamento 
 * e execução de regras de negócio.
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateless
public class PlanoDetalhamentoServiceImpl 
	extends ServiceImpl<Long, PlanoDetalhamento> 
	implements PlanoDetalhamentoService {

	@Override
	public void saveOrUpdate(PlanoDetalhamento planoDetalhamento) {

		if(planoDetalhamento != null) 			
			if(planoDetalhamento.getId() == null)
				persist(planoDetalhamento);
			else
				merger(planoDetalhamento);
		
	}		
}