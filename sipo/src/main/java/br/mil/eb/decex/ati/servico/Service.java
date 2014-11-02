package br.mil.eb.decex.ati.servico;

import java.util.List;
import javax.ejb.Local;

/**
 * Servico com métodos usuais para buscas 
 * simples pelo Id, buscas de listas e funcionalidades 
 * CRUD
 * 
 * @author William <b>Moreira</b> de Pinho - 1o Ten QCO
 * @version 1.0
 */
@Local
public interface Service<PK, T> {

	/**
	 * Busca simples de entidade pela sua chave primária
	 * @param primarykey chave primária da entidade
	 * @return entidade simples
	 */
	T findById(PK primarykey);
	
	/**
	 * Busca todas as entidades
	 * @return lista de entidades
	 */
	List<T> findAll();
	
	/**
	 * Salva uma entidade no mecânismo de persistência
	 * @param entity entidade
	 */
	void persist(T entity);
	
	/**
	 * Atualiza uma entidade no mecânismo de persistência
	 * @param entity entidade
	 */	
	void merger(T entity);
	
	/**
	 * Remove uma entidade no mecânismo de persistência
	 * @param entity entidade
	 * @return success -> operação realizada com sucesso<br/> 
	 * failure -> falha na operação
	 */		
	String remove(T entity);
		
}