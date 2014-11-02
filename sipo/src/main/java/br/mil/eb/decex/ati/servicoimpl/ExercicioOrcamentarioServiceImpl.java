package br.mil.eb.decex.ati.servicoimpl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio;
import br.mil.eb.decex.ati.modelo.ConfiguracaoPlano;
import br.mil.eb.decex.ati.modelo.ExercicioOrcamentario;
import br.mil.eb.decex.ati.servico.ExercicioOrcamentarioService;

/**
 * Implementação JPA para operação em banco de dados de exercícios 
 * orçamentários e execução de regras de negócio.
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateless
public class ExercicioOrcamentarioServiceImpl 
	extends ServiceImpl<Long, ExercicioOrcamentario> 
	implements ExercicioOrcamentarioService {

	@Override
	public List<ConfiguracaoExercicio> listByExercicio(
			ExercicioOrcamentario exercicio) {
		
		TypedQuery<ConfiguracaoExercicio> query = getEntityManager()
				.createQuery("SELECT o FROM ConfiguracaoExercicio o WHERE o.exercicio = :exercicio",
						ConfiguracaoExercicio.class);
						
		return query.setParameter("exercicio", exercicio).getResultList();
	}		
	
	@Override
	public ExercicioOrcamentario findByAno(Integer ano) {
		
		TypedQuery<ExercicioOrcamentario> query = getEntityManager()
				.createQuery("SELECT o FROM ExercicioOrcamentario o WHERE o.ano = :ano",
						ExercicioOrcamentario.class);
						
		return query.setParameter("ano", ano).getSingleResult();
		
	}

	@Override
	public void saveOrUpdate(ExercicioOrcamentario exercicioOrcamentario) {
		
		if(exercicioOrcamentario != null) 									
			if(exercicioOrcamentario.getId() == null)
				persist(exercicioOrcamentario);
			else 
				merger(exercicioOrcamentario);
		
	}

	@Override
	public void addConfiguracao(ConfiguracaoExercicio configuracaoExercicio) {

		if(configuracaoExercicio != null) 									
			if(configuracaoExercicio.getId() == null)
				getEntityManager().persist(configuracaoExercicio);
			else
				getEntityManager().merge(configuracaoExercicio);
		
	}

	@Override
	public String remove(ConfiguracaoExercicio configuracaoExercicio) {
		
		String result = "success";
		
		try {
			getEntityManager().remove(getEntityManager().contains(configuracaoExercicio) ? 
					configuracaoExercicio : getEntityManager().merge(configuracaoExercicio));
			getEntityManager().flush();
		} catch (PersistenceException e){
			result = "failure";
			e.printStackTrace();
		}
		
		return result;	
	}

	/* (non-Javadoc)
	 * @see br.mil.eb.decex.ati.servico.ExercicioOrcamentarioService#addTetoOrcamentario(br.mil.eb.decex.ati.modelo.ConfiguracaoPlano)
	 */
	@Override
	public void addTetoOrcamentario(ConfiguracaoPlano configuracaoPlano) {
		
		getEntityManager().merge(configuracaoPlano);		
				
	}

	@Override
	public void clonarConfiguracaoAnterior(ExercicioOrcamentario exercicioOrcamentario) {
		
		//Recupera o exercício orçamentário anterior
		Integer ano = exercicioOrcamentario.getAno() - 1;
		ExercicioOrcamentario anterior = findByAno(ano);		
		
		//Lista as configurações anteriores
		List<ConfiguracaoExercicio> anteriores = listByExercicio(anterior); 		
		
		//Clona cada configuração retornada da lista
		for (ConfiguracaoExercicio c : anteriores) {
			
			//clona o objeto
			ConfiguracaoExercicio clone = (ConfiguracaoExercicio)ConfiguracaoExercicio.cloneSerializable(c);
			
			//atualiza para exercicio orçamentario atual e marca para inclusão
			clone.setId(null);
			clone.setExercicio(exercicioOrcamentario);
//			clone.setGrupo3(null);
//			clone.setGrupo4(null);
//			clone.setDiariasPassagens(null);
			
			//Iterar sobre a lista retirando seu estado persistente
//			Set<PlanoDetalhamento> pls = clone.getPlanos();
//			Set<PlanoDetalhamento> npls = new HashSet<PlanoDetalhamento>();
			
//			for (PlanoDetalhamento p : pls) 
//				npls.add(p);
			
//			clone.setPlanos(npls);
			
			//persiste no banco de dados
			addConfiguracao(clone);			
		}
	}
}