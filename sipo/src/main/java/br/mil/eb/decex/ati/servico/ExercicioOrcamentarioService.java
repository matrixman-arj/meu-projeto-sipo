package br.mil.eb.decex.ati.servico;

import java.util.List;
import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio;
import br.mil.eb.decex.ati.modelo.ConfiguracaoPlano;
import br.mil.eb.decex.ati.modelo.ExercicioOrcamentario;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface ExercicioOrcamentarioService extends Service<Long, ExercicioOrcamentario> {

	/**
	 * Insere ou atualiza um exercício orçamentário no mecanismo de persistencia
	 * @param exercicioOrcamentario Exercício Orçamentário
	 */	
	void saveOrUpdate(ExercicioOrcamentario exercicioOrcamentario);

	/**
	 * Insere uma nova configuracao de exercicio no mecanismo de persistencia
	 * @param configuracaoExercicio configuração do exercício orçamentário
	 */
	void addConfiguracao(ConfiguracaoExercicio configuracaoExercicio);

	/**
	 * Insere os tetos orçamentários encapsulados no objeto ConfiguracaoPlano
	 * @param configuracaoPlano tetos orçamentarios que devem ser persistidos
	 */
	void addTetoOrcamentario(ConfiguracaoPlano configuracaoPlano);
	
	/**
	 * Clona a configuração do exercício orçamentário anterior ao exercício atual
	 * @param exercicioOrcamentario exercício orçamentário atual
	 */
	void clonarConfiguracaoAnterior(ExercicioOrcamentario exercicioOrcamentario);
	
	/**
	 * Sobrecarga para exclusão de configurações de exercícios orçamentários
	 * @param configuracaoExercicio configuração de exercício orçamentário
	 * @return Sucesso ou Falha
	 */
	String remove(ConfiguracaoExercicio configuracaoExercicio);	
	
	/**
	 * Lista as configurações para um exercício orçamentário
	 * @param exercicio exercício orçamentário
	 * @return lista de configurações de exercicios
	 */
	List<ConfiguracaoExercicio> listByExercicio(ExercicioOrcamentario exercicio);
	
	/**
	 * Recupera um exercício orçamentário tem o ano como parâmetro de busca
	 * @param ano ano do exercício orçamentário
	 * @return exercício orçamentário
	 */
	ExercicioOrcamentario findByAno(Integer ano);
	
}