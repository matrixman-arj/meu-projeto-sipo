package br.mil.eb.decex.ati.servico;

import java.util.List;
import javax.ejb.Local;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;

/**
 * Serviço para métodos específicos deste tipo. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface OrganizacaoMilitarService extends Service<Long, OrganizacaoMilitar> {

	/**
	 * Recupera organização militar passando sigla como filtro
	 * @param sigla sigla da organização militar
	 * @return organização militar
	 */
	OrganizacaoMilitar findBySigla(String sigla);	
	
	/**
	 * Insere ou atualiza uma organização militar no mecanismo de persistencia
	 * @param organizacaoMilitar organização militar
	 */
	void saveOrUpdate(OrganizacaoMilitar organizacaoMilitar);
	
	/**
	 * Lista todas as organizações militares Superiores, Departamentos e  
	 * Diretorias.
	 * @return lista de organizações militares
	 */
	List<OrganizacaoMilitar> listBySuperiores();
	
	/**
	 * Lista todas as organizações militares Superiores, Departamentos e  
	 * Diretorias de uma organização militar em particular.
	 * @param sigla sigla da organização militar
	 * @return lista de organizações militares
	 */	
	List<OrganizacaoMilitar> listBySuperiores(String sigla);	
	
	/**
	 * Lista os subordinados de uma organização militar
	 * @param superior organização militar superior
	 * @return lista de organizações militares subordinadas
	 */
	List<OrganizacaoMilitar> listBySubordinados(OrganizacaoMilitar superior);	
	
}