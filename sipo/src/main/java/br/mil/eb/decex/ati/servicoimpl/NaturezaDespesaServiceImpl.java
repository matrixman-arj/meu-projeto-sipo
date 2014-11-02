package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.NaturezaDespesa;
import br.mil.eb.decex.ati.servico.NaturezaDespesaService;

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
public class NaturezaDespesaServiceImpl extends ServiceImpl<Long, NaturezaDespesa> 
	implements NaturezaDespesaService {

	@Override
	public void saveOrUpdate(NaturezaDespesa naturezaDespesa) {
		
		if(naturezaDespesa != null)
			if(naturezaDespesa.getId() == null)
				persist(naturezaDespesa);
			else
				merger(naturezaDespesa);
	}
}