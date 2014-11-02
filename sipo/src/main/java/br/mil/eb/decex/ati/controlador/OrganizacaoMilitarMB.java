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
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.servico.OrganizacaoMilitarService;

/**
 * Controlador para view de Organizações Militares. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 */
@ViewScoped
@ManagedBean
public class OrganizacaoMilitarMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrganizacaoMilitarService service;
	
	private OrganizacaoMilitar organizacaoMilitar;
	private List<OrganizacaoMilitar> omsuperiores;
	private List<OrganizacaoMilitar> selectedOMs;
	private List<OrganizacaoMilitar> organizacoes;
	private OrganizacaoMilitar organizacaoSelected;	
	
	/**
	 * Inicializa listas de organizações militares e 
	 * organizações militares classficadas como Departamento e 
	 * Diretoria e instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */
	@PostConstruct
	public void init() {
		
		//Instancia o model da página
		organizacaoMilitar = new OrganizacaoMilitar();
		
		//Carrega as listas de apoio da página
		omsuperiores = service.listBySuperiores();
		organizacoes = service.findAll();
		
		//Limpa seleção anterior de OM'S superiores
		selectedOMs = null;
	}
	
	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Organização Militar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param organizacaoMilitar Organização Militar
	 */
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
	
	/**
	 * Lista de organizacoes militares superiores. Apenas departamentos 
	 * ou diretorias disponíveis para seleção no cadastramento de estabelecimentos 
	 * de ensino ou diretorias
	 * @return lista de organizações militares superiores
	 */
	public List<OrganizacaoMilitar> getOmsuperiores() {
		return omsuperiores;
	}	

	/**
	 * Lista com organizações militares superiores selecionadas para 
	 * cadastramento dos Estabelecimentos de Ensino ou Diretoria.
	 * @return lista de organizações militares
	 */
	public List<OrganizacaoMilitar> getSelectedOMs() {
		return selectedOMs;
	}
		
	/**
	 * Lista com organizações militares superiores selecionadas para 
	 * cadastramento dos Estabelecimentos de Ensino ou Diretoria.
	 * @param selectedOMs lista de organizações militares
	 */
	public void setSelectedOMs(List<OrganizacaoMilitar> selectedOMs) {
		this.selectedOMs = selectedOMs;
	}	
		
	/**
	 * Lista das organizações militares cadastradas no 
	 * banco de dados
	 * @return lista de organizações militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoes() {
		return organizacoes;
	}	
	
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Organizações 
	 * Militares
	 * @return Organização Militar
	 */
	public OrganizacaoMilitar getOrganizacaoSelected() {
		return organizacaoSelected;
	}
	
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção da dataTable e detalhamento de Organizações 
	 * Militares
	 * @param organizacaoSelected organização militar selecionada
	 */
	public void setOrganizacaoSelected(OrganizacaoMilitar organizacaoSelected){
		
		this.organizacaoSelected = organizacaoSelected;
		
		if(this.organizacaoSelected != null) {
			List<OrganizacaoMilitar> lista = service.listBySuperiores(organizacaoSelected.getSigla());
			this.organizacaoSelected.setListaSuperiores(lista);
		}		
	}	
	
	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar alterações na organização militar
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		
		organizacaoMilitar = (OrganizacaoMilitar) slc.getObject();
		
		List<OrganizacaoMilitar> lista = service.listBySuperiores(organizacaoMilitar.getSigla());
		
		selectedOMs = new ArrayList<OrganizacaoMilitar>();
		
		for (OrganizacaoMilitar om : lista)
			selectedOMs.add(om);
		
	}
		
	/**
	 * Persiste uma organização militar no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() {
				
		String message = (organizacaoMilitar.getId() == null ? 
			"Registro incluído com sucesso." : "Registro alterado com sucesso.");
				
		try {
			
			//Retira possíveis referências ciclicas
			retirarReferenciaCiclicaOMSelecionadas();
			
			//Configura organizacoes militares superiores
			organizacaoMilitar.setListaSuperiores(selectedOMs);
			service.saveOrUpdate(organizacaoMilitar);
			
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
	 * Exclui uma organização do banco de dados
	 * @return sucesso ou falha
	 */
	public String excluir() {
		
		String result = service.remove(organizacaoMilitar);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
	
	/*
	 * Retira possíveis referências ciclicas, por exemplo, o usuário 
	 * informar uma OM e selecionar a própria OM como superior.
	 * 
	 * Ex: OM: DETMil, OM Superior: DETMil
	 */
	private void retirarReferenciaCiclicaOMSelecionadas() {

		if(selectedOMs != null && selectedOMs.size() != 0) {
			if(organizacaoMilitar != null) {
				for(Iterator<OrganizacaoMilitar> i = selectedOMs.iterator(); i.hasNext();) {
					OrganizacaoMilitar om = i.next();
					if(om.equals(organizacaoMilitar))
						i.remove();
				}
			}
		}
	}
	
}