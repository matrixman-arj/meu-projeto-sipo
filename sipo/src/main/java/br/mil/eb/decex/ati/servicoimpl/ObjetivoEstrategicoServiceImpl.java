package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.ObjetivoEstrategico;
import br.mil.eb.decex.ati.servico.ObjetivoEstrategicoService;

/**
 * Implementação JPA para operação em banco de dados de natureza de despesa 
 * e execução de regras de negócio.
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateless
public class ObjetivoEstrategicoServiceImpl extends ServiceImpl<Long, ObjetivoEstrategico> 
	implements ObjetivoEstrategicoService {

	@Override
	public void saveOrUpdate(ObjetivoEstrategico objetivoEstrategico) {
		
		if(objetivoEstrategico != null)
			if(objetivoEstrategico.getId() == null)
				persist(objetivoEstrategico);
			else
				merger(objetivoEstrategico);
	}
}