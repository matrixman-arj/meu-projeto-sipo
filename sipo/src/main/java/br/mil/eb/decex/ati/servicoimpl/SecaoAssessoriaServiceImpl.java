package br.mil.eb.decex.ati.servicoimpl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import br.mil.eb.decex.ati.enumerado.TipoSecaoAssessoria;
import br.mil.eb.decex.ati.modelo.SecaoAssessoria;
import br.mil.eb.decex.ati.servico.SecaoAssessoriaService;

/**
 * Implementação JPA para operações em banco de dados de seção assessoria e  
 * execução de regras de negócio. 
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author <b>Vanilton</b> Gomes dos Santos - 3° Sgt QE
 * @version 1.0
 */
@Stateless
public class SecaoAssessoriaServiceImpl 
	extends ServiceImpl<Long, SecaoAssessoria> 
	implements SecaoAssessoriaService {

	@Override
	public SecaoAssessoria findBySigla(String sigla) {
		
		TypedQuery<SecaoAssessoria> query = getEntityManager()
				.createQuery("SELECT sec FROM SecaoAssessoria sec WHERE sec.sigla = :sigla",
						SecaoAssessoria.class);
			
			return query.setParameter("sigla", sigla).getSingleResult();
			
	}
	
	@Override
	public List<SecaoAssessoria> listBySecSuperiores() {
		TypedQuery<SecaoAssessoria> query = getEntityManager()
				.createQuery("SELECT sec FROM SecaoAssessoria sec WHERE sec.tipo != :tipo",
						SecaoAssessoria.class);
			
			
			return query.setParameter("tipo", TipoSecaoAssessoria.SUBSEÇAO).getResultList();
	}
	
	
	
	@Override
	public List<SecaoAssessoria> listBySecSubordinados(SecaoAssessoria secsuperior) {

		List<SecaoAssessoria> secoes = buscarSecSubordinados(secsuperior);
		List<SecaoAssessoria> subordinados = new ArrayList<SecaoAssessoria>();
		
		for (SecaoAssessoria secao : secoes) 
			if(secao.getListaSecSuperiores().contains(secsuperior))
				subordinados.add(secao);
		
		return subordinados;
	}

	/*
	 * Metodo auxiliar que busca todas as Seções Assessorias Subordinadas e devolve 
	 * o resultado para o método definido em interface que irá iterar sobre o resultado 
	 * e seleciona apenas os subordinados da SEÇÃO informada
	 */
	private List<SecaoAssessoria> buscarSecSubordinados(SecaoAssessoria Secsuperior) {
		
		TypedQuery<SecaoAssessoria> query = getEntityManager()
				.createQuery("SELECT sec FROM SecaoAssessoria sec WHERE sec.tipo != :tipo "
						+ "AND sec.id != :id", SecaoAssessoria.class);
		
		query.setParameter("tipo", TipoSecaoAssessoria.ASSESSORIA);
		query.setParameter("id", Secsuperior.getId());		
		
		return query.getResultList();		
	}
	
	@Override
	public List<SecaoAssessoria> listBySecSuperiores(String sigla) {
		
		SecaoAssessoria secao = getEntityManager()
				.createQuery("from SecaoAssessoria sec left join fetch sec.listaSecSuperiores l " + 
							 "where sec.sigla = :sigla ", SecaoAssessoria.class)
				.setParameter("sigla", sigla)
				.getResultList().get(0);
		
		return secao.getListaSecSuperiores();
	}
		
	@Override
	public void saveOrUpdate(SecaoAssessoria secaoAssessoria) {
		
		if(secaoAssessoria != null) {
			
			secaoAssessoria.setTipo(buildTipoSecaoAssessoria(secaoAssessoria.getListaSecSuperiores()));
			
			if(secaoAssessoria.getId() == null)
				persist(secaoAssessoria);
			else
				merger(secaoAssessoria);
		}
		
	}	
	
	/*
	 * Define o tipo da Seção Assessoria que está sendo inserida.
	 * 
	 * ASSESSORIA
	 * SEÇÃO
	 * SUBSEÇÃO
	 */
	private TipoSecaoAssessoria buildTipoSecaoAssessoria(List<SecaoAssessoria> Secsuperiores) {
		
		TipoSecaoAssessoria tipo = TipoSecaoAssessoria.SUBSEÇAO;
		
		if(Secsuperiores.size() == 0)
			tipo = TipoSecaoAssessoria.ASSESSORIA;
				
		if(Secsuperiores.size() == 1)  {
			List<SecaoAssessoria> l = listBySecSuperiores(Secsuperiores.get(0).getSigla());
			if(l.size() == 0)
				tipo = TipoSecaoAssessoria.SEÇAO;
		}
		
		return tipo;
	}
}