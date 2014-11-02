package br.mil.eb.decex.ati.servico;

import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface PlanejamentoExercicioService 
	extends Service<Long, ConfiguracaoExercicio>{

}