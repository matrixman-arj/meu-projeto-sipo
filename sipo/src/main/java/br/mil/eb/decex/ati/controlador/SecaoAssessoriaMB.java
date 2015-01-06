package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import br.mil.eb.decex.ati.modelo.SecaoAssessoria;
import br.mil.eb.decex.ati.servico.SecaoAssessoriaService;

/**
 * Controlador para view de Seções e Assessorias. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author <b>Vanilton</b> Gomes dos Santos - 3° Sgt QE
 */
@ViewScoped
@ManagedBean
public class SecaoAssessoriaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SecaoAssessoriaService service;	
	private SecaoAssessoria secaoAssessoria;
	private List<SecaoAssessoria> secsuperiores;
	private List<SecaoAssessoria> selectedSec;
	private List<SecaoAssessoria> secoes;
	private SecaoAssessoria secaoSelected;	
	
	/**
	 * Inicializa listas de seções e assessorias e 
	 * secões assessorias classficadas como Assessoria e 
	 * Seção e instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */
	@PostConstruct
	public void init() {
		
		//Instancia o model da página
		secaoAssessoria = new SecaoAssessoria();
		
		//Carrega as listas de apoio da página
		secsuperiores = service.listBySecSuperiores();
		secoes = service.findAll();
		
		//Limpa seleção anterior de secões superiores
		selectedSec = null;
	}
	
	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Seção Assessoria
	 */
	public SecaoAssessoria getSecaoAssessoria() {
		return secaoAssessoria;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param secaoAssessoria Seção Assessoria
	 */
	public void setSecaoAssessoria(SecaoAssessoria secaoAssessoria) {
		this.secaoAssessoria = secaoAssessoria;
	}
	
	/**
	 * Lista de seções e assessorias superiores. Apenas assessorias 
	 * ou seções disponíveis para seleção no cadastramento de subseções 
	 * ou seções
	 * @return lista de seções e assessorias superiores
	 */
	public List<SecaoAssessoria> getSecsuperiores() {
		return secsuperiores;
	}	

	/**
	 * Lista com seções e assessorias superiores selecionadas para 
	 * cadastramento de Subseção ou Seção.
	 * @return lista de seções assessorias
	 */
	public List<SecaoAssessoria> getSelectedSec() {
		return selectedSec;
	}
		
	/**
	 * Lista com seções e assessorias superiores selecionadas para 
	 * cadastramento de Subseção ou Seção.
	 * @param selectedSec lista de seções assessorias
	 */
	public void setSelectedSec(List<SecaoAssessoria> selectedSec) {
		this.selectedSec = selectedSec;
	}	
		
	/**
	 * Lista das seções assessorias cadastradas no 
	 * banco de dados
	 * @return lista de seções assessorias
	 */
	public List<SecaoAssessoria> getSecoes() {
		return secoes;
	}	
	
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de seções 
	 * assessorias 	 
	 * @return Seções Assessorias
	 */
	public SecaoAssessoria getSecaoSelected() {
		return secaoSelected;
	}
	
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de seções 
	 * assessorias 	
	 * @param secaoSelected seção assessoria selecionada
	 */
	public void setSecaoSelected(SecaoAssessoria secaoSelected){
		
		this.secaoSelected = secaoSelected;
		
		if(this.secaoSelected != null) {
			List<SecaoAssessoria> lista = service.listBySecSuperiores(secaoSelected.getSigla());
			this.secaoSelected.setListaSecSuperiores(lista);
		}		
	}	
	
	public void setSecsuperiores(List<SecaoAssessoria> secsuperiores) {
		this.secsuperiores = secsuperiores;
	}

	public void setSecoes(List<SecaoAssessoria> secoes) {
		this.secoes = secoes;
	}

	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar alterações na seção assessoria
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		
		secaoAssessoria = (SecaoAssessoria) slc.getObject();
		
		List<SecaoAssessoria> lista = service.listBySecSuperiores(secaoAssessoria.getSigla());
		
		selectedSec = new ArrayList<SecaoAssessoria>();
		
		for (SecaoAssessoria secao : lista)
			selectedSec.add(secao);
		
	}
		
	/**
	 * Persiste uma seção assessoria no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() {
				
		String message = (secaoAssessoria.getId() == null ? 
			"Registro incluído com sucesso." : "Registro alterado com sucesso.");
				
		try {
			
			//Retira possíveis referências ciclicas
			retirarReferenciaCiclicaSecoesSelecionadas();
			
			//Configura organizacoes militares superiores
			secaoAssessoria.setListaSecSuperiores(selectedSec);
			service.saveOrUpdate(secaoAssessoria);		
			
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Confirmação"));
			return null;
		}
		
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
			new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
				
		init();
		
		return "success";
	}
	
	/**
	 * Exclui uma seção do banco de dados
	 * @return sucesso ou falha
	 */
	public String excluir() {
		
		String result = service.remove(secaoAssessoria);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
	
	/*
	 * Retira possíveis referências ciclicas, por exemplo, o usuário 
	 * informar uma SEÇÃO/ASSESSORIA e selecionar a própria SEÇÃO/ASSESSORIA como superior.
	 * 
	 * Ex: SEÇÃO/ASSESSORIA: MANUTENÇÃO, SEÇÃO/ASSESSORIA Superior: MANUTENÇÃO
	 */
	private void retirarReferenciaCiclicaSecoesSelecionadas() {

		if(selectedSec != null && selectedSec.size() != 0) {
			if(secaoAssessoria != null) {
				for(Iterator<SecaoAssessoria> i = selectedSec.iterator(); i.hasNext();) {
					SecaoAssessoria secao = i.next();
					if(secao.equals(secaoAssessoria))
						i.remove();
				}
			}
		}
	}
	
}