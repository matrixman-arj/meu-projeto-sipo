package br.mil.eb.decex.ati.servicoimpl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import br.mil.eb.decex.ati.servico.Service;

/**
 * Service Generico com implementação JPA de métodos gerais 
 * para persistencia em Banco de Dados.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 *
 * @param <PK> Long, chave primária
 * @param <T> Entity, entidade do modelo
 */
@Stateless
public class ServiceImpl<PK, T> implements Service<PK, T> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@SuppressWarnings("unchecked")
	public T findById(PK primarykey) {
		return (T)em.find(getClassType(), primarykey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return em.createQuery("FROM " + getClassType().getName())
				.getResultList();
	}

	@Override
	public void persist(T entity) {
		em.persist(entity);
	}

	@Override
	public void merger(T entity) {
		em.merge(entity);
	}

	@Override
	public String remove(T entity) {
		String result = "success";
		try {
			em.remove(em.contains(entity) ? entity : em.merge(entity));
			em.flush();
		} catch (PersistenceException e){
			result = "failure";
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Expoe uma instancia de entity manager para utilização em 
	 * classes filhas
	 * @return entity manager
	 */
	protected EntityManager getEntityManager(){
		return em;
	}
	
	private Class<?> getClassType() {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		return clazz;
	}
	
}