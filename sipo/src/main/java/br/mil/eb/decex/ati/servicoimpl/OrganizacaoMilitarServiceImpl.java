package br.mil.eb.decex.ati.servicoimpl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import br.mil.eb.decex.ati.enumerado.TipoOM;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.servico.OrganizacaoMilitarService;

/**
 * Implementação JPA para operações em banco de dados de organização militar e  
 * execução de regras de negócio. 
 * <br/>
 * <b>Os comentários para os métodos de negócio estão definidos na interface.</b>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Stateless
public class OrganizacaoMilitarServiceImpl 
	extends ServiceImpl<Long, OrganizacaoMilitar> 
	implements OrganizacaoMilitarService {

	@Override
	public OrganizacaoMilitar findBySigla(String sigla) {
		
		TypedQuery<OrganizacaoMilitar> query = getEntityManager()
				.createQuery("SELECT o FROM OrganizacaoMilitar o WHERE o.sigla = :sigla",
						OrganizacaoMilitar.class);
			
			return query.setParameter("sigla", sigla).getSingleResult();
			
	}
	
	@Override
	public List<OrganizacaoMilitar> listBySuperiores() {
		TypedQuery<OrganizacaoMilitar> query = getEntityManager()
				.createQuery("SELECT o FROM OrganizacaoMilitar o WHERE o.tipo != :tipo",
						OrganizacaoMilitar.class);
			
			
			return query.setParameter("tipo", TipoOM.ESTABELECIMENTO_ENSINO).getResultList();
	}
	
	
	
	@Override
	public List<OrganizacaoMilitar> listBySubordinados(OrganizacaoMilitar superior) {

		List<OrganizacaoMilitar> organizacoes = buscarSubordinados(superior);
		List<OrganizacaoMilitar> subordinados = new ArrayList<OrganizacaoMilitar>();
		
		for (OrganizacaoMilitar organizacao : organizacoes) 
			if(organizacao.getListaSuperiores().contains(superior))
				subordinados.add(organizacao);
		
		return subordinados;
	}

	/*
	 * Metodo auxiliar que busca todas as Organizações Militares Subordinadas e devolve 
	 * o resultado para o método definido em interface que irá iterar sobre o resultado 
	 * e seleciona apenas os subordinados da OM informada
	 */
	private List<OrganizacaoMilitar> buscarSubordinados(OrganizacaoMilitar superior) {
		
		TypedQuery<OrganizacaoMilitar> query = getEntityManager()
				.createQuery("SELECT o FROM OrganizacaoMilitar o WHERE o.tipo != :tipo "
						+ "AND o.id != :id", OrganizacaoMilitar.class);
		
		query.setParameter("tipo", TipoOM.DEPARTAMENTO);
		query.setParameter("id", superior.getId());		
		
		return query.getResultList();		
	}
	
	@Override
	public List<OrganizacaoMilitar> listBySuperiores(String sigla) {
		
		OrganizacaoMilitar organizacao = getEntityManager()
				.createQuery("from OrganizacaoMilitar o left join fetch o.listaSuperiores l " + 
							 "where o.sigla = :sigla ", OrganizacaoMilitar.class)
				.setParameter("sigla", sigla)
				.getResultList().get(0);
		
		return organizacao.getListaSuperiores();
	}
		
	@Override
	public void saveOrUpdate(OrganizacaoMilitar organizacaoMilitar) {
		
		if(organizacaoMilitar != null) {
			
			organizacaoMilitar.setTipo(buildTipoOM(organizacaoMilitar.getListaSuperiores()));
			
			if(organizacaoMilitar.getId() == null)
				persist(organizacaoMilitar);
			else
				merger(organizacaoMilitar);
		}
		
	}	
	
	/*
	 * Define o tipo da Organização Militar que está sendo inserida.
	 * 
	 * DEPARTAMENTO
	 * DIRETORIA
	 * ESTABELECIMENTO_ENSINO
	 */
	private TipoOM buildTipoOM(List<OrganizacaoMilitar> superiores) {
		
		TipoOM tipo = TipoOM.ESTABELECIMENTO_ENSINO;
		
		if(superiores.size() == 0)
			tipo = TipoOM.DEPARTAMENTO;
				
		if(superiores.size() == 1)  {
			List<OrganizacaoMilitar> l = listBySuperiores(superiores.get(0).getSigla());
			if(l.size() == 0)
				tipo = TipoOM.DIRETORIA;
		}
		
		return tipo;
	}
}