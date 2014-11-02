package br.mil.eb.decex.ati.servicoimpl;

import javax.ejb.Stateless;
import br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio;
import br.mil.eb.decex.ati.servico.PlanejamentoExercicioService;

@Stateless
public class PlanejamentoExercicioServiceImpl 
	extends ServiceImpl<Long, ConfiguracaoExercicio> 
	implements PlanejamentoExercicioService {

}